package com.grubjack.university.domain;

/**
 * Created by grubjack on 28.10.2016.
 */
public class Lesson {
    private String subject;
    private Classroom classroom;
    private Teacher teacher;
    private Group group;

    public Lesson() {
    }

    public Lesson(String subject) {
        this.subject = subject;
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
}
