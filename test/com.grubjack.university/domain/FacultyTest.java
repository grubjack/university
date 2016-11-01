package com.grubjack.university.domain;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by grubjack on 31.10.2016.
 */
public class FacultyTest {

    Faculty faculty = new Faculty();

    @Test
    public void testNoGroups() {
        Assert.assertEquals(0, faculty.getGroups().size());
    }

    @Test
    public void testCreateGroup() {
        Group group1 = new Group("FEL-331");
        faculty.createGroup(group1);
        faculty.createGroup(group1);
        Assert.assertEquals(1, faculty.getGroups().size());
    }

    @Test
    public void testCreateNullGroup() {
        faculty.createGroup(null);
        Assert.assertEquals(0, faculty.getGroups().size());
    }

    @Test
    public void testDeleteGroup() {
        Group group1 = new Group();
        Group group2 = new Group();
        faculty.createGroup(group1);
        faculty.createGroup(group2);
        faculty.deleteGroup(group1);
        Assert.assertEquals(1, faculty.getGroups().size());
    }

    @Test
    public void testDeleteNullGroup() {
        Group group1 = new Group();
        faculty.createGroup(group1);
        faculty.deleteGroup(null);
        Assert.assertEquals(1, faculty.getGroups().size());
    }

    @Test
    public void testUpdateGroup() {
        Group group1 = new Group("FEL-321");
        Group group2 = new Group("VITI-322");
        faculty.createGroup(group1);
        faculty.createGroup(group2);
        group1.setName("FEL-444");
        faculty.updateGroup(group1);
        Assert.assertEquals("FEL-444", faculty.getGroups().get(0).getName());
    }

    @Test
    public void testUpdateNullGroup() {
        Group group1 = new Group("FEL-321");
        Group group2 = new Group("VITI-322");
        faculty.createGroup(group1);
        faculty.createGroup(group2);
        group1 = null;
        faculty.updateGroup(group1);
        Assert.assertEquals(2, faculty.getGroups().size());
    }

    @Test
    public void testNoDepartments() {
        Assert.assertEquals(0, faculty.getDepartments().size());
    }

    @Test
    public void testCreateDepartment() {
        Department department1 = new Department("Kafedra ASU");
        faculty.createDepartment(department1);
        faculty.createDepartment(department1);
        Assert.assertEquals(1, faculty.getDepartments().size());
    }

    @Test
    public void testCreateNullDepartment() {
        faculty.createDepartment(null);
        Assert.assertEquals(0, faculty.getDepartments().size());
    }

    @Test
    public void testDeleteDepartment() {
        Department department1 = new Department();
        Department department2 = new Department();
        faculty.createDepartment(department1);
        faculty.createDepartment(department2);
        faculty.deleteDepartment(department1);
        Assert.assertEquals(1, faculty.getDepartments().size());
    }

    @Test
    public void testDeleteNullDepartment() {
        Department department1 = new Department();
        faculty.createDepartment(department1);
        faculty.deleteDepartment(null);
        Assert.assertEquals(1, faculty.getDepartments().size());
    }

    @Test
    public void testUpdateDepartment() {
        Department department1 = new Department("Kafedra ASU");
        Department department2 = new Department("Kafedra INYAZ");
        faculty.createDepartment(department1);
        faculty.createDepartment(department2);
        department1.setName("Kafedra DPU");
        faculty.updateDepartment(department1);
        Assert.assertEquals("Kafedra DPU", faculty.getDepartments().get(0).getName());
    }

    @Test
    public void testUpdateNullDepartment() {
        Department department1 = new Department("Kafedra ASU");
        Department department2 = new Department("Kafedra INYAZ");
        faculty.createDepartment(department1);
        faculty.createDepartment(department2);
        department1 = null;
        faculty.updateDepartment(department1);
        Assert.assertEquals(2, faculty.getDepartments().size());
    }


