package com.grubjack.university.servlet;

import com.grubjack.university.domain.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by grubjack on 17.11.2016.
 */
@WebServlet("/search")
public class SearchServlet extends AbstractHttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String entity = req.getParameter("entity");
        String title = "Home page";
        String page = "index.html";

        if (name != null) {

            if (entity != null && !entity.isEmpty()) {

                if (entity.equalsIgnoreCase("student")) {

                    String gid = req.getParameter("gid");

                    List<Student> students = new ArrayList<>();

                    if (gid != null && !gid.isEmpty()) {
                        page = "students.jsp?gid=" + gid;
                        req.setAttribute("groupId", gid);
                        Group group = Group.findById(Integer.parseInt(gid));
                        if (group != null) {
                            title = String.format("Students of %s group", group.getName());
                            students = group.findStudents(name);
                        }

                    } else {
                        title = "Students";
                        page = "students.jsp";
                        students = Student.findByName(name);
                    }

                    req.setAttribute("students", students);

                } else if (entity.equalsIgnoreCase("teacher")) {

                    String did = req.getParameter("did");
                    List<Teacher> teachers = new ArrayList<>();

                    if (did != null && !did.isEmpty()) {
                        page = "teachers.jsp?did=" + did;
                        req.setAttribute("departmentId", did);
                        Department department = Department.findById(Integer.parseInt(did));
                        if (department != null) {
                            title = String.format("Teachers of %s department", department.getName());
                            teachers = department.findTeachers(name);
                        }
                    } else {
                        title = "Teachers";
                        page = "teachers.jsp";
                        teachers = Teacher.findByName(name);

                    }
                    req.setAttribute("teachers", teachers);

                } else if (entity.equalsIgnoreCase("faculty")) {

                    List<Faculty> faculties = Faculty.findByName(name);
                    title = "Faculties";
                    page = "faculties.jsp";
                    req.setAttribute("faculties", faculties);

                } else if (entity.equalsIgnoreCase("department")) {

                    String fid = req.getParameter("fid");
                    List<Department> departments = new ArrayList<>();

                    if (fid != null && !fid.isEmpty()) {
                        page = "departments.jsp?fid=" + fid;
                        req.setAttribute("facultyId", fid);

                        Faculty faculty = Faculty.findById(Integer.parseInt(fid));
                        if (faculty != null) {
                            title = String.format("Departments of %s faculty", faculty.getName());
                            departments = faculty.findDepartments(name);
                        }

                    } else {
                        title = "Departments";
                        page = "departments.jsp";
                        departments = Department.findByName(name);
                    }

                    req.setAttribute("departments", departments);

                } else if (entity.equalsIgnoreCase("group")) {

                    String fid = req.getParameter("fid");
                    List<Group> groups = new ArrayList<>();

                    if (fid != null && !fid.isEmpty()) {
                        page = "groups.jsp?fid=" + fid;
                        req.setAttribute("facultyId", fid);

                        Faculty faculty = Faculty.findById(Integer.parseInt(fid));
                        if (faculty != null) {
                            title = String.format("Groups of %s faculty", faculty.getName());
                            groups = faculty.findGroups(name);
                        }

                    } else {
                        title = "Groups";
                        page = "groups.jsp";
                        groups = Group.findByName(name);
                    }

                    req.setAttribute("groups", groups);
                }

            }
        }
        req.setAttribute("title", title);
        req.getRequestDispatcher(page).forward(req, resp);
    }
}
