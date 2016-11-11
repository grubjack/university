package com.grubjack.university.servlet;

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
@WebServlet("/lessons")
public class LessonServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("lessons", University.getInstance().getLessons());
        req.getRequestDispatcher("lessons.jsp").forward(req, resp);
    }
}
