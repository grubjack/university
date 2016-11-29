package com.grubjack.university.dao;

import com.grubjack.university.model.Classroom;
import com.grubjack.university.model.DayOfWeek;
import com.grubjack.university.model.TimeOfDay;

import java.util.List;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface ClassroomDao extends BaseDao<Classroom> {

    void create(Classroom classroom);

    void update(Classroom classroom);

    List<Classroom> findAvailable(DayOfWeek dayOfWeek, TimeOfDay timeOfDay);
}
