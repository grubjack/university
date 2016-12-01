package com.grubjack.university.dao;

import com.grubjack.university.model.Classroom;
import com.grubjack.university.model.DayOfWeek;
import com.grubjack.university.model.TimeOfDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by grubjack on 30.11.2016.
 */

@Transactional(readOnly = true)
public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {

    List<Classroom> findAllByOrderByNumber();

    List<Classroom> findAvailableByDayAndTime(DayOfWeek day, TimeOfDay time);

}