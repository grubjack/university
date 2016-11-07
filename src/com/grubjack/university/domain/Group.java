package com.grubjack.university.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
public class Group {
    private int id;
    private String name;
    private List<Student> students;
    private Faculty faculty;

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

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
}
