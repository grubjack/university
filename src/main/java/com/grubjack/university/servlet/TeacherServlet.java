package com.grubjack.university.servlet;

import com.grubjack.university.domain.Department;
import com.grubjack.university.domain.Teacher;
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
@WebServlet("/teachers")
public class TeacherServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String departmentId = req.getParameter("id");

        String title = "Teachers";
        List<Teacher> teachers = null;

        if (departmentId != null) {
            Department department = University.getInstance().findDepartment(Integer.parseInt(departmentId));
            if (department != null) {
                teachers = department.getTeachers();
                title = String.format("Teacher of %s department", department.getName());
            }
        } else {
            teachers = University.getInstance().getTeachers();
        }

        req.setAttribute("title", title);
        req.setAttribute("teachers", teachers);
        req.getRequestDispatcher("teachers.jsp").forward(req, resp);
    }
}
