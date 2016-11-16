package com.grubjack.university.dao;

import com.grubjack.university.domain.Classroom;
import com.grubjack.university.domain.DayOfWeek;
import com.grubjack.university.domain.TimeOfDay;
import com.grubjack.university.exception.DaoException;

import java.util.List;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface ClassroomDao extends BaseDao<Classroom> {

    void create(Classroom classroom) throws DaoException;

    void update(Classroom classroom) throws DaoException;

    Classroom findByNumber(String number) throws DaoException;

    List<Classroom> findAvailable(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) throws DaoException;
}
