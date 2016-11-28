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

    private University university;

    private static LessonDao lessonDao;

    private static List<Timetable> timetables;

    public Timetable() {
        if (AbstractHttpServlet.getContext() != null) {
            this.university = (University) AbstractHttpServlet.getContext().getBean("university");
            this.lessonDao = (LessonDao) AbstractHttpServlet.getContext().getBean("lessonDaoImpl");
        }
    }

    public Timetable(String name) {
        this();
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
            Lesson.findAll().add(lesson);
            Timetable.setAll(null);
            lessonDao.create(lesson);
        }
    }

    public void deleteLesson(Lesson lesson) {
        if (lesson != null) {
            getLessons().remove(lesson);
            Lesson.findAll().remove(lesson);
            Timetable.setAll(null);
            lessonDao.delete(lesson.getId());
        }
    }

    public void updateLesson(Lesson lesson) {
        Lesson oldLesson = lessonDao.find(lesson.getId());
        if (oldLesson != null) {
            int index = getLessons().indexOf(oldLesson);
            int index2 = Lesson.findAll().indexOf(oldLesson);
            if (index != -1) {
                getLessons().set(index, lesson);
            }
            if (index2 != -1) {
                Lesson.findAll().set(index2, lesson);
            }
            Timetable.setAll(null);
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


    public static List<Timetable> findAll() {
        if (timetables == null) {
            timetables = new ArrayList<>();
            for (Group group : Group.findAll()) {
                Timetable timetable = new Timetable(group.getName());
                timetable.setLessons(lessonDao.findGroupLessons(group.getId()));
                timetables.add(timetable);
            }
        }
        return timetables;
    }

    public static void setAll(List<Timetable> timetables) {
        Timetable.timetables = timetables;
    }

}
