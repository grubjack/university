package com.grubjack.university.servlet;

import com.grubjack.university.domain.Faculty;
import com.grubjack.university.domain.University;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by grubjack on 09.11.2016.
 */
@WebServlet("/faculties")
public class FacultyServlet extends HttpServlet {

    public static final String LIST = "faculties.jsp";
    public static final String ADD_OR_EDIT = "faculty.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = LIST;
        String action = req.getParameter("action");
        String facultyId = req.getParameter("id");

        if ("delete".equalsIgnoreCase(action)) {
            if (facultyId != null) {
                Faculty faculty = University.getInstance().findFaculty(Integer.parseInt(facultyId));
                University.getInstance().deleteFaculty(faculty);
                req.setAttribute("faculties", University.getInstance().getFaculties());
            }
        } else if ("edit".equalsIgnoreCase(action)) {
            forward = ADD_OR_EDIT;
            if (facultyId != null) {
                Faculty faculty = University.getInstance().findFaculty(Integer.parseInt(facultyId));
                req.setAttribute("faculty", faculty);
            }

        } else if ("create".equalsIgnoreCase(action)) {
            forward = ADD_OR_EDIT;
        } else {
            forward = LIST;
            req.setAttribute("faculties", University.getInstance().getFaculties());
        }

        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");

        if (name != null && !name.isEmpty()) {
            Faculty faculty = new Faculty(name);

            if (id == null || id.isEmpty()) {
                University.getInstance().createFaculty(faculty);
            } else {
                faculty.setId(Integer.parseInt(id));
                University.getInstance().updateFaculty(faculty);
            }
        }
        req.setAttribute("faculties", University.getInstance().getFaculties());
        req.getRequestDispatcher(LIST).forward(req, resp);
    }
}
