package com.grubjack.university.dao;

import com.grubjack.university.domain.Faculty;
import com.grubjack.university.exception.DaoException;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface FacultyDao extends BaseDao<Faculty> {

    void create(Faculty faculty) throws DaoException;

    void update(Faculty faculty) throws DaoException;

    Faculty findByName(String name) throws DaoException;

    Faculty findByGroup(int groupId) throws DaoException;

    Faculty findByStudent(int studentId) throws DaoException;

    Faculty findByTeacher(int teacherId) throws DaoException;
}
