package com.grubjack.university.servlet;

import com.grubjack.university.domain.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by grubjack on 09.11.2016.
 */
@WebServlet("/lessons")
public class LessonServlet extends HttpServlet {

    public static final String LIST = "lessons.jsp";
    public static final String ADD_OR_EDIT = "lesson.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String forward = LIST;
        String lessonId = req.getParameter("id");
        String teacherId = req.getParameter("tid");
        String groupId = req.getParameter("gid");
        String studentId = req.getParameter("sid");
        String facultyId = req.getParameter("fid");
        String action = req.getParameter("action");
        String day = req.getParameter("day");
        String time = req.getParameter("time");

        Lesson lesson = null;
        Group group = null;
        Teacher teacher = null;

        if (lessonId != null && !lessonId.isEmpty()) {
            lesson = University.getInstance().findLesson(Integer.parseInt(lessonId));
            group = lesson.getGroup();
        }

        if (teacherId != null && !teacherId.isEmpty()) {
            teacher = University.getInstance().findTeacher(Integer.parseInt(teacherId));
        }

        if (groupId != null && !groupId.isEmpty()) {
            group = University.getInstance().findGroup(Integer.parseInt(groupId));
        }

        if (studentId != null && !studentId.isEmpty()) {
            Student student = University.getInstance().findStudent(Integer.parseInt(studentId));
            group = University.getInstance().findGroup(student);
        }

        if (facultyId != null && !facultyId.isEmpty()) {
            Faculty faculty = University.getInstance().findFaculty(Integer.parseInt(facultyId));

            if (faculty != null) {
                List<Group> groups;
                if (lesson != null) {
                    groups = University.getInstance().findAvailableGroups(lesson.getDayOfWeek(), lesson.getTimeOfDay());
                } else {
                    groups = University.getInstance().findAvailableGroups(DayOfWeek.valueOf(day), TimeOfDay.convert(time));
                }
                List<Group> facultyGroups = new ArrayList<>();
                for (Group facultyGroup : faculty.getGroups()) {
                    if (groups.contains(facultyGroup)) {
                        facultyGroups.add(facultyGroup);
                    }
                }
                req.setAttribute("facultyGroups", facultyGroups);
            }
        }

        if ("create".equalsIgnoreCase(action)) {
            forward = ADD_OR_EDIT;
            req.setAttribute("selectedGroup", group);
            req.setAttribute("selectedTeacher", teacher);
            req.setAttribute("groups", University.getInstance().findAvailableGroups(DayOfWeek.valueOf(day), TimeOfDay.convert(time)));
            req.setAttribute("teachers", University.getInstance().findAvailableTeachers(DayOfWeek.valueOf(day), TimeOfDay.convert(time)));
            req.setAttribute("rooms", University.getInstance().findAvailableRooms(DayOfWeek.valueOf(day), TimeOfDay.convert(time)));
        } else if ("delete".equalsIgnoreCase(action)) {
            if (lesson != null) {
                Group lessonGroup = lesson.getGroup();
                if (lessonGroup != null) {
                    Faculty faculty = University.getInstance().findGroupFaculty(lessonGroup.getId());
                    faculty.getTimetable().findUnit(lesson.getDayOfWeek().toString()).deleteLesson(lesson);
                }
            }
        } else if ("edit".equalsIgnoreCase(action)) {
            forward = ADD_OR_EDIT;
            if (lesson != null) {
                req.setAttribute("lesson", lesson);
                req.setAttribute("selectedGroup", group);
                req.setAttribute("selectedTeacher", teacher);
                req.setAttribute("groups", University.getInstance().findAvailableGroups(lesson.getDayOfWeek(), lesson.getTimeOfDay()));
                req.setAttribute("teachers", University.getInstance().findAvailableTeachers(lesson.getDayOfWeek(), lesson.getTimeOfDay()));
                req.setAttribute("rooms", University.getInstance().findAvailableRooms(lesson.getDayOfWeek(), lesson.getTimeOfDay()));
            }
        } else {
            req.setAttribute("lessons", University.getInstance().getLessons());
        }

        req.setAttribute("sid", studentId);
        req.setAttribute("tid", teacherId);
        req.setAttribute("gid", groupId);
        req.setAttribute("fid", facultyId);
        req.setAttribute("day", day);
        req.setAttribute("time", time);
        req.setAttribute("lessons", University.getInstance().getLessons());
        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = LIST;

        String id = req.getParameter("id");
        String day = req.getParameter("day");
        String time = req.getParameter("time");

        String subject = req.getParameter("subject");
        String teacherId = req.getParameter("teacher");
        String groupId = req.getParameter("group");
        String classroomId = req.getParameter("classroom");

        String fid = req.getParameter("fid");
        String sid = req.getParameter("sid");
        String tid = req.getParameter("tid");
        String gid = req.getParameter("gid");

        Faculty faculty = null;

        if (fid != null && !fid.isEmpty()) {
            faculty = University.getInstance().findFaculty(Integer.parseInt(fid));
        } else if (sid != null && !sid.isEmpty()) {
            faculty = University.getInstance().findStudentFaculty(Integer.parseInt(sid));
        } else if (tid != null && !tid.isEmpty()) {
            faculty = University.getInstance().findTeacherFaculty(Integer.parseInt(tid));
        } else if (gid != null && !gid.isEmpty()) {
            faculty = University.getInstance().findGroupFaculty(Integer.parseInt(gid));
        }


        if (faculty != null && subject != null && !subject.isEmpty() && teacherId != null && !teacherId.isEmpty() &&
                groupId != null && !groupId.isEmpty() && classroomId != null && !classroomId.isEmpty()) {

            Teacher teacher = University.getInstance().findTeacher(Integer.parseInt(teacherId));
            Group group = University.getInstance().findGroup(Integer.parseInt(groupId));
            Classroom classroom = University.getInstance().findRoom(Integer.parseInt(classroomId));

            Lesson lesson = new Lesson(subject);
            lesson.setDayOfWeek(DayOfWeek.valueOf(day));
            lesson.setTimeOfDay(TimeOfDay.convert(time));
            lesson.setTeacher(teacher);
            lesson.setGroup(group);
            lesson.setClassroom(classroom);

            if (id == null || id.isEmpty()) {
                faculty.getTimetable().findUnit(day).createLesson(lesson);
            } else {
                lesson.setId(Integer.parseInt(id));
                faculty.getTimetable().findUnit(day).updateLesson(lesson);
            }
        }

        req.setAttribute("lessons", University.getInstance().getLessons());
        req.getRequestDispatcher(forward).forward(req, resp);
    }
}
