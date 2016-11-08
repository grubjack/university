package com.grubjack.university.dao;

import com.grubjack.university.domain.Classroom;
import com.grubjack.university.domain.Faculty;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface FacultyDao extends BaseDao<Faculty> {

    void create(Faculty faculty);

    void update(Faculty faculty);

    Faculty findByName(String name);

}
