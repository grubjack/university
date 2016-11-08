package com.grubjack.university.dao;

import com.grubjack.university.domain.Classroom;
import com.grubjack.university.exception.DaoException;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface ClassroomDao extends BaseDao<Classroom> {

    void create(Classroom classroom) throws DaoException;

    void update(Classroom classroom) throws DaoException;

    Classroom findByNumber(String number) throws DaoException;
}
