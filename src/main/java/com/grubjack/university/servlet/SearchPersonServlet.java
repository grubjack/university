package com.grubjack.university.servlet;

import com.grubjack.university.domain.Student;
import com.grubjack.university.domain.Teacher;
import com.grubjack.university.domain.University;

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
public class SearchPersonServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String person = req.getParameter("person");
        String title = "Home page";
        String page = "index.html";

        if (name != null) {

            if (person != null && !person.isEmpty()) {

                if (person.equalsIgnoreCase("student")) {

                    List<Student> students = new ArrayList<>();
                    for (Student student : University.getInstance().getStudents()) {
                        if (student.getName().toLowerCase().contains(name.toLowerCase())) {
                            students.add(student);
                        }
                    }
                    title = "Students";
                    page = "students.jsp";
                    req.setAttribute("students", students);

                } else if (person.equalsIgnoreCase("teacher")) {

                    List<Teacher> teachers = new ArrayList<>();
                    for (Teacher teacher : University.getInstance().getTeachers()) {
                        if (teacher.getName().toLowerCase().contains(name.toLowerCase())) {
                            teachers.add(teacher);
                        }
                    }
                    title = "Teachers";
                    page = "teachers.jsp";
                    req.setAttribute("teachers", teachers);
                }

            }
        }
        req.setAttribute("title", title);
        req.getRequestDispatcher(page).forward(req, resp);
    }
}
