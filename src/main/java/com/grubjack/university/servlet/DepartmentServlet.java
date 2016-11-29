package com.grubjack.university.servlet;

import com.grubjack.university.model.Department;
import com.grubjack.university.model.Faculty;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by grubjack on 09.11.2016.
 */
@WebServlet("/departments")
public class DepartmentServlet extends AbstractHttpServlet {

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
            Faculty faculty = facultyService.findById(Integer.parseInt(facultyId));
            if (faculty != null) {

                title = String.format("Departments of %s faculty", faculty.getName());

                if ("create".equalsIgnoreCase(action)) {
                    forward = ADD_OR_EDIT;
                    title = "Create department";

                } else if ("delete".equalsIgnoreCase(action)) {
                    if (departmentId != null) {
                        departmentService.delete(Integer.parseInt(departmentId));
                    }

                } else if ("edit".equalsIgnoreCase(action)) {
                    forward = ADD_OR_EDIT;
                    if (departmentId != null) {
                        Department department = departmentService.findById(Integer.parseInt(departmentId));
                        title = "Edit department";
                        req.setAttribute("department", department);
                    }
                }
                departments = facultyService.findDepartments(Integer.parseInt(facultyId));
            }

        } else {
            departments = departmentService.findAll();
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
            Faculty faculty = facultyService.findById(Integer.parseInt(facultyId));
            if (faculty != null && name != null && !name.isEmpty()) {
                Department department = new Department(name);

                if (id == null || id.isEmpty()) {
                    facultyService.create(department, Integer.parseInt(facultyId));
                } else {
                    department.setId(Integer.parseInt(id));
                    facultyService.update(department, Integer.parseInt(facultyId));
                }

                departments = facultyService.findDepartments(Integer.parseInt(facultyId));
                title = String.format("Departments of %s faculty", faculty.getName());
            }
        }
        req.setAttribute("facultyId", facultyId);
        req.setAttribute("departments", departments);
        req.setAttribute("title", title);
        req.getRequestDispatcher(LIST).forward(req, resp);
    }
}
