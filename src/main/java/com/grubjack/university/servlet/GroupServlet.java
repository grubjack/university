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

    public static final String LIST = "groups.jsp";
    public static final String ADD_OR_EDIT = "group.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String forward = LIST;
        String facultyId = req.getParameter("fid");
        String groupId = req.getParameter("id");
        String action = req.getParameter("action");

        String title = "Groups";
        List<Group> groups = null;

        if (facultyId != null) {
            Faculty faculty = University.getInstance().findFaculty(Integer.parseInt(facultyId));
            if (faculty != null) {

                title = String.format("Groups of %s faculty", faculty.getName());

                if ("create".equalsIgnoreCase(action)) {
                    forward = ADD_OR_EDIT;
                    title = "Create group";

                } else if ("delete".equalsIgnoreCase(action)) {
                    if (groupId != null) {
                        Group group = University.getInstance().findGroup(Integer.parseInt(groupId));
                        faculty.deleteGroup(group);
                    }

                } else if ("edit".equalsIgnoreCase(action)) {
                    forward = ADD_OR_EDIT;
                    if (groupId != null) {
                        Group group = University.getInstance().findGroup(Integer.parseInt(groupId));
                        req.setAttribute("group", group);
                        title = "Edit group";
                    }
                }
                groups = faculty.getGroups();
            }

        } else {
            groups = University.getInstance().getGroups();
        }

        req.setAttribute("facultyId", facultyId);
        req.setAttribute("groups", groups);
        req.setAttribute("title", title);
        req.getRequestDispatcher(forward).forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String facultyId = req.getParameter("fid");
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        List<Group> groups = null;
        String title = "Groups";

        if (facultyId != null) {
            Faculty faculty = University.getInstance().findFaculty(Integer.parseInt(facultyId));
            if (faculty != null && name != null && !name.isEmpty()) {
                Group group = new Group(name);

                if (id == null || id.isEmpty()) {
                    faculty.createGroup(group);
                } else {
                    group.setId(Integer.parseInt(id));
                    faculty.updateGroup(group);
                }

                groups = faculty.getGroups();
                title = String.format("Groups of %s faculty", faculty.getName());
            }
        }
        req.setAttribute("facultyId", facultyId);
        req.setAttribute("groups", groups);
        req.setAttribute("title", title);
        req.getRequestDispatcher(LIST).forward(req, resp);
    }

}
