package com.grubjack.university.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
public class Timetable {
    private String name;
    private List<Lesson> lessons = new ArrayList<>();

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
