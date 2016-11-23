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

                    String gid = req.getParameter("gid");

                    List<Student> students = new ArrayList<>();

                    if (gid != null && !gid.isEmpty()) {
                        page = "students.jsp?gid=" + gid;
                        req.setAttribute("groupId", gid);
                        Group group = University.getInstance().findGroup(Integer.parseInt(gid));
                        if (group != null) {
                            title = String.format("Students of %s group", group.getName());
                            for (Student student : group.getStudents()) {
                                if (student.getName().toLowerCase().contains(name.toLowerCase())) {
                                    students.add(student);
                                }
                            }
                        }

                    } else {
                        title = "Students";
                        page = "students.jsp";

                        for (Student student : University.getInstance().getStudents()) {

                            if (student.getName().toLowerCase().contains(name.toLowerCase())) {
                                students.add(student);
                            }
                        }

                    }

                    req.setAttribute("students", students);

                } else if (entity.equalsIgnoreCase("teacher")) {

                    String did = req.getParameter("did");
                    List<Teacher> teachers = new ArrayList<>();

                    if (did != null && !did.isEmpty()) {
                        page = "teachers.jsp?did=" + did;
                        req.setAttribute("departmentId", did);
                        Department department = University.getInstance().findDepartment(Integer.parseInt(did));
                        if (department != null) {
                            title = String.format("Teachers of %s department", department.getName());
                            for (Teacher teacher : department.getTeachers()) {
                                if (teacher.getName().toLowerCase().contains(name.toLowerCase())) {
                                    teachers.add(teacher);
                                }
                            }
                        }


                    } else {
                        title = "Teachers";
                        page = "teachers.jsp";

                        for (Teacher teacher : University.getInstance().getTeachers()) {
                            if (teacher.getName().toLowerCase().contains(name.toLowerCase())) {
                                teachers.add(teacher);
                            }
                        }

                    }
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

                    String fid = req.getParameter("fid");
                    List<Department> departments = new ArrayList<>();

                    if (fid != null && !fid.isEmpty()) {
                        page = "departments.jsp?fid=" + fid;
                        req.setAttribute("facultyId", fid);

                        Faculty faculty = University.getInstance().findFaculty(Integer.parseInt(fid));
                        if (faculty != null) {
                            title = String.format("Departments of %s faculty", faculty.getName());
                            for (Department department : faculty.getDepartments()) {
                                if (department.getName().toLowerCase().contains(name.toLowerCase())) {
                                    departments.add(department);
                                }
                            }
                        }

                    } else {
                        title = "Departments";
                        page = "departments.jsp";

                        for (Department department : University.getInstance().getDepartments()) {
                            if (department.getName().toLowerCase().contains(name.toLowerCase())) {
                                departments.add(department);
                            }
                        }
                    }

                    req.setAttribute("departments", departments);

                } else if (entity.equalsIgnoreCase("group")) {

                    String fid = req.getParameter("fid");
                    List<Group> groups = new ArrayList<>();

                    if (fid != null && !fid.isEmpty()) {
                        page = "groups.jsp?fid=" + fid;
                        req.setAttribute("facultyId", fid);

                        Faculty faculty = University.getInstance().findFaculty(Integer.parseInt(fid));
                        if (faculty != null) {
                            title = String.format("Groups of %s faculty", faculty.getName());
                            for (Group group : faculty.getGroups()) {
                                if (group.getName().toLowerCase().contains(name.toLowerCase())) {
                                    groups.add(group);
                                }
                            }
                        }

                    } else {
                        title = "Groups";
                        page = "groups.jsp";

                        for (Group group : University.getInstance().getGroups()) {
                            if (group.getName().toLowerCase().contains(name.toLowerCase())) {
                                groups.add(group);
                            }
                        }
                    }

                    req.setAttribute("groups", groups);
                }

            }
        }
        req.setAttribute("title", title);
        req.getRequestDispatcher(page).forward(req, resp);
    }
}
