package com.grubjack.university.dao;

import com.grubjack.university.domain.Group;

import java.util.List;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface GroupDao {

    Group save(Group group);

    boolean delete(int id);

    Group get(int id);

    List<Group> getAll();

    Group getByName(String name);

    List<Group> getAll(int facultyId);

}
