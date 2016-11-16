package com.grubjack.university.dao;

import com.grubjack.university.domain.DayOfWeek;
import com.grubjack.university.domain.Group;
import com.grubjack.university.domain.Student;
import com.grubjack.university.domain.TimeOfDay;
import com.grubjack.university.exception.DaoException;

import java.util.List;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface GroupDao extends BaseDao<Group> {

    void create(Group group, int facultyId) throws DaoException;

    void update(Group group, int facultyId) throws DaoException;

    Group findByName(String name) throws DaoException;

    Group findByStudent(Student student) throws DaoException;

    List<Group> findAll(int facultyId) throws DaoException;

    List<Group> findAvailable(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) throws DaoException;

}
