package com.grubjack.university.dao;

import com.grubjack.university.dao.impl.*;
import com.grubjack.university.domain.Student;
import com.grubjack.university.domain.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by grubjack on 03.11.2016.
 */
public final class DaoFactory {
    private static final String PROPERTIES_FILE = "db/postgres.properties";
    private static final Properties PROPERTIES = new Properties();
    private static DaoFactory instance;

    private static Logger log = LoggerFactory.getLogger(DaoFactory.class);

    private DaoFactory() {
    }

    public static Connection getConnection() {
        Connection connection = null;
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_FILE);

        if (inputStream != null) {

            try {
                PROPERTIES.load(inputStream);
            } catch (IOException e) {
                log.error("Can't load property file", e);
            }
            try {
                Class.forName(PROPERTIES.getProperty("database.driver"));
            } catch (ClassNotFoundException e) {
                log.error("Can't load class for name", e);
            }

            try {
                String url = PROPERTIES.getProperty("database.url");
                String username = PROPERTIES.getProperty("database.username");
                String password = PROPERTIES.getProperty("database.password");
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                log.error("Can't get connection", e);
            }
        } else {
            log.error("Can't get input stream from property file");
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
