package com.grubjack.university.domain;

/**
 * Created by grubjack on 28.10.2016.
 */
public class Lesson implements Comparable<Lesson> {
    private int id;
    private String subject;
    private Teacher teacher;
    private Group group;
    private Classroom classroom;
    private DayOfWeek dayOfWeek;
    private TimeOfDay timeOfDay;

    public Lesson() {
    }

    public Lesson(String subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lesson lesson = (Lesson) o;

        if (subject != null ? !subject.equals(lesson.subject) : lesson.subject != null) return false;
        if (classroom != null ? !classroom.equals(lesson.classroom) : lesson.classroom != null) return false;
        if (teacher != null ? !teacher.equals(lesson.teacher) : lesson.teacher != null) return false;
        return group != null ? group.equals(lesson.group) : lesson.group == null;

    }

    @Override
    public int hashCode() {
        int result = subject != null ? subject.hashCode() : 0;
        result = 31 * result + (classroom != null ? classroom.hashCode() : 0);
        result = 31 * result + (teacher != null ? teacher.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(TimeOfDay timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    @Override
    public int compareTo(Lesson o) {
        int day = dayOfWeek.compareTo(o.getDayOfWeek());
        if (day != 0)
            return day;
        return timeOfDay.compareTo(o.getTimeOfDay());
    }
}
