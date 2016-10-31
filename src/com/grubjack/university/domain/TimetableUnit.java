package com.grubjack.university.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
public class TimetableUnit {
    private List<Lesson> lessons;

    public TimetableUnit() {
        lessons = new ArrayList<>();
    }


    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public void createLesson(Lesson lesson) {
        if (lesson != null && !lessons.contains(lesson))
            lessons.add(lesson);
    }

    public void deleteLesson(Lesson lesson) {
        lessons.remove(lesson);
    }

    public void updateLesson(Lesson lesson) {
        int index = lessons.indexOf(lesson);
        if (index != -1) {
            lessons.remove(lesson);
            lessons.add(index, lesson);
        }
    }
}
