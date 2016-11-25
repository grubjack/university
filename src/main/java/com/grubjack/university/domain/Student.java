package com.grubjack.university.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grubjack.university.dao.PersonDao;
import com.grubjack.university.dao.impl.StudentDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
@Service
@Entity
@Table(name = "students")
public class Student extends Person {

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    @JsonIgnore
    private Group group;

    @Autowired
    @Qualifier(value = "studentDao")
    @Transient
    private PersonDao<Student> studentDao;

    @Transient
    @JsonIgnore
    private List<Student> students;

    public Student() {
    }

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }


    public List<Student> findAll() {
        if (students == null) {
            students = studentDao.findAll();
        }
        Collections.sort(students);
        return students;
    }

    public void setAll(List<Student> students) {
        this.students = students;
    }

    public List<Student> findByName(String name) {
        List<Student> result = new ArrayList<>();
        for (Student student : findAll()) {
            if (student.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(student);
            }
        }
        return result;
    }

    public Student findById(int id) {
        return studentDao.find(id);
    }

}
