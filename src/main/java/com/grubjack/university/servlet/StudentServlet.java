package com.grubjack.university.servlet;

import com.grubjack.university.domain.Group;
import com.grubjack.university.domain.Student;
import com.grubjack.university.domain.University;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by grubjack on 09.11.2016.
 */
@WebServlet("/students")
public class StudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String groupId = req.getParameter("id");

        String title = "Students";
        List<Student> students = null;

        if (groupId != null) {
            Group group = University.getInstance().findGroup(Integer.parseInt(groupId));
            if (group != null) {
                students = group.getStudents();
                title = String.format("Students of %s group", group.getName());
            }
        } else {
            students = University.getInstance().getStudents();
        }

        req.setAttribute("title", title);
        req.setAttribute("students", students);
        req.getRequestDispatcher("students.jsp").forward(req, resp);
    }
}
