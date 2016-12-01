package com.grubjack.university.dao;

import com.grubjack.university.model.DayOfWeek;
import com.grubjack.university.model.Group;
import com.grubjack.university.model.TimeOfDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by grubjack on 30.11.2016.
 */
@Transactional(readOnly = true)
public interface GroupRepository extends JpaRepository<Group, Integer> {

    List<Group> findAllByOrderByName();

    List<Group> findByFacultyIdOrderByName(int facultyId);

    List<Group> findAvailableByDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay);

    Group findByName(String name);

    List<Group> findGroupsByName(String name);

    List<Group> findGroupsByNameAndFaculty(String name, int facultyId);

}