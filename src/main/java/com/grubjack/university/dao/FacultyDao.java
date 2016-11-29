package com.grubjack.university.dao;

import com.grubjack.university.model.Faculty;

import java.util.List;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface FacultyDao extends BaseDao<Faculty> {

    void create(Faculty faculty);

    void update(Faculty faculty);

    List<Faculty> findByName(String name);

}
