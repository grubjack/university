package com.grubjack.university.dao;

import com.grubjack.university.domain.Department;

import java.util.List;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface DepartmentDao {

    Department save(Department department);

    boolean delete(int id);

    Department get(int id);

    List<Department> getAll();

    Department getByName(String name);

    List<Department> getAll(int facultyId);

}
