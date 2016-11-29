package com.grubjack.university.servlet;

import com.grubjack.university.service.*;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by grubjack on 22.11.2016.
 */
public abstract class AbstractHttpServlet extends HttpServlet {
    private static ApplicationContext context;
    protected UniversityService universityService;
    protected ClassroomService classroomService;
    protected DepartmentService departmentService;
    protected FacultyService facultyService;
    protected GroupService groupService;
    protected LessonService lessonService;
    protected StudentService studentService;
    protected TeacherService teacherService;

    @Override
    public void init() throws ServletException {
        super.init();
        context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        universityService = (UniversityService) context.getBean("universityService");
        classroomService = (ClassroomService) context.getBean("classroomService");
        departmentService = (DepartmentService) context.getBean("departmentService");
        facultyService = (FacultyService) context.getBean("facultyService");
        groupService = (GroupService) context.getBean("groupService");
        lessonService = (LessonService) context.getBean("lessonService");
        studentService = (StudentService) context.getBean("studentService");
        teacherService = (TeacherService) context.getBean("teacherService");
    }

    public static ApplicationContext getContext() {
        return context;
    }
}