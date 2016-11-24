package com.grubjack.university.dao;

import com.grubjack.university.domain.Department;

import java.util.List;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface DepartmentDao extends BaseDao<Department> {

    void create(Department department, int facultyId);

    void update(Department department, int facultyId);

    List<Department> findAll(int facultyId);

}
