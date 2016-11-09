package com.grubjack.university.servlet;

import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.PersonDao;
import com.grubjack.university.domain.Teacher;
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
@WebServlet("/teachers")
public class TeacherServlet extends HttpServlet {

    private static Logger log = LoggerFactory.getLogger(TeacherServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PersonDao<Teacher> teacherDao = DaoFactory.getInstance().getTeacherDao();
        try {
            req.setAttribute("teachers", teacherDao.findAll());
        } catch (DaoException e) {
            log.error("Can't find teachers", e);
        }
        req.getRequestDispatcher("teachers.jsp").forward(req, resp);
    }
}
