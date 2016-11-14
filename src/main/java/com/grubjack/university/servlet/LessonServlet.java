package com.grubjack.university.servlet;

import com.grubjack.university.domain.Lesson;
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
 * Created by grubjack on 09.11.2016.
 */
@WebServlet("/lessons")
public class LessonServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String id = req.getParameter("id");
        List<Lesson> lessons;

        if (id != null) {
            lessons = new ArrayList<>();
            Lesson lesson = University.getInstance().findLesson(Integer.parseInt(id));
            if (lesson != null) {
                lessons.add(lesson);
            }

        } else {
            lessons = University.getInstance().getLessons();
        }


        req.setAttribute("lessons", lessons);
        req.getRequestDispatcher("lessons.jsp").forward(req, resp);
    }
}
