package com.grubjack.university.dao;

import com.grubjack.university.domain.DayOfWeek;
import com.grubjack.university.domain.Lesson;

import java.util.List;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface LessonDao extends RootDao<Lesson> {

    List<Lesson> findAllByGroup(int groupId);

    List<Lesson> findAllByTeacher(int teacherId);

    List<Lesson> findAllByDayForGroup(int groupId, DayOfWeek dayOfWeek);

    List<Lesson> findAllByDayForTeacher(int teacherId, DayOfWeek dayOfWeek);

}
