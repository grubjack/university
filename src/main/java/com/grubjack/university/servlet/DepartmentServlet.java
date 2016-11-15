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

    public static final String LIST = "departments.jsp";
    public static final String ADD_OR_EDIT = "department.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String forward = LIST;
        String facultyId = req.getParameter("fid");
        String departmentId = req.getParameter("id");
        String action = req.getParameter("action");

        String title = "Departments";
        List<Department> departments = null;

        if (facultyId != null) {
            Faculty faculty = University.getInstance().findFaculty(Integer.parseInt(facultyId));
            if (faculty != null) {

                if ("create".equalsIgnoreCase(action)) {
                    forward = ADD_OR_EDIT;

                } else if ("delete".equalsIgnoreCase(action)) {
                    if (departmentId != null) {
                        Department department = University.getInstance().findDepartment(Integer.parseInt(departmentId));
                        faculty.deleteDepartment(department);
                    }

                } else if ("edit".equalsIgnoreCase(action)) {
                    forward = ADD_OR_EDIT;
                    if (departmentId != null) {
                        Department department = University.getInstance().findDepartment(Integer.parseInt(departmentId));
                        req.setAttribute("department", department);
                    }


                }

                departments = faculty.getDepartments();
                title = String.format("Departments of %s faculty", faculty.getName());
            }

        } else {
            departments = University.getInstance().getDepartments();
        }

        req.setAttribute("facultyId", facultyId);
        req.setAttribute("departments", departments);
        req.setAttribute("title", title);
        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String facultyId = req.getParameter("fid");
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        List<Department> departments = null;
        String title = "Departments";

        if (facultyId != null) {
            Faculty faculty = University.getInstance().findFaculty(Integer.parseInt(facultyId));
            if (faculty != null && name != null && !name.isEmpty()) {
                Department department = new Department(name);

                if (id == null || id.isEmpty()) {
                    faculty.createDepartment(department);
                } else {
                    department.setId(Integer.parseInt(id));
                    faculty.updateDepartment(department);
                }

                departments = faculty.getDepartments();
                title = String.format("Departments of %s faculty", faculty.getName());
            }
        }
        req.setAttribute("facultyId", facultyId);
        req.setAttribute("departments", departments);
        req.setAttribute("title", title);
        req.getRequestDispatcher(LIST).forward(req, resp);
    }
}
