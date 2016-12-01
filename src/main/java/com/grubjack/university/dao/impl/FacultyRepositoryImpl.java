package com.grubjack.university.dao.impl;

import com.grubjack.university.dao.FacultyDao;
import com.grubjack.university.dao.FacultyRepository;
import com.grubjack.university.model.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by grubjack on 30.11.2016.
 */
@Repository
public class FacultyRepositoryImpl implements FacultyDao {

    @Autowired
    private FacultyRepository facultyRepository;

    @Override
    public void delete(int id) {
        facultyRepository.delete(id);
    }

    @Override
    public Faculty find(int id) {
        return facultyRepository.findOne(id);
    }

    @Override
    public List<Faculty> findAll() {
        return facultyRepository.findAllByOrderByName();
    }

    @Override
    public void create(Faculty faculty) {
        update(faculty);
    }

    @Override
    public void update(Faculty faculty) {
        facultyRepository.save(faculty);
    }

    @Override
    public List<Faculty> findByName(String name) {
        return facultyRepository.findFacultiesByName(name);
    }
}