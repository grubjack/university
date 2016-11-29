package com.grubjack.university.service;

import com.grubjack.university.dao.PersonDao;
import com.grubjack.university.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by grubjack on 29.11.2016.
 */
@Service
public class StudentService implements BaseService<Student> {

    @Autowired
    private PersonDao<Student> studentDao;

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public Student findById(int id) {
        return studentDao.find(id);
    }

    @Override
    public void delete(int id) {
        studentDao.delete(id);
    }

    public List<Student> findByName(String name) {
        if (name != null) {
            return studentDao.findByName(name);
        }
        return Collections.emptyList();
    }
}
