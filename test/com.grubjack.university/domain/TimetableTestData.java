package com.grubjack.university.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grubjack on 31.10.2016.
 */
public class TimetableTestData {
    public static final Classroom room1 = new Classroom("1", "1 floor", 150);
    public static final Classroom room2 = new Classroom("2", "1 floor", 200);
    public static final Classroom room3 = new Classroom("3", "2 floor", 200);
    public static final Classroom room4 = new Classroom("4", "2 floor", 50);

    public static final Student student1 = new Student("Cassandra", "Medina");
    public static final Student student2 = new Student("Ann", "Alexander");
    public static final Student student3 = new Student("Casey", "Ball");
    public static final Student student4 = new Student("Kendra", "Webster");
    public static final Student student5 = new Student("Joshua", "Coleman");
    public static final Student student6 = new Student("Leonard", "Owens");
    public static final Student student7 = new Student("Antonia", "Alvarez");
    public static final Student student8 = new Student("Greg", "Jimenez");
    public static final Student student9 = new Student("Marion", "Guzman");
    public static final Student student10 = new Student("Marco", "Houston");
    public static final Student student11 = new Student("Ramiro", "Jones");
    public static final Student student12 = new Student("Patsy", "Hall");

    public static final Group group1 = new Group("FEL-321");
    public static final Group group2 = new Group("FEL-322");
    public static final Group group3 = new Group("VITI-321");

    public static final Teacher teacher1 = new Teacher("Boyd", "Crawford", 1000);
    public static final Teacher teacher2 = new Teacher("Grace", "Hampton", 2000);
    public static final Teacher teacher3 = new Teacher("Van", "Francis", 1200);
    public static final Teacher teacher4 = new Teacher("Jenna", "Murphy", 2500);
    public static final Teacher teacher5 = new Teacher("Vivian", "Lyons", 1600);

    public static final Lesson lesson1 = new Lesson("Mathematics");
    public static final Lesson lesson2 = new Lesson("Science");
    public static final Lesson lesson3 = new Lesson("Art");
    public static final Lesson lesson4 = new Lesson("Dramatics");
    public static final Lesson lesson5 = new Lesson("Reading");
    public static final Lesson lesson6 = new Lesson("English");
    public static final Lesson lesson7 = new Lesson("Algebra");

    public static final TimetableUnit unit1 = new TimetableUnit();
    public static final TimetableUnit unit2 = new TimetableUnit();

    public static final List<Group> groups = new ArrayList<>();

    public static final Timetable timetable = new Timetable("FEL Faculty Timetable");


    static {
        groups.add(group1);
        groups.add(group2);
        groups.add(group3);


        group1.createStudent(student1);
        group1.createStudent(student2);
        group1.createStudent(student3);
        group1.createStudent(student4);
        group1.createStudent(student5);
        group1.createStudent(student6);
        group1.createStudent(student7);
        group1.createStudent(student8);

        group2.createStudent(student9);
        group2.createStudent(student10);

        group3.createStudent(student11);
        group3.createStudent(student12);

        lesson1.setClassroom(room1);
        lesson1.setTeacher(teacher1);
        lesson1.setGroup(group1);

        lesson2.setClassroom(room2);
        lesson2.setTeacher(teacher1);
        lesson2.setGroup(group3);

        lesson3.setClassroom(room3);
        lesson3.setTeacher(teacher2);
        lesson3.setGroup(group1);

        lesson4.setClassroom(room4);
        lesson4.setTeacher(teacher2);
        lesson4.setGroup(group2);

        lesson5.setClassroom(room1);
        lesson5.setTeacher(teacher4);
        lesson5.setGroup(group2);

        lesson6.setClassroom(room2);
        lesson6.setTeacher(teacher5);
        lesson6.setGroup(group2);

        lesson7.setClassroom(room3);
        lesson7.setTeacher(teacher3);
        lesson7.setGroup(group1);

        unit1.createLesson(lesson1);
        unit1.createLesson(lesson2);
        unit1.createLesson(lesson3);

        unit2.createLesson(lesson4);
        unit2.createLesson(lesson5);
        unit2.createLesson(lesson6);
        unit2.createLesson(lesson7);

        timetable.createUnit(DayOfWeek.MONDAY, unit1);
        timetable.createUnit(DayOfWeek.THURSDAY, unit2);
    }
}
