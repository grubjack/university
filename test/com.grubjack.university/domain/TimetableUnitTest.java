package com.grubjack.university.domain;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by grubjack on 31.10.2016.
 */
public class TimetableUnitTest {

    TimetableUnit unit = new TimetableUnit();

    @Test
    public void testNoLessons() {
        Assert.assertEquals(0, unit.getLessons().size());
    }

    @Test
    public void testCreateLesson() {
        Classroom room = new Classroom("140a", "1 floor", 50);
        Teacher teacher = new Teacher("Petr", "Ivanov", 20000);
        Group group = new Group("FEL-322");
        Lesson lesson = new Lesson("Mathematics", room, teacher, group);
        unit.createLesson(lesson);
        unit.createLesson(lesson);
        Assert.assertEquals(1, unit.getLessons().size());
    }

    @Test
    public void testCreateNullLesson() {
        unit.createLesson(null);
        Assert.assertEquals(0, unit.getLessons().size());
    }

    @Test
    public void testDeleteLesson() {
        Lesson lesson1 = new Lesson();
        Lesson lesson2 = new Lesson();
        unit.createLesson(lesson1);
        unit.createLesson(lesson2);
        unit.deleteLesson(lesson1);
        Assert.assertEquals(1, unit.getLessons().size());
    }

    @Test
    public void testDeleteNullLesson() {
        Lesson lesson1 = new Lesson();
        unit.createLesson(lesson1);
        unit.deleteLesson(null);
        Assert.assertEquals(1, unit.getLessons().size());
    }

    @Test
    public void testUpdateLesson() {
        Classroom room1 = new Classroom("140a", "1 floor", 50);
        Classroom room2 = new Classroom("159", "Floor 4", 10);
        Teacher teacher1 = new Teacher("Petr", "Ivanov", 20000);
        Teacher teacher2 = new Teacher("Eduard", "Chuiko", 15000);
        Group group1 = new Group("FEL-322");
        Group group2 = new Group("VITI-321");
        Lesson lesson1 = new Lesson("Mathematics", room1, teacher1, group1);
        Lesson lesson2 = new Lesson("English", room2, teacher2, group2);
        unit.createLesson(lesson1);
        unit.createLesson(lesson2);
        lesson1.setSubject("Art");
        unit.updateLesson(lesson1);
        Assert.assertEquals("Art", unit.getLessons().get(0).getSubject());
    }

    @Test
    public void testUpdateNullLesson() {
        Classroom room1 = new Classroom("140a", "1 floor", 50);
        Classroom room2 = new Classroom("159", "Floor 4", 10);
        Teacher teacher1 = new Teacher("Petr", "Ivanov", 20000);
        Teacher teacher2 = new Teacher("Eduard", "Chuiko", 15000);
        Group group1 = new Group("FEL-322");
        Group group2 = new Group("VITI-321");
        Lesson lesson1 = new Lesson("Mathematics", room1, teacher1, group1);
        Lesson lesson2 = new Lesson("English", room2, teacher2, group2);
        unit.createLesson(lesson1);
        unit.createLesson(lesson2);
        lesson1 = null;
        unit.updateLesson(lesson1);
        Assert.assertEquals(2, unit.getLessons().size());
    }

}