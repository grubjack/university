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
 * Created by grubjack on 17.11.2016.
 */
@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String entity = req.getParameter("entity");
        String title = "Home page";
        String page = "index.html";

        if (name != null) {

            if (entity != null && !entity.isEmpty()) {

                if (entity.equalsIgnoreCase("student")) {

                    List<Student> students = new ArrayList<>();
                    for (Student student : University.getInstance().getStudents()) {
                        if (student.getName().toLowerCase().contains(name.toLowerCase())) {
                            students.add(student);
                        }
                    }
                    title = "Students";
                    page = "students.jsp";
                    req.setAttribute("students", students);

                } else if (entity.equalsIgnoreCase("teacher")) {

                    List<Teacher> teachers = new ArrayList<>();
                    for (Teacher teacher : University.getInstance().getTeachers()) {
                        if (teacher.getName().toLowerCase().contains(name.toLowerCase())) {
                            teachers.add(teacher);
                        }
                    }
                    title = "Teachers";
                    page = "teachers.jsp";
                    req.setAttribute("teachers", teachers);

                } else if (entity.equalsIgnoreCase("faculty")) {

                    List<Faculty> faculties = new ArrayList<>();
                    for (Faculty faculty : University.getInstance().getFaculties()) {
                        if (faculty.getName().toLowerCase().contains(name.toLowerCase())) {
                            faculties.add(faculty);
                        }
                    }
                    title = "Faculties";
                    page = "faculties.jsp";
                    req.setAttribute("faculties", faculties);

                } else if (entity.equalsIgnoreCase("department")) {

                    List<Department> departments = new ArrayList<>();
                    for (Department department : University.getInstance().getDepartments()) {
                        if (department.getName().toLowerCase().contains(name.toLowerCase())) {
                            departments.add(department);
                        }
                    }
                    title = "Departments";
                    page = "departments.jsp";
                    req.setAttribute("departments", departments);

                } else if (entity.equalsIgnoreCase("group")) {

                    List<Group> groups = new ArrayList<>();
                    for (Group group : University.getInstance().getGroups()) {
                        if (group.getName().toLowerCase().contains(name.toLowerCase())) {
                            groups.add(group);
                        }
                    }
                    title = "Groups";
                    page = "groups.jsp";
                    req.setAttribute("groups", groups);
                }

            }
        }
        req.setAttribute("title", title);
        req.getRequestDispatcher(page).forward(req, resp);
    }
}
