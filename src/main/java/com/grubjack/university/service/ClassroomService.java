package com.grubjack.university.service;

import com.grubjack.university.dao.ClassroomDao;
import com.grubjack.university.model.Classroom;
import com.grubjack.university.model.DayOfWeek;
import com.grubjack.university.model.TimeOfDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by grubjack on 29.11.2016.
 */
@Service
public class ClassroomService implements BaseService<Classroom> {

    @Autowired
    private ClassroomDao classroomDao;

    @Override
    public List<Classroom> findAll() {
        return classroomDao.findAll();
    }

    @Override
    public Classroom findById(int id) {
        return classroomDao.find(id);
    }

    @Override
    public void delete(int id) {
        classroomDao.delete(id);
    }

    public List<Classroom> findAvailable(DayOfWeek day, TimeOfDay time) {
        if (day != null && time != null) {
            return classroomDao.findAvailable(day, time);
        }
        return Collections.emptyList();
    }


}
