package com.grubjack.university.servlet;

import com.grubjack.university.domain.Department;
import com.grubjack.university.domain.Faculty;
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
@WebServlet("/departments")
public class DepartmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String facultyId = req.getParameter("fid");

        String title = "Departments";
        List<Department> departments = null;

        if (facultyId != null) {
            Faculty faculty = University.getInstance().findFaculty(Integer.parseInt(facultyId));
            if (faculty != null) {
                departments = faculty.getDepartments();
                title = String.format("Departments of %s faculty", faculty.getName());
            }
        } else {
            departments = University.getInstance().getDepartments();
        }


        req.setAttribute("departments", departments);
        req.setAttribute("title", title);
        req.getRequestDispatcher("departments.jsp").forward(req, resp);
    }
}
