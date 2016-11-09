package com.grubjack.university.servlet;

import com.grubjack.university.dao.ClassroomDao;
import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class ClassroomServlet extends HttpServlet {

    private static Logger log = LoggerFactory.getLogger(ClassroomServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClassroomDao classroomDao = DaoFactory.getInstance().getClassroomDao();
        try {
            req.setAttribute("classrooms", classroomDao.findAll());
        } catch (DaoException e) {
            log.error("Can't find classrooms", e);
        }
        req.getRequestDispatcher("classrooms.jsp").forward(req, resp);
    }
}
