package com.grubjack.university.domain;

import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.LessonDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
public class TimetableUnit {
    private List<Lesson> lessons;

    private LessonDao lessonDao = DaoFactory.getInstance().getLessonDao();

    public TimetableUnit() {
        lessons = new ArrayList<>();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimetableUnit that = (TimetableUnit) o;

        return lessons != null ? lessons.equals(that.lessons) : that.lessons == null;

    }

    @Override
    public int hashCode() {
        return lessons != null ? lessons.hashCode() : 0;
    }

    public void createLesson(Lesson lesson) {
        if (lesson != null && !lessons.contains(lesson)) {
            lessonDao.create(lesson);
            lessons.add(lesson);
        }
    }

    public void deleteLesson(Lesson lesson) {
        if (lesson != null) {
            lessonDao.delete(lesson.getId());
            lessons.remove(lesson);
        }
    }

    public void updateLesson(Lesson lesson) {
        Lesson oldLesson = lessonDao.find(lesson.getId());
        if (oldLesson != null) {
            lessons.remove(oldLesson);
            lessons.add(lesson);
            lessonDao.update(lesson);
        }
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}
