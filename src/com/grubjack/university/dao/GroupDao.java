package com.grubjack.university.dao;

import com.grubjack.university.DaoException;
import com.grubjack.university.domain.Group;

import java.util.List;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface GroupDao extends BaseDao<Group> {

    void create(Group group, int facultyId) throws DaoException;

    void update(Group group, int facultyId) throws DaoException;

    Group findByName(String name) throws DaoException;

    List<Group> findAll(int facultyId) throws DaoException;

}
