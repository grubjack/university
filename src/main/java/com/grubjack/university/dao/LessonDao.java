package com.grubjack.university.dao;

import com.grubjack.university.domain.DayOfWeek;
import com.grubjack.university.domain.Lesson;
import com.grubjack.university.domain.TimeOfDay;
import com.grubjack.university.exception.DaoException;

import java.util.List;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface LessonDao extends BaseDao<Lesson> {

    void create(Lesson lesson) throws DaoException;

    void update(Lesson lesson) throws DaoException;

    List<Lesson> findGroupLessons(int groupId, DayOfWeek dayOfWeek) throws DaoException;

    List<Lesson> findTeacherLessons(int teacherId, DayOfWeek dayOfWeek) throws DaoException;

    Lesson findGroupLesson(int groupId, DayOfWeek dayOfWeek, TimeOfDay timeOfDay) throws DaoException;

    Lesson findTeacherLesson(int teacherId, DayOfWeek dayOfWeek, TimeOfDay timeOfDay) throws DaoException;

}
