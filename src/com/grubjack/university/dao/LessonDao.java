package com.grubjack.university.dao;

import com.grubjack.university.domain.DayOfWeek;
import com.grubjack.university.domain.Lesson;

import java.util.List;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface LessonDao extends BaseDao<Lesson> {

    List<Lesson> findGroupLessons(int facultyId, int groupId, DayOfWeek dayOfWeek);

    List<Lesson> findTeacherLessons(int facultyId, int teacherId, DayOfWeek dayOfWeek);

}
