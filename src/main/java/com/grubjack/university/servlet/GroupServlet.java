package com.grubjack.university.servlet;

import com.grubjack.university.domain.Faculty;
import com.grubjack.university.domain.Group;
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
@WebServlet("/groups")
public class GroupServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String facultyId = req.getParameter("id");

        String title = "Groups";
        List<Group> groups = null;

        if (facultyId != null) {
            Faculty faculty = University.getInstance().findFaculty(Integer.parseInt(facultyId));
            if (faculty != null) {
                groups = faculty.getGroups();
                title = String.format("Groups of %s faculty", faculty.getName());
            }
        } else {
            groups = University.getInstance().getGroups();
        }

        req.setAttribute("groups", groups);
        req.setAttribute("title", title);
        req.getRequestDispatcher("groups.jsp").forward(req, resp);
    }

}
