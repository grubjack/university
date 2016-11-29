package com.grubjack.university.dao;

import com.grubjack.university.model.Lesson;

import java.util.List;

/**
 * Created by grubjack on 02.11.2016.
 */
public interface LessonDao extends BaseDao<Lesson> {

    void create(Lesson lesson);

    void update(Lesson lesson);

    List<Lesson> findGroupLessons(int groupId);

    List<Lesson> findTeacherLessons(int teacherId);

}
