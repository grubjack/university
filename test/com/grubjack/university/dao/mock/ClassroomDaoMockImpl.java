package com.grubjack.university.dao.mock;

import com.grubjack.university.dao.ClassroomDao;
import com.grubjack.university.domain.Classroom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grubjack on 04.11.2016.
 */
public class ClassroomDaoMockImpl implements ClassroomDao {
    private List<Classroom> classrooms = new ArrayList<>();

    @Override
    public void create(Classroom classroom) {
        String number = classroom.getNumber();
        for (Classroom room : classrooms) {
            if (room.getNumber().equalsIgnoreCase(number))
                return;
        }
        classrooms.add(classroom);
    }

    @Override
    public void update(Classroom classroom) {
        int index = classrooms.indexOf(classroom);
        if (index != -1) {
            classrooms.remove(classroom);
            classrooms.add(index, classroom);
        }
    }

    @Override
    public void delete(int id) {
        if (id < classrooms.size())
            classrooms.remove(id);
    }

    @Override
    public Classroom find(int id) {
        if (id < classrooms.size())
            return classrooms.get(id);
        return null;
    }

    @Override
    public List<Classroom> findAll() {
        return classrooms;
    }

    @Override
    public Classroom findByNumber(String number) {
        for (Classroom classroom : classrooms) {
            if (classroom.getNumber().equalsIgnoreCase(number))
                return classroom;
        }
        return null;
    }
}
