package com.grubjack.university.servlet;

import com.grubjack.university.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by grubjack on 09.11.2016.
 */
@WebServlet("/lessons")
public class LessonServlet extends AbstractHttpServlet {

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
        String groupName = req.getParameter("groupname");

        Lesson lesson = null;
        Group group = null;
        Teacher teacher = null;

        String title = "Timetable";
        String home = "index.html";
        String homeTitle = "Home page";
        List<Timetable> timetables = new ArrayList<>();


        // find group and teacher for default select
        if (lessonId != null && !lessonId.isEmpty()) {
            lesson = lessonService.findById(Integer.parseInt(lessonId));
            if (lesson != null) {
                group = lesson.getGroup();
            }
        }

        if (teacherId != null && !teacherId.isEmpty()) {
            teacher = teacherService.findById(Integer.parseInt(teacherId));
            if (teacher != null) {
                title = String.format("Timetable for teacher %s", teacher.getName());
            }
        }

        if (groupId != null && !groupId.isEmpty()) {
            group = groupService.findById(Integer.parseInt(groupId));
            if (group != null) {
                title = String.format("Timetable for group %s", group.getName());
            }
        }

        if (studentId != null && !studentId.isEmpty()) {
            Student student = studentService.findById(Integer.parseInt(studentId));
            if (student != null) {
                title = String.format("Timetable for student %s", student.getName());
            }
        }

        if (facultyId != null && !facultyId.isEmpty()) {
            Faculty faculty = facultyService.findById(Integer.parseInt(facultyId));
            if (faculty != null) {
                title = String.format("Timetable for faculty %s", faculty.getName());
                if (groupName != null && !groupName.isEmpty()) {
                    group = groupService.find(groupName);
                }
            }
        }

        // actions
        if ("create".equalsIgnoreCase(action)) {
            forward = ADD_OR_EDIT;
            title = "Create lesson";
            req.setAttribute("selectedGroup", group);
            req.setAttribute("selectedTeacher", teacher);
            List<Group> groups = groupService.findAvailable(DayOfWeek.valueOf(day), TimeOfDay.convert(time));
            List<Teacher> teachers = teacherService.findAvailable(DayOfWeek.valueOf(day), TimeOfDay.convert(time));
            List<Classroom> rooms = classroomService.findAvailable(DayOfWeek.valueOf(day), TimeOfDay.convert(time));
            if (groups.size() == 0) {
                req.setAttribute("groupNotification", "no free groups");
            }
            if (teachers.size() == 0) {
                req.setAttribute("teacherNotification", "no free teachers");
            }
            if (rooms.size() == 0) {
                req.setAttribute("roomNotification", "no free rooms");
            }
            req.setAttribute("groups", groups);
            req.setAttribute("teachers", teachers);
            req.setAttribute("rooms", rooms);
        } else if ("delete".equalsIgnoreCase(action)) {
            if (lesson != null) {
                lessonService.delete(Integer.parseInt(lessonId));
            }
        } else if ("edit".equalsIgnoreCase(action)) {
            forward = ADD_OR_EDIT;
            if (lesson != null) {
                title = "Edit lesson";
                req.setAttribute("lesson", lesson);
                req.setAttribute("selectedGroup", group);
                req.setAttribute("selectedTeacher", teacher);
                List<Group> groups = groupService.findAvailable(DayOfWeek.valueOf(day), TimeOfDay.convert(time));
                List<Teacher> teachers = teacherService.findAvailable(DayOfWeek.valueOf(day), TimeOfDay.convert(time));
                if (groups.size() == 0 && group == null) {
                    req.setAttribute("groupNotification", "no free groups");
                }
                if (teachers.size() == 0 && teacher == null) {
                    req.setAttribute("teacherNotification", "no free teachers");
                }

                req.setAttribute("groups", groups);
                req.setAttribute("teachers", teachers);
                req.setAttribute("groups", groups);
                req.setAttribute("teachers", teachers);
                req.setAttribute("rooms", classroomService.findAvailable(DayOfWeek.valueOf(day), TimeOfDay.convert(time)));
            }
        }

