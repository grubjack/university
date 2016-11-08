package com.grubjack.university.dao;

import com.grubjack.university.domain.Department;
import com.grubjack.university.exception.DaoException;

import java.util.List;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface DepartmentDao extends BaseDao<Department> {

    void create(Department department, int facultyId) throws DaoException;

    void update(Department department, int facultyId) throws DaoException;

    Department findByName(String name) throws DaoException;

    List<Department> findAll(int facultyId) throws DaoException;

}
