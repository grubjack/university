package com.grubjack.university.ui;

import com.grubjack.university.dao.ClassroomDao;
import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.DepartmentDao;
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
@WebServlet("/departments")
public class DepartmentServlet extends HttpServlet {

    private static Logger log = LoggerFactory.getLogger(DepartmentServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DepartmentDao departmentDao = DaoFactory.getInstance().getDepartmentDao();
        try {
            req.setAttribute("departments", departmentDao.findAll());
        } catch (DaoException e) {
            log.error("Can't find departments", e);
        }
        req.getRequestDispatcher("departments.jsp").forward(req, resp);
    }
}