        // find timetable(s)
        if (facultyId != null) {
            timetables = lessonService.findGroupTimetables(Integer.parseInt(facultyId));
            home = "faculties";
            homeTitle = "Faculties";
            req.setAttribute("fid", facultyId);
        } else if (groupId != null) {
            timetables.add(lessonService.findGroupTimetable(Integer.parseInt(groupId)));
            home = "groups";
            homeTitle = "Groups";
            req.setAttribute("gid", groupId);
        } else if (studentId != null) {
            timetables.add(lessonService.findStudentTimetable(Integer.parseInt(studentId)));
            home = "students";
            homeTitle = "Students";
            req.setAttribute("sid", studentId);
        } else if (teacherId != null) {
            timetables.add(lessonService.findTeacherTimetable(Integer.parseInt(teacherId)));
            home = "teachers";
            homeTitle = "Teachers";
            req.setAttribute("tid", teacherId);
        } else {
            timetables = lessonService.findGroupTimetables();
        }

        req.setAttribute("sid", studentId);
        req.setAttribute("tid", teacherId);
        req.setAttribute("gid", groupId);
        req.setAttribute("fid", facultyId);
        req.setAttribute("day", day);
        req.setAttribute("time", time);

        req.setAttribute("title", title);
        req.setAttribute("home", home);
        req.setAttribute("homeTitle", homeTitle);
        req.setAttribute("days", DayOfWeek.names());
        req.setAttribute("times", TimeOfDay.names());
        req.setAttribute("timetables", timetables);
        req.getRequestDispatcher(forward).forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = LIST;

        String title = "Timetable";
        String home = "index.html";
        String homeTitle = "Home page";

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
        List<Timetable> timetables = new ArrayList<>();

        if (subject != null && !subject.isEmpty() && teacherId != null && !teacherId.isEmpty() &&
                groupId != null && !groupId.isEmpty() && classroomId != null && !classroomId.isEmpty()) {

            Lesson lesson = new Lesson(subject);
            lesson.setDayOfWeek(DayOfWeek.valueOf(day));
            lesson.setTimeOfDay(TimeOfDay.convert(time));

            if (id == null || id.isEmpty()) {
                lessonService.create(lesson, Integer.parseInt(teacherId), Integer.parseInt(classroomId), Integer.parseInt(groupId));
            } else {
                lesson.setId(Integer.parseInt(id));
                lessonService.update(lesson, Integer.parseInt(teacherId), Integer.parseInt(classroomId), Integer.parseInt(groupId));
            }

            if (fid != null && !fid.isEmpty()) {
                faculty = facultyService.findById(Integer.parseInt(fid));
                timetables = lessonService.findGroupTimetables(Integer.parseInt(fid));
                title = String.format("Timetable for faculty %s", faculty.getName());
                req.setAttribute("fid", fid);
            } else if (sid != null && !sid.isEmpty()) {
                Student student = studentService.findById(Integer.parseInt(sid));
                timetables.add(lessonService.findStudentTimetable(Integer.parseInt(sid)));
                title = String.format("Timetable for student %s", student.getName());
                req.setAttribute("sid", sid);
            } else if (tid != null && !tid.isEmpty()) {
                Teacher teacher = teacherService.findById(Integer.parseInt(tid));
                timetables.add(lessonService.findTeacherTimetable(Integer.parseInt(tid)));
                title = String.format("Timetable for teacher %s", teacher.getName());
                req.setAttribute("tid", tid);
            } else if (gid != null && !gid.isEmpty()) {
                Group group = groupService.findById(Integer.parseInt(gid));
                timetables.add(lessonService.findGroupTimetable(Integer.parseInt(gid)));
                title = String.format("Timetable for group %s", group.getName());
                req.setAttribute("gid", gid);
            } else {
                timetables = lessonService.findGroupTimetables();
            }
        }
        req.setAttribute("title", title);
        req.setAttribute("home", home);
        req.setAttribute("homeTitle", homeTitle);
        req.setAttribute("days", DayOfWeek.names());
        req.setAttribute("times", TimeOfDay.names());
        req.setAttribute("timetables", timetables);
        req.getRequestDispatcher(forward).forward(req, resp);
    }
}
