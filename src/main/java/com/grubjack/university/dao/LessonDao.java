package com.grubjack.university.dao;

import com.grubjack.university.domain.DayOfWeek;
import com.grubjack.university.domain.Lesson;

import java.util.List;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface LessonDao extends BaseDao<Lesson> {

    void create(Lesson lesson);

    void update(Lesson lesson);

    List<Lesson> findFacultyLessons(int facultyId);

    List<Lesson> findGroupLessons(int groupId);

}
