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
        Assert.assertEquals(2, faculty.findStudentTimetable(student).getUnits().size());
    }

    @Test
    public void testFindStudentWithoutGroupTimetable() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Student student = new Student();
        Assert.assertEquals(0, faculty.findStudentTimetable(student).getUnits().size());
    }

    @Test
    public void testFindNullStudentTimetable() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Assert.assertEquals(0, faculty.findStudentTimetable(null).getUnits().size());
    }


    @Test
    public void testFindTeacherTimetable() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Teacher teacher = TimetableTestData.teacher3;
        Assert.assertEquals(1, faculty.findTeacherTimetable(teacher).getUnits().size());
    }

    @Test
    public void testFindNewTeacherTimetable() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Teacher teacher = new Teacher();
        Assert.assertEquals(0, faculty.findTeacherTimetable(teacher).getUnits().size());
    }

    @Test
    public void testFindNullTeacherTimetable() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Assert.assertEquals(0, faculty.findTeacherTimetable(null).getUnits().size());
    }

    @Test
    public void testFindGroupTimetable() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Group group = TimetableTestData.group1;
        Assert.assertEquals(2, faculty.findGroupTimetable(group).getUnits().size());
    }

    @Test
    public void testFindNewGroupTimetable() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Group group = new Group();
        Assert.assertEquals(0, faculty.findGroupTimetable(group).getUnits().size());
    }

    @Test
    public void testFindNullGroupTimetable() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Assert.assertEquals(0, faculty.findGroupTimetable(null).getUnits().size());
    }


    @Test
    public void testFindGroupTimetableUnit() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Group group = TimetableTestData.group1;
        Assert.assertEquals(2, faculty.findGroupTimetableUnit(group, DayOfWeek.MONDAY).getLessons().size());
    }

    @Test
    public void testFindNewGroupTimetableUnit() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Group group = new Group();
        Assert.assertEquals(0, faculty.findGroupTimetableUnit(group, DayOfWeek.MONDAY).getLessons().size());
    }

    @Test
    public void testFindNullGroupTimetableUnit() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Assert.assertEquals(0, faculty.findGroupTimetableUnit(null, DayOfWeek.MONDAY).getLessons().size());
    }

    @Test
    public void testFindStudentTimetableUnit() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Student student = TimetableTestData.student2;
        Assert.assertEquals(1, faculty.findStudentTimetableUnit(student, DayOfWeek.THURSDAY).getLessons().size());
    }

    @Test
    public void testFindNewStudentTimetableUnit() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Student student = new Student();
        Assert.assertEquals(0, faculty.findStudentTimetableUnit(student, DayOfWeek.THURSDAY).getLessons().size());
    }

    @Test
    public void testFindNullStudentTimetableUnit() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Assert.assertEquals(0, faculty.findStudentTimetableUnit(null, DayOfWeek.THURSDAY).getLessons().size());
    }

    @Test
    public void testFindTeacherTimetableUnit() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Teacher teacher = TimetableTestData.teacher2;
        Assert.assertEquals(1, faculty.findTeacherTimetableUnit(teacher, DayOfWeek.MONDAY).getLessons().size());
    }

    @Test
    public void testFindNewTeacherTimetableUnit() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Teacher teacher = new Teacher();
        Assert.assertEquals(0, faculty.findTeacherTimetableUnit(teacher, DayOfWeek.MONDAY).getLessons().size());
    }

    @Test
    public void testFindNullTeacherTimetableUnit() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Assert.assertEquals(0, faculty.findTeacherTimetableUnit(null, DayOfWeek.MONDAY).getLessons().size());
    }

    @Test
    public void testStudentGroup() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Student student = TimetableTestData.student2;
        Assert.assertEquals(TimetableTestData.group1.getName(), faculty.findStudentGroup(student).getName());
    }

    @Test
    public void testNewStudentGroup() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Student student = new Student();
        Assert.assertNull(faculty.findStudentGroup(student));
    }

    @Test
    public void testNullStudentGroup() {
        faculty.setGroups(TimetableTestData.groups);
        faculty.setTimetable(TimetableTestData.timetable);
        Assert.assertNull(faculty.findStudentGroup(null));
    }


}