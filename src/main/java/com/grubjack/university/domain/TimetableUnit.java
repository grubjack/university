package com.grubjack.university.domain;

import com.grubjack.university.dao.LessonDao;
import com.grubjack.university.servlet.AbstractHttpServlet;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
public class TimetableUnit {
    private DayOfWeek dayOfWeek;
    private List<Lesson> lessons = new ArrayList<>();

    private LessonDao lessonDao = (LessonDao) AbstractHttpServlet.getContext().getBean("lessonDaoImpl");
    private University university = (University) AbstractHttpServlet.getContext().getBean("university");

    public TimetableUnit(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;

    }

    public TimetableUnit() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimetableUnit that = (TimetableUnit) o;

        if (dayOfWeek != that.dayOfWeek) return false;
        return lessons != null ? lessons.equals(that.lessons) : that.lessons == null;

    }

    @Override
    public int hashCode() {
        int result = dayOfWeek != null ? dayOfWeek.hashCode() : 0;
        result = 31 * result + (lessons != null ? lessons.hashCode() : 0);
        return result;
    }

    public Lesson findLesson(String time) {
        for (Lesson lesson : lessons) {
            if (lesson.getTimeOfDay().toString().equals(time)) {
                return lesson;
            }
        }
        return null;
    }

    public void createLesson(Lesson lesson) {
        if (lesson != null && !lessons.contains(lesson)) {
            lessons.add(lesson);
            university.getLessons().add(lesson);
            university.setTimetables(null);
            lessonDao.create(lesson);
        }
    }

    public void deleteLesson(Lesson lesson) {
        if (lesson != null) {
            lessons.remove(lesson);
            university.getLessons().remove(lesson);
            university.setTimetables(null);
            lessonDao.delete(lesson.getId());
        }
    }

    public void updateLesson(Lesson lesson) {
        Lesson oldLesson = lessonDao.find(lesson.getId());
        if (oldLesson != null) {
            int index = lessons.indexOf(oldLesson);
            int index2 = university.getLessons().indexOf(oldLesson);
            if (index != -1) {
                lessons.set(index, lesson);
            }
            if (index2 != -1) {
                university.getLessons().set(index2, lesson);
            }
            lessonDao.update(lesson);
        }
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }


}
