package com.grubjack.university.ui;

import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.PersonDao;
import com.grubjack.university.domain.Student;
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
@WebServlet("/students")
public class StudentServlet extends HttpServlet {

    private static Logger log = LoggerFactory.getLogger(StudentServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PersonDao<Student> studentDao = DaoFactory.getInstance().getStudentDao();
        try {
            req.setAttribute("students", studentDao.findAll());
        } catch (DaoException e) {
            log.error("Can't find students", e);
        }
        req.getRequestDispatcher("students.jsp").forward(req, resp);
    }
}
