package com.grubjack.university.dao;

import com.grubjack.university.dao.impl.*;
import com.grubjack.university.domain.*;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by grubjack on 03.11.2016.
 */
public final class DaoFactory {
    private static DaoFactory instance;
    private static Logger log = LoggerFactory.getLogger(DaoFactory.class);

    private DaoFactory() {
    }

    public static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory = null;
        try {
            Properties properties = new Properties();
            properties.put("hibernate.show_sql", "true");
            properties.put("hibernate.format_sql", "false");
            properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/UniversityDB");

            LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
            sessionBuilder.addProperties(properties);
            sessionBuilder.addAnnotatedClasses(Classroom.class, Department.class, Faculty.class, Group.class, Lesson.class, Student.class, Teacher.class);
            sessionFactory = sessionBuilder.buildSessionFactory();

        } catch (NamingException e) {
            log.error("Can't find datasource in jndi context", e);
        }
        return sessionFactory;
    }

    public static DaoFactory getInstance() {
        if (instance == null) {
            instance = new DaoFactory();
        }
        return instance;
    }

    public ClassroomDao getClassroomDao() {
        return new ClassroomDaoImpl();
    }

    public DepartmentDao getDepartmentDao() {
        return new DepartmentDaoImpl();
    }

    public FacultyDao getFacultyDao() {
        return new FacultyDaoImpl();
    }

    public GroupDao getGroupDao() {

        return new GroupDaoImpl();
    }

    public PersonDao<Student> getStudentDao() {
        return new StudentDaoImpl();
    }

    public PersonDao<Teacher> getTeacherDao() {
        return new TeacherDaoImpl();
    }

    public LessonDao getLessonDao() {
        return new LessonDaoImpl();
    }
}