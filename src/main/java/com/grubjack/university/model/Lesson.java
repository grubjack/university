package com.grubjack.university.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grubjack.university.dao.LessonDao;
import com.grubjack.university.servlet.AbstractHttpServlet;

import javax.persistence.*;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
@Entity
@Table(name = "lessons", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"week_day", "day_time", "room_id"}, name = "lessons_unique_day_time_room_idx"),
        @UniqueConstraint(columnNames = {"week_day", "day_time", "teacher_id"}, name = "lessons_unique_day_time_teacher_idx"),
        @UniqueConstraint(columnNames = {"week_day", "day_time", "group_id"}, name = "lessons_unique_day_time_group_idx")})
public class Lesson {

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private int id;

    @Column(name = "subject", nullable = false, unique = true)
    private String subject;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Classroom classroom;

    @Enumerated(EnumType.STRING)
    @Column(name = "week_day")
    private DayOfWeek dayOfWeek;

    @Convert(converter = DayTimeEnumConverter.class)
    @Column(name = "day_time")
    private TimeOfDay timeOfDay;

    @Transient
    private static LessonDao lessonDao;

    @Transient
    @JsonIgnore
    private static List<Lesson> lessons;

    public Lesson() {
        if (AbstractHttpServlet.getContext() != null) {
            lessonDao = (LessonDao) AbstractHttpServlet.getContext().getBean("lessonDaoImpl");
        }
    }

    public Lesson(String subject) {
        this();
        this.subject = subject;
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

}
