package com.grubjack.university.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
public class Group {

    private String name;
    private List<Student> students;

    public Group() {
        students = new ArrayList<>();
    }

    public Group(String name) {
        this.name = name;
        students = new ArrayList<>();
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

    public void createStudent(Student student) {
        if (student != null && !students.contains(student))
            students.add(student);
    }

    public void deleteStudent(Student student) {
        students.remove(student);
    }

    public void updateStudent(Student student) {
        int index = students.indexOf(student);
        if (index != -1) {
            students.remove(student);
            students.add(index, student);
        }
    }


    public List<Student> findStudentsByFirstName(String firstName) {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getFirstName().equalsIgnoreCase(firstName)) {
                result.add(student);
            }
        }
        return result;
    }

    public List<Student> findStudentsByLastName(String lastName) {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getLastName().equalsIgnoreCase(lastName)) {
                result.add(student);
            }
        }
        return result;
    }

    public List<Student> findStudentsByName(String firstName, String lastName) {
        List<Student> result = new ArrayList<>();

        for (Student student : students) {
            if (student.getFirstName().equalsIgnoreCase(firstName) && student.getLastName().equalsIgnoreCase(lastName)) {
                result.add(student);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + "\'}";
    }
}
