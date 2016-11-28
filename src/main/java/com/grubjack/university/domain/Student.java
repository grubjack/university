package com.grubjack.university.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grubjack.university.dao.PersonDao;
import com.grubjack.university.servlet.AbstractHttpServlet;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
@Entity
@Table(name = "students")
public class Student extends Person {

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    @JsonIgnore
    private Group group;

    @Transient
    private static PersonDao<Student> studentDao;

    @Transient
    @JsonIgnore
    private static List<Student> students;

    public Student() {
        if (AbstractHttpServlet.getContext() != null) {
            studentDao = (PersonDao<Student>) AbstractHttpServlet.getContext().getBean("studentDao");
        }
    }

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
        if (AbstractHttpServlet.getContext() != null) {
            studentDao = (PersonDao<Student>) AbstractHttpServlet.getContext().getBean("studentDao");
        }
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public static List<Student> findAll() {
        if (students == null) {
            students = studentDao.findAll();
        }
        Collections.sort(students);
        return students;
    }

    public static void setAll(List<Student> students) {
        Student.students = students;
    }

    public static List<Student> findByName(String name) {
        List<Student> result = new ArrayList<>();
        for (Student student : findAll()) {
            if (student.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(student);
            }
        }
        return result;
    }

    public static Student findById(int id) {
        return studentDao.find(id);
    }


}
