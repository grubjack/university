package com.grubjack.university.dao.impl;

import com.grubjack.university.dao.FacultyRepository;
import com.grubjack.university.dao.GroupDao;
import com.grubjack.university.dao.GroupRepository;
import com.grubjack.university.model.DayOfWeek;
import com.grubjack.university.model.Group;
import com.grubjack.university.model.TimeOfDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by grubjack on 30.11.2016.
 */
@Repository
public class GroupRepositoryImpl implements GroupDao {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Override
    public void delete(int id) {
        groupRepository.delete(id);
    }

    @Override
    public Group find(int id) {
        return groupRepository.findOne(id);
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAllByOrderByName();
    }

    @Override
    public void create(Group group, int facultyId) {
        update(group, facultyId);
    }

    @Override
    public void update(Group group, int facultyId) {
        group.setFaculty(facultyRepository.getOne(facultyId));
        groupRepository.save(group);
    }

    @Override
    public List<Group> findAll(int facultyId) {
        return groupRepository.findByFacultyIdOrderByName(facultyId);
    }

    @Override
    public List<Group> findAvailable(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        return groupRepository.findAvailableByDayAndTime(dayOfWeek, timeOfDay);
    }

    @Override
    public Group find(String name) {
        return groupRepository.findByName(name);
    }

    @Override
    public List<Group> findByName(String name) {
        return groupRepository.findGroupsByName(name);
    }

    @Override
    public List<Group> findByName(String name, int facultyId) {
        return groupRepository.findGroupsByNameAndFaculty(name, facultyId);
    }
}