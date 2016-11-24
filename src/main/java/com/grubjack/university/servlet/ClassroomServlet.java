package com.grubjack.university.servlet;

import com.grubjack.university.domain.Classroom;
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
@WebServlet("/classrooms")
public class ClassroomServlet extends AbstractHttpServlet {

    public static final String LIST = "classrooms.jsp";
    public static final String ADD_OR_EDIT = "classroom.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = LIST;
        String action = req.getParameter("action");
        String roomId = req.getParameter("id");

        if ("delete".equalsIgnoreCase(action)) {
            if (roomId != null) {
                Classroom room = university.findRoom(Integer.parseInt(roomId));
                university.deleteRoom(room);
                req.setAttribute("classrooms", university.getRooms());
            }
        } else if ("edit".equalsIgnoreCase(action)) {
            forward = ADD_OR_EDIT;
            if (roomId != null) {
                Classroom room = university.findRoom(Integer.parseInt(roomId));
                req.setAttribute("classroom", room);
                req.setAttribute("title", "Edit classroom");
            }

        } else if ("create".equalsIgnoreCase(action)) {
            forward = ADD_OR_EDIT;
            req.setAttribute("title", "Create classroom");
        } else {
            forward = LIST;
            req.setAttribute("classrooms", university.getRooms());
        }

        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String number = req.getParameter("number");
        String location = req.getParameter("location");
        String capacity = req.getParameter("capacity");

        if (number != null && !number.isEmpty() && location != null && !location.isEmpty() && capacity != null && !capacity.isEmpty()) {
            Classroom classroom = new Classroom(number, location, Integer.parseInt(capacity));

            if (id == null || id.isEmpty()) {
                university.createRoom(classroom);
            } else {
                classroom.setId(Integer.parseInt(id));
                university.updateRoom(classroom);
            }
        }
        req.setAttribute("classrooms", university.getRooms());
        req.getRequestDispatcher(LIST).forward(req, resp);
    }
}
