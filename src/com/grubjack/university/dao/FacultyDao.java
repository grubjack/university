package com.grubjack.university.dao;

import com.grubjack.university.domain.Faculty;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface FacultyDao extends RootDao<Faculty> {

    Faculty findByName(String name);

}
