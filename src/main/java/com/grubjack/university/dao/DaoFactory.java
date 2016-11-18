package com.grubjack.university.dao;

import com.grubjack.university.dao.impl.*;
import com.grubjack.university.domain.Student;
import com.grubjack.university.domain.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by grubjack on 03.11.2016.
 */
public final class DaoFactory {
    private static DaoFactory instance;

    private static Logger log = LoggerFactory.getLogger(DaoFactory.class);

    private DaoFactory() {
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/UniversityDB");
            connection = dataSource.getConnection();
        } catch (NamingException | SQLException e) {
            log.error("Can't find datasource in jndi context", e);
        }
        return connection;
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
