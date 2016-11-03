package com.grubjack.university.dao;

import com.grubjack.university.domain.Student;

import java.util.List;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface StudentDao {

    Student save(Student student);

    boolean delete(int id);

    Student get(int id);

    List<Student> getAll();

    List<Student> getByFirstName(String firstName);

    List<Student> getByLastName(String lastName);

    List<Student> getByName(String firstName, String lastName);

    List<Student> getAll(int groupId);
}
