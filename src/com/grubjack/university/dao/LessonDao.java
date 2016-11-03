package com.grubjack.university.dao;

import com.grubjack.university.domain.DayOfWeek;
import com.grubjack.university.domain.Lesson;

import java.util.List;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface LessonDao {

    Lesson save(Lesson lesson);

    boolean delete(int id);

    Lesson get(int id);

    List<Lesson> getAll();

    List<Lesson> getAllByGroup(int groupId);

    List<Lesson> getAllByTeacher(int teacherId);

    List<Lesson> getAllByDayForGroup(int groupId, DayOfWeek dayOfWeek);

    List<Lesson> getAllByDayForTeacher(int teacherId, DayOfWeek dayOfWeek);

}
