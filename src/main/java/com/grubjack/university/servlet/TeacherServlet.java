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

    public static final String LIST = "teachers.jsp";
    public static final String ADD_OR_EDIT = "teacher.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String forward = LIST;
        String departmentId = req.getParameter("did");
        String teacherId = req.getParameter("id");
        String action = req.getParameter("action");

        String title = "Teachers";

        List<Teacher> teachers = null;

        if (departmentId != null) {
            Department department = University.getInstance().findDepartment(Integer.parseInt(departmentId));
            if (department != null) {

                title = String.format("Teachers of %s department", department.getName());

                if ("create".equalsIgnoreCase(action)) {
                    forward = ADD_OR_EDIT;
                    title = "Create teacher";
                } else if ("delete".equalsIgnoreCase(action)) {
                    if (teacherId != null) {
                        Teacher teacher = University.getInstance().findTeacher(Integer.parseInt(teacherId));
                        department.deleteTeacher(teacher);
                    }

                } else if ("edit".equalsIgnoreCase(action)) {
                    forward = ADD_OR_EDIT;
                    if (teacherId != null) {
                        Teacher teacher = University.getInstance().findTeacher(Integer.parseInt(teacherId));
                        req.setAttribute("teacher", teacher);
                        title = "Edit teacher";
                    }
                }

                teachers = department.getTeachers();
            }
        } else {
            teachers = University.getInstance().getTeachers();
        }

        req.setAttribute("departmentId", departmentId);
        req.setAttribute("teachers", teachers);
        req.setAttribute("title", title);
        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String departmentId = req.getParameter("did");
        String id = req.getParameter("id");
        String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("lastname");
        String salary = req.getParameter("salary");
        List<Teacher> teachers = null;
        String title = "Teachers";

        if (departmentId != null) {
            Department department = University.getInstance().findDepartment(Integer.parseInt(departmentId));
            if (department != null && firstname != null && !firstname.isEmpty() && lastname != null && !lastname.isEmpty() && salary != null && !salary.isEmpty()) {

                Teacher teacher = new Teacher(firstname, lastname, Integer.parseInt(salary));

                if (id == null || id.isEmpty()) {
                    department.createTeacher(teacher);
                } else {
                    teacher.setId(Integer.parseInt(id));
                    department.updateTeacher(teacher);
                }

                teachers = department.getTeachers();
                title = String.format("Teachers of %s department", department.getName());
            }
        }
        req.setAttribute("departmentId", departmentId);
        req.setAttribute("teachers", teachers);
        req.setAttribute("title", title);
        req.getRequestDispatcher(LIST).forward(req, resp);
    }

}
