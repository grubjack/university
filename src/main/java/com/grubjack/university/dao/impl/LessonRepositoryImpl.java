package com.grubjack.university.dao.impl;

import com.grubjack.university.model.Lesson;
import com.grubjack.university.dao.LessonDao;
import com.grubjack.university.dao.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by grubjack on 30.11.2016.
 */
@Repository
public class LessonRepositoryImpl implements LessonDao {

    @Autowired
    private LessonRepository lessonRepository;

    @Override
    public void delete(int id) {
        lessonRepository.delete(id);
    }

    @Override
    public Lesson find(int id) {
        return lessonRepository.findOne(id);
    }

    @Override
    public List<Lesson> findAll() {
        return lessonRepository.findAllByOrderByDayOfWeekAscTimeOfDayAsc();
    }

    @Override
    public void create(Lesson lesson) {
        update(lesson);
    }

    @Override
    public void update(Lesson lesson) {
        lessonRepository.save(lesson);
    }

    @Override
    public List<Lesson> findGroupLessons(int groupId) {
        return lessonRepository.findByGroupId(groupId);
    }

    @Override
    public List<Lesson> findTeacherLessons(int teacherId) {
        return lessonRepository.findByTeacherId(teacherId);
    }
}