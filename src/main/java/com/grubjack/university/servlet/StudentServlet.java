package com.grubjack.university.servlet;

import com.grubjack.university.model.Group;
import com.grubjack.university.model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by grubjack on 09.11.2016.
 */
@WebServlet("/students")
public class StudentServlet extends AbstractHttpServlet {

    public static final String LIST = "students.jsp";
    public static final String ADD_OR_EDIT = "student.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String forward = LIST;
        String groupId = req.getParameter("gid");
        String studentId = req.getParameter("id");
        String action = req.getParameter("action");

        String title = "Students";
        List<Student> students = null;

        if (groupId != null) {
            Group group = groupService.findById(Integer.parseInt(groupId));
            if (group != null) {

                title = String.format("Students of %s group", group.getName());

                if ("create".equalsIgnoreCase(action)) {
                    forward = ADD_OR_EDIT;
                    title = "Create student";

                } else if ("delete".equalsIgnoreCase(action)) {
                    if (studentId != null) {
                        studentService.delete(Integer.parseInt(studentId));
                    }

                } else if ("edit".equalsIgnoreCase(action)) {
                    forward = ADD_OR_EDIT;
                    if (studentId != null) {
                        Student student = studentService.findById(Integer.parseInt(studentId));
                        req.setAttribute("student", student);
                        title = "Edit student";
                    }
                }
                students = groupService.findStudents(Integer.parseInt(groupId));
            }
        } else {
            students = studentService.findAll();
        }

        req.setAttribute("groupId", groupId);
        req.setAttribute("students", students);
        req.setAttribute("title", title);
        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String groupId = req.getParameter("gid");
        String id = req.getParameter("id");
        String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("lastname");
        List<Student> students = null;
        String title = "Students";

        if (groupId != null) {
            Group group = groupService.findById(Integer.parseInt(groupId));
            if (group != null && firstname != null && !firstname.isEmpty() && lastname != null && !lastname.isEmpty()) {

                Student student = new Student(firstname, lastname);

                if (id == null || id.isEmpty()) {
                    groupService.create(student, Integer.parseInt(groupId));
                } else {
                    student.setId(Integer.parseInt(id));
                    groupService.update(student, Integer.parseInt(groupId));
                }

                students = groupService.findStudents(Integer.parseInt(groupId));
                title = String.format("Student of %s group", group.getName());
            }
        }
        req.setAttribute("groupId", groupId);
        req.setAttribute("students", students);
        req.setAttribute("title", title);
        req.getRequestDispatcher(LIST).forward(req, resp);
    }

}
