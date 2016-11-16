package com.grubjack.university.servlet;

import com.grubjack.university.domain.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by grubjack on 09.11.2016.
 */
@WebServlet("/lessons")
public class LessonServlet extends HttpServlet {

    public static final String LIST = "lessons.jsp";
    public static final String ADD_OR_EDIT = "lesson.jsp";
    public static final String TIMETABLE = "timetable";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String forward = LIST;
        String sid = req.getParameter("sid");
        String tid = req.getParameter("tid");
        String gid = req.getParameter("gid");
        String fid = req.getParameter("fid");
        String lessonId = req.getParameter("id");
        String action = req.getParameter("action");

        String day = req.getParameter("day");
        String time = req.getParameter("time");
        String groupName = req.getParameter("groupname");

        Faculty faculty = null;
        Lesson lesson = null;
        String url = "";

        if (lessonId != null && !lessonId.isEmpty()) {
            lesson = University.getInstance().findLesson(Integer.parseInt(lessonId));
        }

        if (fid != null && !fid.isEmpty()) {
            faculty = University.getInstance().findFaculty(Integer.parseInt(fid));
            url = "?fid=" + fid;
            req.setAttribute("fid", fid);
        } else if (sid != null && !sid.isEmpty()) {
            faculty = University.getInstance().findStudentFaculty(Integer.parseInt(sid));
            url = "?sid=" + sid;
            req.setAttribute("sid", sid);
        } else if (tid != null && !tid.isEmpty()) {
            faculty = University.getInstance().findTeacherFaculty(Integer.parseInt(tid));
            url = "?tid=" + tid;
            req.setAttribute("tid", tid);
        } else if (gid != null && !gid.isEmpty()) {
            faculty = University.getInstance().findGroupFaculty(Integer.parseInt(gid));
            url = "?gid=" + gid;
            req.setAttribute("gid", gid);
        }

        if (faculty != null) {

            if ("create".equalsIgnoreCase(action)) {
                forward = ADD_OR_EDIT;
                req.setAttribute("rooms", University.getInstance().findAvailableRooms(DayOfWeek.valueOf(day), TimeOfDay.convert(time)));
                req.setAttribute("teachers", University.getInstance().findAvailableTeachers(DayOfWeek.valueOf(day), TimeOfDay.convert(time)));
            } else if ("delete".equalsIgnoreCase(action)) {
                if (lesson != null) {
                    faculty.getTimetable().findUnit(lesson.getDayOfWeek().toString()).deleteLesson(lesson);
                    forward = TIMETABLE + url;
                }

            } else if ("edit".equalsIgnoreCase(action)) {
                forward = ADD_OR_EDIT;
                if (lesson != null) {
                    req.setAttribute("lesson", lesson);
                    req.setAttribute("rooms", University.getInstance().findAvailableRooms(DayOfWeek.valueOf(day), TimeOfDay.convert(time)));
                    req.setAttribute("teachers", University.getInstance().findAvailableTeachers(DayOfWeek.valueOf(day), TimeOfDay.convert(time)));
                }
            }
        } else {
            req.setAttribute("lessons", University.getInstance().getLessons());
        }

        req.setAttribute("sid", sid);
        req.setAttribute("tid", tid);
        req.setAttribute("gid", gid);
        req.setAttribute("fid", fid);
        req.setAttribute("day", day);
        req.setAttribute("time", time);
        req.setAttribute("groupName", groupName);
        req.getRequestDispatcher(forward).forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fid = req.getParameter("fid");
        String sid = req.getParameter("sid");
        String tid = req.getParameter("tid");
        String gid = req.getParameter("gid");

        String day = req.getParameter("day");
        String time = req.getParameter("time");
        String groupName = req.getParameter("groupName");

        String id = req.getParameter("id");

        String subject = req.getParameter("subject");
        String teacherId = req.getParameter("teacher");
        String classroomId = req.getParameter("classroom");

        String url = "";
        Faculty faculty = null;

        if (fid != null && !fid.isEmpty()) {
            faculty = University.getInstance().findFaculty(Integer.parseInt(fid));
            url = "?fid=" + fid;
            req.setAttribute("fid", fid);
        } else if (sid != null && !sid.isEmpty()) {
            faculty = University.getInstance().findStudentFaculty(Integer.parseInt(sid));
            url = "?sid=" + sid;
            req.setAttribute("sid", sid);
        } else if (tid != null && !tid.isEmpty()) {
            faculty = University.getInstance().findTeacherFaculty(Integer.parseInt(tid));
            url = "?tid=" + tid;
            req.setAttribute("tid", tid);
        } else if (gid != null && !gid.isEmpty()) {
            faculty = University.getInstance().findGroupFaculty(Integer.parseInt(gid));
            url = "?gid=" + gid;
            req.setAttribute("gid", gid);
        }

        if (faculty != null) {
            Group group = faculty.findGroup(groupName);


            if (subject != null && !subject.isEmpty() && teacherId != null && !teacherId.isEmpty() && classroomId != null && !classroomId.isEmpty() && group != null) {
                Lesson lesson = new Lesson(subject);

                Teacher teacher = University.getInstance().findTeacher(Integer.parseInt(teacherId));
                Classroom classroom = University.getInstance().findRoom(Integer.parseInt(classroomId));

                lesson.setGroup(group);
                lesson.setTeacher(teacher);
                lesson.setClassroom(classroom);
                lesson.setDayOfWeek(DayOfWeek.valueOf(day));
                lesson.setTimeOfDay(TimeOfDay.convert(time));

                if (id == null || id.isEmpty()) {
                    faculty.getTimetable().findUnit(day).createLesson(lesson);
                } else {
                    lesson.setId(Integer.parseInt(id));
                    faculty.getTimetable().findUnit(day).updateLesson(lesson);
                }

            }
        }
        resp.sendRedirect(TIMETABLE + url);
    }
}
