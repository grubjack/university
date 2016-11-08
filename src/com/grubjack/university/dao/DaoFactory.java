package com.grubjack.university.dao;

import com.grubjack.university.dao.impl.*;
import com.grubjack.university.domain.Student;
import com.grubjack.university.domain.Teacher;

import java.io.IOException;
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

    static {

        try {
            PROPERTIES.load(ClassLoader.getSystemResourceAsStream(PROPERTIES_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Class.forName(PROPERTIES.getProperty("database.driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private DaoFactory() {
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            String url = PROPERTIES.getProperty("database.url");
            String username = PROPERTIES.getProperty("database.username");
            String password = PROPERTIES.getProperty("database.password");
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
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
