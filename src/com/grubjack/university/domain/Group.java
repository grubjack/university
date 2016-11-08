package com.grubjack.university.domain;

import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.PersonDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
public class Group {
    private int id;
    private String name;
    private List<Student> students;

    private PersonDao<Student> studentDao = DaoFactory.getInstance().getStudentDao();

    public Group() {
        students = new ArrayList<>();
    }

    public Group(String name) {
        this.name = name;
        students = new ArrayList<>();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (name != null ? !name.equals(group.name) : group.name != null) return false;
        return students != null ? students.equals(group.students) : group.students == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (students != null ? students.hashCode() : 0);
        return result;
    }

    public void createStudent(Student student, int groupId) {
        if (student != null && !students.contains(student)) {
            studentDao.create(student, groupId);
            students.add(student);
        }
    }

    public void deleteStudent(Student student) {
        if (student != null) {
            studentDao.delete(student.getId());
            students.remove(student);
        }
    }

    public void updateStudent(Student student, int groupId) {
        Student oldStudent = studentDao.find(student.getId());
        if (studentDao != null) {
            students.remove(oldStudent);
            students.add(student);
            studentDao.update(student, groupId);
        }
    }


    public List<Student> findStudentsByFirstName(String firstName) {
        return studentDao.findByFirstName(firstName);
    }

    public List<Student> findStudentsByLastName(String lastName) {
        return studentDao.findByLastName(lastName);
    }

    public List<Student> findStudentsByName(String firstName, String lastName) {
        return studentDao.findByName(firstName, lastName);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

}
