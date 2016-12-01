package com.grubjack.university.dao.impl;

import com.grubjack.university.dao.DepartmentDao;
import com.grubjack.university.dao.DepartmentRepository;
import com.grubjack.university.dao.FacultyRepository;
import com.grubjack.university.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by grubjack on 30.11.2016.
 */
@Repository
public class DepartmentRepositoryImpl implements DepartmentDao {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Override
    public void delete(int id) {
        departmentRepository.delete(id);
    }

    @Override
    public Department find(int id) {
        return departmentRepository.findOne(id);
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAllByOrderByName();
    }

    @Override
    public void create(Department department, int facultyId) {
        update(department, facultyId);
    }

    @Override
    public void update(Department department, int facultyId) {
        department.setFaculty(facultyRepository.getOne(facultyId));
        departmentRepository.save(department);
    }

    @Override
    public List<Department> findAll(int facultyId) {
        return departmentRepository.findByFacultyIdOrderByName(facultyId);
    }

    @Override
    public List<Department> findByName(String name) {
        return departmentRepository.findDepartmentsByName(name);
    }

    @Override
    public List<Department> findByName(String name, int facultyId) {
        return departmentRepository.findDepartmentsByNameAndFaculty(name, facultyId);
    }
}