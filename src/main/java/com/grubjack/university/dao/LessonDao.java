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

    void create(Lesson lesson, int facultyId) throws DaoException;

    void update(Lesson lesson, int facultyId) throws DaoException;

    List<Lesson> findGroupLessons(int facultyId, int groupId, DayOfWeek dayOfWeek) throws DaoException;

    List<Lesson> findTeacherLessons(int facultyId, int teacherId, DayOfWeek dayOfWeek) throws DaoException;

}