    @Test
    public void testFindStudentTimetable() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Student student = TimetableTestData.student2;
        Assert.assertEquals(2, faculty.findTimetable(student).getUnits().size());
    }

    @Test
    public void testFindStudentWithoutGroupTimetable() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Student student = new Student();
        Assert.assertEquals(0, faculty.findTimetable(student).getUnits().size());
    }

    @Test
    public void testFindNullStudentTimetable() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Student student = null;
        Assert.assertEquals(0, faculty.findTimetable(student).getUnits().size());
    }


    @Test
    public void testFindTeacherTimetable() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Teacher teacher = TimetableTestData.teacher3;
        Assert.assertEquals(1, faculty.findTimetable(teacher).getUnits().size());
    }

    @Test
    public void testFindNewTeacherTimetable() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Teacher teacher = new Teacher();
        Assert.assertEquals(0, faculty.findTimetable(teacher).getUnits().size());
    }

    @Test
    public void testFindNullTeacherTimetable() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Teacher teacher = null;
        Assert.assertEquals(0, faculty.findTimetable(teacher).getUnits().size());
    }

    @Test
    public void testFindGroupTimetable() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Group group = TimetableTestData.group1;
        Assert.assertEquals(2, faculty.findTimetable(group).getUnits().size());
    }

    @Test
    public void testFindNewGroupTimetable() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Group group = new Group();
        Assert.assertEquals(0, faculty.findTimetable(group).getUnits().size());
    }

    @Test
    public void testFindNullGroupTimetable() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Group group = null;
        Assert.assertEquals(0, faculty.findTimetable(group).getUnits().size());
    }


    @Test
    public void testFindGroupDayTimetable() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Group group = TimetableTestData.group1;
        Assert.assertEquals(2, faculty.findDayTimetable(group, DayOfWeek.MONDAY).getLessons().size());
    }

    @Test
    public void testFindNewGroupDayTimetable() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Group group = new Group();
        Assert.assertEquals(0, faculty.findDayTimetable(group, DayOfWeek.MONDAY).getLessons().size());
    }

    @Test
    public void testFindNullGroupDayTimetable() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Group group = null;
        Assert.assertEquals(0, faculty.findDayTimetable(group, DayOfWeek.MONDAY).getLessons().size());
    }

    @Test
    public void testFindStudentDayTimetable() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Student student = TimetableTestData.student2;
        Assert.assertEquals(1, faculty.findDayTimetable(student, DayOfWeek.THURSDAY).getLessons().size());
    }

    @Test
    public void testFindNewStudentDayTimetable() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Student student = new Student();
        Assert.assertEquals(0, faculty.findDayTimetable(student, DayOfWeek.THURSDAY).getLessons().size());
    }

    @Test
    public void testFindNullStudentDayTimetable() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Student student = null;
        Assert.assertEquals(0, faculty.findDayTimetable(student, DayOfWeek.THURSDAY).getLessons().size());
    }

    @Test
    public void testFindTeacherDayTimetable() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Teacher teacher = TimetableTestData.teacher2;
        Assert.assertEquals(1, faculty.findDayTimetable(teacher, DayOfWeek.MONDAY).getLessons().size());
    }

    @Test
    public void testFindNewTeacherDayTimetable() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Teacher teacher = new Teacher();
        Assert.assertEquals(0, faculty.findDayTimetable(teacher, DayOfWeek.MONDAY).getLessons().size());
    }

    @Test
    public void testFindNullTeacherDayTimetable() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Teacher teacher = null;
        Assert.assertEquals(0, faculty.findDayTimetable(teacher, DayOfWeek.MONDAY).getLessons().size());
    }

    @Test
    public void testStudentGroup() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Student student = TimetableTestData.student2;
        Assert.assertEquals(TimetableTestData.group1.getName(), faculty.findGroup(student).getName());
    }

    @Test
    public void testNewStudentGroup() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Student student = new Student();
        Assert.assertNull(faculty.findGroup(student));
    }

    @Test
    public void testNullStudentGroup() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Assert.assertNull(faculty.findGroup(null));
    }


}