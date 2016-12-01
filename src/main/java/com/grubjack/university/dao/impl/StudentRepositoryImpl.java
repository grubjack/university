package com.grubjack.university.dao.impl;

import com.grubjack.university.dao.GroupRepository;
import com.grubjack.university.dao.PersonDao;
import com.grubjack.university.dao.StudentRepository;
import com.grubjack.university.model.DayOfWeek;
import com.grubjack.university.model.Student;
import com.grubjack.university.model.TimeOfDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by grubjack on 30.11.2016.
 */
@Repository
public class StudentRepositoryImpl implements PersonDao<Student> {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public void delete(int id) {
        studentRepository.delete(id);
    }

    @Override
    public Student find(int id) {
        return studentRepository.findOne(id);
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAllByOrderByLastNameAscFirstNameAsc();
    }

    @Override
    public void create(Student student, int groupId) {
        update(student, groupId);
    }

    @Override
    public void update(Student student, int groupId) {
        student.setGroup(groupRepository.findOne(groupId));
        studentRepository.save(student);
    }

    @Override
    public List<Student> findAll(int groupId) {
        return studentRepository.findByGroupIdOrderByLastNameAscFirstNameAsc(groupId);
    }

    @Override
    public List<Student> findAvailable(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Student> findByName(String name) {
        return studentRepository.findStudentsByName(name);
    }

    @Override
    public List<Student> findByName(String name, int groupId) {
        return studentRepository.findStudentsByNameAndGroup(name, groupId);
    }
}