package com.grubjack.university.dao;

import com.grubjack.university.domain.Group;

import java.util.List;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface GroupDao extends RootDao<Group> {

    Group findByName(String name);

    List<Group> findAll(int facultyId);

}
