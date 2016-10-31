package com.grubjack.university.domain;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by grubjack on 31.10.2016.
 */
public class GroupTest {
    
    Group group = new Group();

    @Test
    public void testNoStudents() {
        Assert.assertEquals(0, group.getStudents().size());
    }

    @Test
    public void testCreateStudent() {
        Student student1 = new Student("Ivan", "Petrov");
        group.createStudent(student1);
        group.createStudent(student1);
        Assert.assertEquals(1, group.getStudents().size());
    }

    @Test
    public void testCreateNullStudent() {
        group.createStudent(null);
        Assert.assertEquals(0, group.getStudents().size());
    }

    @Test
    public void testDeleteStudent() {
        Student student1 = new Student();
        Student student2 = new Student();
        group.createStudent(student1);
        group.createStudent(student2);
        group.deleteStudent(student1);
        Assert.assertEquals(1, group.getStudents().size());
    }

    @Test
    public void testDeleteNullStudent() {
        Student student1 = new Student();
        group.createStudent(student1);
        group.deleteStudent(null);
        Assert.assertEquals(1, group.getStudents().size());
    }

    @Test
    public void testUpdateStudent() {
        Student student1 = new Student("Petr", "Ivanov");
        Student student2 = new Student("Eduard", "Chuiko");
        group.createStudent(student1);
        group.createStudent(student2);
        student1.setFirstName("Vasya");
        group.updateStudent(student1);
        Assert.assertEquals("Vasya", group.getStudents().get(0).getFirstName());
    }

    @Test
    public void testUpdateNullStudent() {
        Student student1 = new Student("Petr", "Ivanov");
        Student student2 = new Student("Eduard", "Chuiko");
        group.createStudent(student1);
        group.createStudent(student2);
        student1 = null;
        group.updateStudent(student1);
        Assert.assertEquals(2, group.getStudents().size());
    }

    @Test
    public void testFindStudentsByFirstName() throws Exception {
        Student student1 = new Student("Petr", "Ivanov");
        Student student2 = new Student("Eduard", "Chuiko");
        group.createStudent(student1);
        group.createStudent(student2);
        Assert.assertEquals(1, group.findStudentsByFirstName("eduard").size());
    }

    @Test
    public void testFindStudentsByLastName() throws Exception {
        Student student1 = new Student("Petr", "Ivanov");
        Student student2 = new Student("Eduard", "Ivanov");
        group.createStudent(student1);
        group.createStudent(student2);
        Assert.assertEquals(2, group.findStudentsByLastName("Ivanov").size());
    }

    @Test
    public void testFindStudentsByName() throws Exception {
        Student student1 = new Student("Petr", "Ivanov");
        Student student2 = new Student("Eduard", "Chuiko");
        group.createStudent(student1);
        group.createStudent(student2);
        Assert.assertEquals(0, group.findStudentsByName("Petr", "Chuiko").size());
    }

    @Test
    public void testFindStudentsByNullName() throws Exception {
        Student student1 = new Student("Petr", "Ivanov");
        Student student2 = new Student("Eduard", "Chuiko");
        group.createStudent(student1);
        group.createStudent(student2);
        Assert.assertEquals(0, group.findStudentsByName(null, null).size());
    }

}