package com.grubjack.university.dao;

import com.grubjack.university.domain.Faculty;

import java.util.List;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface FacultyDao {

    Faculty save(Faculty faculty);

    boolean delete(int id);

    Faculty get(int id);

    List<Faculty> getAll();

    Faculty getByName(String name);

}
