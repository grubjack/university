package com.grubjack.university.servlet;

import com.grubjack.university.service.University;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by grubjack on 22.11.2016.
 */
public abstract class AbstractHttpServlet extends HttpServlet {
    private static ApplicationContext context;
    protected University university;

    @Override
    public void init() throws ServletException {
        super.init();
        context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        university = (University) context.getBean("university");
    }

    public static ApplicationContext getContext() {
        return context;
    }
}