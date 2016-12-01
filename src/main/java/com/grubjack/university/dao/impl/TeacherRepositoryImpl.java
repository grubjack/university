package com.grubjack.university.dao.impl;

import com.grubjack.university.dao.DepartmentRepository;
import com.grubjack.university.dao.PersonDao;
import com.grubjack.university.dao.TeacherRepository;
import com.grubjack.university.model.DayOfWeek;
import com.grubjack.university.model.Teacher;
import com.grubjack.university.model.TimeOfDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by grubjack on 30.11.2016.
 */
@Repository
public class TeacherRepositoryImpl implements PersonDao<Teacher> {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public void delete(int id) {
        teacherRepository.delete(id);
    }

    @Override
    public Teacher find(int id) {
        return teacherRepository.findOne(id);
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAllByOrderByLastNameAscFirstNameAsc();
    }

    @Override
    public void create(Teacher teacher, int departmentId) {
        update(teacher, departmentId);
    }

    @Override
    public void update(Teacher teacher, int departmentId) {
        teacher.setDepartment(departmentRepository.findOne(departmentId));
        teacherRepository.save(teacher);
    }

    @Override
    public List<Teacher> findAll(int departmentId) {
        return teacherRepository.findByDepartmentIdOrderByLastNameAscFirstNameAsc(departmentId);
    }

    @Override
    public List<Teacher> findAvailable(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        return teacherRepository.findAvailableByDayAndTime(dayOfWeek, timeOfDay);
    }

    @Override
    public List<Teacher> findByName(String name) {
        return teacherRepository.findTeachersByName(name);
    }

    @Override
    public List<Teacher> findByName(String name, int departmentId) {
        return teacherRepository.findTeachersByNameAndDepartment(name, departmentId);
    }

}