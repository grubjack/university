package com.grubjack.university.dao;

import com.grubjack.university.domain.DayOfWeek;
import com.grubjack.university.domain.TimeOfDay;

import java.util.List;

/**
 * Created by grubjack on 03.11.2016.
 */
public interface PersonDao<T> extends BaseDao<T> {

    void create(T t, int unitId);

    void update(T t, int unitId);

    List<T> findAll(int unitId);

    List<T> findAvailable(DayOfWeek dayOfWeek, TimeOfDay timeOfDay);

}
