package com.grubjack.university.dao.impl;

import com.grubjack.university.dao.ClassroomDao;
import com.grubjack.university.dao.ClassroomRepository;
import com.grubjack.university.model.Classroom;
import com.grubjack.university.model.DayOfWeek;
import com.grubjack.university.model.TimeOfDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by grubjack on 30.11.2016.
 */
@Repository
public class ClassroomRepositoryImpl implements ClassroomDao {

    @Autowired
    private ClassroomRepository classroomRepository;

    @Override
    public void delete(int id) {
        classroomRepository.delete(id);
    }

    @Override
    public Classroom find(int id) {
        return classroomRepository.findOne(id);
    }

    @Override
    public List<Classroom> findAll() {
        return classroomRepository.findAllByOrderByNumber();
    }

    @Override
    public void create(Classroom classroom) {
        update(classroom);
    }

    @Override
    public void update(Classroom classroom) {
        classroomRepository.save(classroom);
    }

    @Override
    public List<Classroom> findAvailable(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        return classroomRepository.findAvailableByDayAndTime(dayOfWeek,timeOfDay);
    }
}