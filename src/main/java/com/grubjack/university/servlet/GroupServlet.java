package com.grubjack.university.servlet;

import com.grubjack.university.model.Faculty;
import com.grubjack.university.model.Group;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by grubjack on 09.11.2016.
 */
@WebServlet("/groups")
public class GroupServlet extends AbstractHttpServlet {

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
            Faculty faculty = facultyService.findById(Integer.parseInt(facultyId));
            if (faculty != null) {

                title = String.format("Groups of %s faculty", faculty.getName());

                if ("create".equalsIgnoreCase(action)) {
                    forward = ADD_OR_EDIT;
                    title = "Create group";

                } else if ("delete".equalsIgnoreCase(action)) {
                    if (groupId != null) {
                        groupService.delete(Integer.parseInt(groupId));
                    }

                } else if ("edit".equalsIgnoreCase(action)) {
                    forward = ADD_OR_EDIT;
                    if (groupId != null) {
                        Group group = groupService.findById(Integer.parseInt(groupId));
                        req.setAttribute("group", group);
                        title = "Edit group";
                    }
                }
                groups = facultyService.findGroups(Integer.parseInt(facultyId));
            }

        } else {
            groups = groupService.findAll();
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
            Faculty faculty = facultyService.findById(Integer.parseInt(facultyId));
            if (faculty != null && name != null && !name.isEmpty()) {
                Group group = new Group(name);

                if (id == null || id.isEmpty()) {
                    facultyService.create(group, Integer.parseInt(facultyId));
                } else {
                    group.setId(Integer.parseInt(id));
                    facultyService.update(group, Integer.parseInt(facultyId));
                }

                groups = facultyService.findGroups(Integer.parseInt(facultyId));
                title = String.format("Groups of %s faculty", faculty.getName());
            }
        }
        req.setAttribute("facultyId", facultyId);
        req.setAttribute("groups", groups);
        req.setAttribute("title", title);
        req.getRequestDispatcher(LIST).forward(req, resp);
    }

}
