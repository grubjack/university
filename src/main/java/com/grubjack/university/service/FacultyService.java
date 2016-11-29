package com.grubjack.university.service;

import com.grubjack.university.dao.DepartmentDao;
import com.grubjack.university.dao.FacultyDao;
import com.grubjack.university.dao.GroupDao;
import com.grubjack.university.model.Department;
import com.grubjack.university.model.Faculty;
import com.grubjack.university.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by grubjack on 29.11.2016.
 */
@Service
public class FacultyService implements BaseService<Faculty> {

    @Autowired
    private FacultyDao facultyDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private GroupDao groupDao;

    @Override
    public List<Faculty> findAll() {
        return facultyDao.findAll();
    }

    @Override
    public Faculty findById(int id) {
        return facultyDao.find(id);
    }

    @Override
    public void delete(int id) {
        facultyDao.delete(id);
    }

    public void create(Department department, int facultyId) {
        if (department != null && !departmentDao.findAll().contains(department)) {
            departmentDao.create(department, facultyId);
        }
    }

    public void update(Department department, int facultyId) {
        if (department != null) {
            departmentDao.update(department, facultyId);
        }
    }


    public void create(Group group, int facultyId) {
        if (group != null && !groupDao.findAll().contains(group)) {
            groupDao.create(group, facultyId);
        }
    }

    public void update(Group group, int facultyId) {
        if (group != null) {
            groupDao.update(group, facultyId);
        }
    }

    public List<Faculty> findByName(String name) {
        if (name != null) {
            return facultyDao.findByName(name);
        }
        return Collections.emptyList();
    }

    public List<Department> findDepartments(int facultyId) {
        return departmentDao.findAll(facultyId);
    }

    public List<Group> findGroups(int facultyId) {
        return groupDao.findAll(facultyId);
    }

    public List<Group> findGroupsByName(String name, int facultyId) {
        if (name != null) {
            return groupDao.findByName(name, facultyId);
        }
        return Collections.emptyList();
    }

    public List<Department> findDepartmentsByName(String name, int facultyId) {
        if (name != null) {
            return departmentDao.findByName(name, facultyId);
        }
        return Collections.emptyList();
    }

}
