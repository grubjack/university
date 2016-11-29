package com.grubjack.university.dao;

import com.grubjack.university.model.DayOfWeek;
import com.grubjack.university.model.Group;
import com.grubjack.university.model.TimeOfDay;

import java.util.List;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface GroupDao extends BaseDao<Group> {

    void create(Group group, int facultyId);

    void update(Group group, int facultyId);

    List<Group> findAll(int facultyId);

    List<Group> findAvailable(DayOfWeek dayOfWeek, TimeOfDay timeOfDay);

    Group find(String name);

    List<Group> findByName(String name);

    List<Group> findByName(String name, int facultyId);

}
