package com.grubjack.university.servlet;

import com.grubjack.university.domain.DayOfWeek;
import com.grubjack.university.domain.TimeOfDay;
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
@WebServlet("/timetable")
public class TimetableServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("days", DayOfWeek.names());
        req.setAttribute("times", TimeOfDay.names());
        req.setAttribute("timetables", University.getInstance().findGroupTimetables());
        req.getRequestDispatcher("timetable.jsp").forward(req, resp);
    }
}
