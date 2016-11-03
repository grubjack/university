package com.grubjack.university.domain;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by grubjack on 31.10.2016.
 */
public class DepartmentTest {

    Department department = new Department();

    @Test
    public void testNoTeachers() {
        Assert.assertEquals(0, department.getTeachers().size());
    }

    @Test
    public void testCreateTeacher() {
        Teacher teacher1 = new Teacher("Ivan", "Petrov", 16000);
        department.createTeacher(teacher1);
        department.createTeacher(teacher1);
        Assert.assertEquals(1, department.getTeachers().size());
    }

    @Test
    public void testCreateNullTeacher() {
        department.createTeacher(null);
        Assert.assertEquals(0, department.getTeachers().size());
    }

    @Test
    public void testDeleteTeacher() {
        Teacher teacher1 = new Teacher();
        Teacher teacher2 = new Teacher();
        department.createTeacher(teacher1);
        department.createTeacher(teacher2);
        department.deleteTeacher(teacher1);
        Assert.assertEquals(0, department.getTeachers().size());
    }

    @Test
    public void testDeleteNullTeacher() {
        Teacher teacher1 = new Teacher();
        department.createTeacher(teacher1);
        department.deleteTeacher(null);
        Assert.assertEquals(1, department.getTeachers().size());
    }

    @Test
    public void testUpdateTeacher() {
        Teacher teacher1 = new Teacher("Petr", "Ivanov", 20000);
        Teacher teacher2 = new Teacher("Eduard", "Chuiko", 15000);
        department.createTeacher(teacher1);
        department.createTeacher(teacher2);
        teacher1.setSalary(30000);
        department.updateTeacher(teacher1);
        List<Teacher> teachers = department.getTeachers();
        if (teachers != null && teachers.size() > 1) {
            Teacher firstTeacher = teachers.get(0);
            if (firstTeacher != null)
                Assert.assertEquals(30000, firstTeacher.getSalary());
        }
    }

    @Test
    public void testUpdateNullTeacher() {
        Teacher teacher1 = new Teacher("Petr", "Ivanov", 20000);
        Teacher teacher2 = new Teacher("Eduard", "Chuiko", 15000);
        department.createTeacher(teacher1);
        department.createTeacher(teacher2);
        teacher1 = null;
        department.updateTeacher(teacher1);
        Assert.assertEquals(2, department.getTeachers().size());
    }

    @Test
    public void testFindTeachersByFirstName() throws Exception {
        Teacher teacher1 = new Teacher("Petr", "Ivanov", 20000);
        Teacher teacher2 = new Teacher("Eduard", "Chuiko", 15000);
        department.createTeacher(teacher1);
        department.createTeacher(teacher2);
        Assert.assertEquals(1, department.findTeachersByFirstName("eduard").size());
    }

    @Test
    public void testFindTeachersByLastName() throws Exception {
        Teacher teacher1 = new Teacher("Petr", "Ivanov", 20000);
        Teacher teacher2 = new Teacher("Eduard", "Ivanov", 15000);
        department.createTeacher(teacher1);
        department.createTeacher(teacher2);
        Assert.assertEquals(2, department.findTeachersByLastName("Ivanov").size());
    }

    @Test
    public void testFindTeachersByName() throws Exception {
        Teacher teacher1 = new Teacher("Petr", "Ivanov", 20000);
        Teacher teacher2 = new Teacher("Eduard", "Chuiko", 15000);
        department.createTeacher(teacher1);
        department.createTeacher(teacher2);
        Assert.assertEquals(0, department.findTeachersByName("Petr", "Chuiko").size());
    }

    @Test
    public void testFindTeachersByNullName() throws Exception {
        Teacher teacher1 = new Teacher("Petr", "Ivanov", 20000);
        Teacher teacher2 = new Teacher("Eduard", "Chuiko", 15000);
        department.createTeacher(teacher1);
        department.createTeacher(teacher2);
        Assert.assertEquals(0, department.findTeachersByName(null, null).size());
    }

}