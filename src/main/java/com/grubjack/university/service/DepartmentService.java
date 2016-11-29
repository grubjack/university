package com.grubjack.university.service;

import com.grubjack.university.dao.DepartmentDao;
import com.grubjack.university.dao.PersonDao;
import com.grubjack.university.model.Department;
import com.grubjack.university.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by grubjack on 29.11.2016.
 */
@Service
public class DepartmentService implements BaseService<Department> {

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private PersonDao<Teacher> teacherDao;

    @Override
    public List<Department> findAll() {
        return departmentDao.findAll();
    }

    @Override
    public Department findById(int id) {
        return departmentDao.find(id);
    }

    @Override
    public void delete(int id) {
        departmentDao.delete(id);
    }

    public List<Department> findByName(String name) {
        if (name != null) {
            return departmentDao.findByName(name);
        }
        return Collections.emptyList();
    }

    public void create(Teacher teacher, int departmentId) {
        if (teacher != null && !teacherDao.findAll().contains(teacher)) {
            teacher.setDepartment(departmentDao.find(departmentId));
            teacherDao.create(teacher, departmentId);
        }
    }

    public void update(Teacher teacher, int departmentId) {
        if (teacher != null) {
            teacher.setDepartment(departmentDao.find(departmentId));
            teacherDao.update(teacher, departmentId);
        }
    }

    public List<Teacher> findTeachers(int departmentId) {
        return teacherDao.findAll(departmentId);
    }

    public List<Teacher> findTeachersByName(String name, int departmentId) {
        if (name != null) {
            return teacherDao.findByName(name, departmentId);
        }
        return Collections.emptyList();
    }

}
