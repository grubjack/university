package com.grubjack.university.dao;

import com.grubjack.university.domain.Classroom;
import com.grubjack.university.domain.DayOfWeek;
import com.grubjack.university.domain.TimeOfDay;

import java.util.List;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface ClassroomDao extends BaseDao<Classroom> {

    void create(Classroom classroom);

    void update(Classroom classroom);

    Classroom findByNumber(String number);

    List<Classroom> findAvailable(DayOfWeek dayOfWeek, TimeOfDay timeOfDay);
}
