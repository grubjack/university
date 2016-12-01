package com.grubjack.university.dao;

import com.grubjack.university.model.DayOfWeek;
import com.grubjack.university.model.Teacher;
import com.grubjack.university.model.TimeOfDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by grubjack on 30.11.2016.
 */
@Transactional(readOnly = true)
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    List<Teacher> findAllByOrderByLastNameAscFirstNameAsc();

    List<Teacher> findByDepartmentIdOrderByLastNameAscFirstNameAsc(int departmentId);

    List<Teacher> findAvailableByDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay);

    List<Teacher> findTeachersByName(String name);

    List<Teacher> findTeachersByNameAndDepartment(String name, int departmentId);

}
