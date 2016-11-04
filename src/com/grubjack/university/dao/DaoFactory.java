package com.grubjack.university.dao;

import com.grubjack.university.dao.jdbc.*;
import com.grubjack.university.domain.Student;
import com.grubjack.university.domain.Teacher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by grubjack on 03.11.2016.
 */
public final class DaoFactory {
    public static final String DB_URL = "jdbc:postgresql://localhost/university";
    public static final String DB_DRIVER = "org.postgresql.Driver";
    public static final String DB_USER = "fox";
    public static final String DB_PASS = "rootINA";
    private static DaoFactory instance;

    static {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private DaoFactory() {
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
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
        return new ClassroomDaoPlainJdbcImpl();
    }

    public DepartmentDao getDepartmentDao() {
        return new DepartmentDaoPlainJdbcImpl();
    }

    public FacultyDao getFacultyDao() {
        return new FacultyDaoPlainJdbcImpl();
    }

    public GroupDao getGroupDao() {
        return new GroupDaoPlainJdbcImpl();
    }

    public PersonDao<Student> getStudentDao() {
        return new StudentDaoPlainJdbcImpl();
    }

    public PersonDao<Teacher> getTeacherDao() {
        return new TeacherDaoPlainJdbcImpl();
    }
}
