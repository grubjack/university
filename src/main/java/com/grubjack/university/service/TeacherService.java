package com.grubjack.university.service;

import com.grubjack.university.dao.PersonDao;
import com.grubjack.university.model.DayOfWeek;
import com.grubjack.university.model.Teacher;
import com.grubjack.university.model.TimeOfDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by grubjack on 29.11.2016.
 */
@Service
public class TeacherService implements BaseService<Teacher> {

    @Autowired
    private PersonDao<Teacher> teacherDao;

    @Override
    public List<Teacher> findAll() {
        return teacherDao.findAll();
    }

    @Override
    public Teacher findById(int id) {
        return teacherDao.find(id);
    }

    @Override
    public void delete(int id) {
        teacherDao.delete(id);
    }

    public List<Teacher> findByName(String name) {
        if (name != null) {
            return teacherDao.findByName(name);
        }
        return Collections.emptyList();
    }

    public List<Teacher> findAvailable(DayOfWeek day, TimeOfDay time) {
        if (day != null && time != null) {
            return teacherDao.findAvailable(day, time);
        }
        return Collections.emptyList();
    }
}
