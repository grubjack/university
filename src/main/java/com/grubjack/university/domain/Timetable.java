package com.grubjack.university.domain;

import com.grubjack.university.dao.LessonDao;
import com.grubjack.university.servlet.AbstractHttpServlet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
public class Timetable {
    private String name;

    private List<Lesson> lessons = new ArrayList<>();

    private LessonDao lessonDao = (LessonDao) AbstractHttpServlet.getContext().getBean("lessonDaoImpl");
    private University university = (University) AbstractHttpServlet.getContext().getBean("university");

    public Timetable() {
    }

    public Timetable(String name) {
        this.name = name;

    }

    public Lesson findLesson(String day, String time) {
        for (Lesson lesson : lessons) {
            if (lesson.getDayOfWeek().toString().equalsIgnoreCase(day) && lesson.getTimeOfDay().toString().equalsIgnoreCase(time)) {
                return lesson;
            }
        }
        return null;
    }


    public void createLesson(Lesson lesson) {
        if (lesson != null && !getLessons().contains(lesson)) {
            getLessons().add(lesson);
            university.getLessons().add(lesson);
            university.setTimetables(null);
            lessonDao.create(lesson);
        }
    }

    public void deleteLesson(Lesson lesson) {
        if (lesson != null) {
            getLessons().remove(lesson);
            university.getLessons().remove(lesson);
            university.setTimetables(null);
            lessonDao.delete(lesson.getId());
        }
    }

    public void updateLesson(Lesson lesson) {
        Lesson oldLesson = lessonDao.find(lesson.getId());
        if (oldLesson != null) {
            int index = getLessons().indexOf(oldLesson);
            int index2 = university.getLessons().indexOf(oldLesson);
            if (index != -1) {
                getLessons().set(index, lesson);
            }
            if (index2 != -1) {
                university.getLessons().set(index2, lesson);
            }
            university.setTimetables(null);
            lessonDao.update(lesson);
        }
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
