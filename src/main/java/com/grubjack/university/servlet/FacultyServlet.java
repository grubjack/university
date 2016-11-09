package com.grubjack.university.servlet;

import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.FacultyDao;
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
@WebServlet("/faculties")
public class FacultyServlet extends HttpServlet {

    private static Logger log = LoggerFactory.getLogger(FacultyServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        try {
            req.setAttribute("faculties", facultyDao.findAll());
        } catch (DaoException e) {
            log.error("Can't find faculties", e);
        }
        req.getRequestDispatcher("faculties.jsp").forward(req, resp);
    }
}
