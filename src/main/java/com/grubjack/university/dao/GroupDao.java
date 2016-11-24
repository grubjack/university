package com.grubjack.university.dao;

import com.grubjack.university.domain.DayOfWeek;
import com.grubjack.university.domain.Group;
import com.grubjack.university.domain.Student;
import com.grubjack.university.domain.TimeOfDay;

import java.util.List;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface GroupDao extends BaseDao<Group> {

    void create(Group group, int facultyId);

    void update(Group group, int facultyId);

    Group findByName(String name);

    List<Group> findAll(int facultyId);

    List<Group> findAvailable(DayOfWeek dayOfWeek, TimeOfDay timeOfDay);

}
