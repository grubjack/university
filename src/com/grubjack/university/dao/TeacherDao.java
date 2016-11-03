package com.grubjack.university.dao;

import com.grubjack.university.domain.Teacher;

import java.util.List;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface TeacherDao {

    Teacher save(Teacher teacher);

    boolean delete(int id);

    Teacher get(int id);

    List<Teacher> getAll();

    List<Teacher> getByFirstName(String firstName);

    List<Teacher> getByLastName(String lastName);

    List<Teacher> getByName(String firstName, String lastName);

    List<Teacher> getAll(int departmentId);
}
