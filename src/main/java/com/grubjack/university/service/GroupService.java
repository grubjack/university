package com.grubjack.university.service;

import com.grubjack.university.dao.GroupDao;
import com.grubjack.university.dao.PersonDao;
import com.grubjack.university.model.DayOfWeek;
import com.grubjack.university.model.Group;
import com.grubjack.university.model.Student;
import com.grubjack.university.model.TimeOfDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by grubjack on 29.11.2016.
 */
@Service
public class GroupService implements BaseService<Group> {

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private PersonDao<Student> studentDao;

    @Override
    public List<Group> findAll() {
        return groupDao.findAll();
    }

    @Override
    public Group findById(int id) {
        return groupDao.find(id);
    }

    @Override
    public void delete(int id) {
        groupDao.delete(id);
    }

    public List<Group> findAvailable(DayOfWeek day, TimeOfDay time) {
        if (day != null && time != null) {
            return groupDao.findAvailable(day, time);
        }
        return Collections.emptyList();
    }

    public List<Group> findByName(String name) {
        if (name != null) {
            return groupDao.findByName(name);
        }
        return Collections.emptyList();
    }

    public void create(Student student, int groupId) {
        if (student != null) {
            student.setGroup(groupDao.find(groupId));
            studentDao.create(student, groupId);
        }
    }

    public void update(Student student, int groupId) {
        if (student != null) {
            student.setGroup(groupDao.find(groupId));
            studentDao.update(student, groupId);
        }
    }

    public List<Student> findStudents(int groupId) {
        return studentDao.findAll(groupId);
    }

    public List<Student> findStudentsByName(String name, int groupId) {
        if (name != null) {
            return studentDao.findByName(name, groupId);
        }
        return Collections.emptyList();
    }

    public Group find(String name) {
        if (name != null && !name.isEmpty()) {
            return groupDao.find(name);
        }
        return null;
    }

}
