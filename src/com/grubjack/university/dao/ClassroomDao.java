package com.grubjack.university.dao;

import com.grubjack.university.domain.Classroom;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface ClassroomDao extends BaseDao<Classroom> {

    Classroom findByNumber(String number);
}
