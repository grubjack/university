package com.grubjack.university.servlet;

import com.grubjack.university.domain.Student;
import com.grubjack.university.domain.University;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by grubjack on 22.11.2016.
 */
public abstract class AbstractHttpServlet extends HttpServlet {
    protected University university;
    protected Student studentService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        university = (University) context.getBean("university");
        studentService = (Student) context.getBean("student");
    }
}