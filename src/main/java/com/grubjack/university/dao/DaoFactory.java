package com.grubjack.university.dao;

import com.grubjack.university.dao.impl.*;
import com.grubjack.university.domain.Student;
import com.grubjack.university.domain.Teacher;

/**
 * Created by grubjack on 03.11.2016.
 */
public final class DaoFactory {
    private static DaoFactory instance;

    private DaoFactory() {
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
