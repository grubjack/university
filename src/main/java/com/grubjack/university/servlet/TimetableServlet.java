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
@WebServlet("/timetable")
public class TimetableServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String facultyId = req.getParameter("fid");
        String groupId = req.getParameter("gid");
        String studentId = req.getParameter("sid");
        String teacherId = req.getParameter("tid");

        String title = "Timetable";
        String home = "index.html";
        String homeTitle = "Home page";
        List<Timetable> timetables = new ArrayList<>();

        if (facultyId != null) {
            Faculty faculty = University.getInstance().findFaculty(Integer.parseInt(facultyId));
            if (faculty != null) {
                timetables = faculty.findGroupTimetables();
                title = String.format("Timetable for faculty %s", faculty.getName());
                home = "faculties";
                homeTitle = "Faculties";
            }
        } else if (groupId != null) {
            Faculty faculty = University.getInstance().findGroupFaculty(Integer.parseInt(groupId));
            Group group = University.getInstance().findGroup(Integer.parseInt(groupId));
            if (faculty != null && group != null) {
                timetables.add(faculty.findTimetable(group));
                title = String.format("Timetable for group %s", group.getName());
                home = "groups";
                homeTitle = "Groups";
            }
        } else if (studentId != null) {
            Faculty faculty = University.getInstance().findStudentFaculty(Integer.parseInt(studentId));
            Student student = University.getInstance().findStudent(Integer.parseInt(studentId));
            if (faculty != null && student != null) {
                timetables.add(faculty.findTimetable(student));
                title = String.format("Timetable for student %s", student.getName());
                home = "students";
                homeTitle = "Students";
            }
        } else if (teacherId != null) {
            Faculty faculty = University.getInstance().findTeacherFaculty(Integer.parseInt(teacherId));
            Teacher teacher = University.getInstance().findTeacher(Integer.parseInt(teacherId));
            if (faculty != null && teacher != null) {
                timetables.add(faculty.findTimetable(teacher));
                title = String.format("Timetable for teacher %s", teacher.getName());
                home = "teachers";
                homeTitle = "Teachers";
            }

        } else {
            timetables = University.getInstance().findGroupTimetables();
        }

        req.setAttribute("days", DayOfWeek.names());
        req.setAttribute("times", TimeOfDay.names());
        req.setAttribute("timetables", timetables);
        req.setAttribute("title", title);
        req.setAttribute("home", home);
        req.setAttribute("homeTitle", homeTitle);
        req.getRequestDispatcher("timetable.jsp").forward(req, resp);
    }
}
