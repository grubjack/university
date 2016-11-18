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
            lesson = University.getInstance().findLesson(Integer.parseInt(lessonId));
            if (lesson != null) {
                group = lesson.getGroup();
            }
        }

        if (teacherId != null && !teacherId.isEmpty()) {
            teacher = University.getInstance().findTeacher(Integer.parseInt(teacherId));
            if (teacher != null) {
                title = String.format("Timetable for teacher %s", teacher.getName());
            }
        }

        if (groupId != null && !groupId.isEmpty()) {
            group = University.getInstance().findGroup(Integer.parseInt(groupId));
            if (group != null) {
                title = String.format("Timetable for group %s", group.getName());
            }
        }

        if (studentId != null && !studentId.isEmpty()) {
            Student student = University.getInstance().findStudent(Integer.parseInt(studentId));
            Faculty faculty = University.getInstance().findStudentFaculty(Integer.parseInt(studentId));
            group = faculty.findGroupByStudent(student);
            if (student != null) {
                title = String.format("Timetable for student %s", student.getName());
            }
        }

        if (facultyId != null && !facultyId.isEmpty()) {
            Faculty faculty = University.getInstance().findFaculty(Integer.parseInt(facultyId));
            if (faculty != null) {
                title = String.format("Timetable for faculty %s", faculty.getName());
                if (groupName != null && !groupName.isEmpty()) {
                    group = faculty.findGroup(groupName);
                }
            }
        }

        // actions
         if ("create".equalsIgnoreCase(action)) {
            forward = ADD_OR_EDIT;
            title = "Create lesson";
            req.setAttribute("selectedGroup", group);
            req.setAttribute("selectedTeacher", teacher);
            List<Group> groups = University.getInstance().findAvailableGroups(DayOfWeek.valueOf(day), TimeOfDay.convert(time));
            List<Teacher> teachers = University.getInstance().findAvailableTeachers(DayOfWeek.valueOf(day), TimeOfDay.convert(time));
            List<Classroom> rooms = University.getInstance().findAvailableRooms(DayOfWeek.valueOf(day), TimeOfDay.convert(time));
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
                Group lessonGroup = lesson.getGroup();
                if (lessonGroup != null) {
                    Faculty faculty = University.getInstance().findGroupFaculty(lessonGroup.getId());
                    faculty.getTimetable().findUnit(lesson.getDayOfWeek().toString()).deleteLesson(lesson);
                }
            }
        } else if ("edit".equalsIgnoreCase(action)) {
            forward = ADD_OR_EDIT;
            if (lesson != null) {
                title = "Edit lesson";
                req.setAttribute("lesson", lesson);
                req.setAttribute("selectedGroup", group);
                req.setAttribute("selectedTeacher", teacher);
                List<Group> groups = University.getInstance().findAvailableGroups(DayOfWeek.valueOf(day), TimeOfDay.convert(time));
                List<Teacher> teachers = University.getInstance().findAvailableTeachers(DayOfWeek.valueOf(day), TimeOfDay.convert(time));
                if (groups.size() == 0 && group == null) {
                    req.setAttribute("groupNotification", "no free groups");
                }
                if (teachers.size() == 0 && teacher == null) {
                    req.setAttribute("teacherNotification", "no free teachers");
                }

                req.setAttribute("groups", groups);
                req.setAttribute("teachers", teachers);
                req.setAttribute("groups", groups);
                req.setAttribute("teachers",teachers);
                req.setAttribute("rooms", University.getInstance().findAvailableRooms(DayOfWeek.valueOf(day), TimeOfDay.convert(time)));
            }
        }

        // find timetable(s)
        if (facultyId != null) {
            Faculty faculty = University.getInstance().findFaculty(Integer.parseInt(facultyId));
            if (faculty != null) {
                timetables = faculty.findGroupTimetables();
                home = "faculties";
                homeTitle = "Faculties";
                req.setAttribute("fid", faculty.getId());
            }
        } else if (groupId != null) {
            Faculty faculty = University.getInstance().findGroupFaculty(Integer.parseInt(groupId));
            Group group2 = University.getInstance().findGroup(Integer.parseInt(groupId));
            if (faculty != null && group2 != null) {
                timetables.add(faculty.findTimetable(group2));
                home = "groups";
                homeTitle = "Groups";
                req.setAttribute("gid", group2.getId());
            }
        } else if (studentId != null) {
            Faculty faculty = University.getInstance().findStudentFaculty(Integer.parseInt(studentId));
            Student student = University.getInstance().findStudent(Integer.parseInt(studentId));
            if (faculty != null && student != null) {
                timetables.add(faculty.findTimetable(student));
                home = "students";
                homeTitle = "Students";
                req.setAttribute("sid", student.getId());
            }
        } else if (teacherId != null) {
            Faculty faculty = University.getInstance().findTeacherFaculty(Integer.parseInt(teacherId));
            Teacher teacher2 = University.getInstance().findTeacher(Integer.parseInt(teacherId));
            if (faculty != null && teacher2 != null) {
                timetables.add(faculty.findTimetable(teacher2));
                home = "teachers";
                homeTitle = "Teachers";
                req.setAttribute("tid", teacher2.getId());
            }
        } else {
            timetables = University.getInstance().findGroupTimetables();
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

            if (fid != null && !fid.isEmpty()) {
                timetables = faculty.findGroupTimetables();
                title = String.format("Timetable for faculty %s", faculty.getName());
                req.setAttribute("fid", fid);
            } else if (sid != null && !sid.isEmpty()) {
                Student student = University.getInstance().findStudent(Integer.parseInt(sid));
                timetables.add(faculty.findTimetable(faculty.findGroupByStudent(student)));
                title = String.format("Timetable for student %s", student.getName());
                req.setAttribute("sid", sid);
            } else if (tid != null && !tid.isEmpty()) {
                timetables.add(faculty.findTimetable(teacher));
                title = String.format("Timetable for teacher %s", teacher.getName());
                req.setAttribute("tid", tid);
            } else if (gid != null && !gid.isEmpty()) {
                timetables.add(faculty.findTimetable(group));
                title = String.format("Timetable for group %s", group.getName());
                req.setAttribute("gid", gid);
            } else {
                timetables = University.getInstance().findGroupTimetables();
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
