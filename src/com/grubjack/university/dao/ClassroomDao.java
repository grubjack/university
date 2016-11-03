package com.grubjack.university.dao;

import com.grubjack.university.domain.Classroom;

import java.util.List;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface ClassroomDao {

    Classroom save(Classroom room);

    boolean delete(int id);

    Classroom get(int id);

    List<Classroom> getAll();

    Classroom getByNumber(String number);
}
