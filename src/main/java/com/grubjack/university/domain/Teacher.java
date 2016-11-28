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
@Table(name = "teachers")
public class Teacher extends Person {

    @Column(name = "salary", nullable = false)
    private int salary;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    @JsonIgnore
    private Department department;

    @Transient
    private static PersonDao<Teacher> teacherDao;

    @Transient
    @JsonIgnore
    private static List<Teacher> teachers;

    public Teacher() {
        if (AbstractHttpServlet.getContext() != null) {
            teacherDao = (PersonDao<Teacher>) AbstractHttpServlet.getContext().getBean("teacherDao");
        }
    }

    public Teacher(String firstName, String lastName, int salary) {
        super(firstName, lastName);
        this.salary = salary;
        if (AbstractHttpServlet.getContext() != null) {
            teacherDao = (PersonDao<Teacher>) AbstractHttpServlet.getContext().getBean("teacherDao");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        if (!super.equals(o)) return false;

        Teacher teacher = (Teacher) o;

        return salary == teacher.salary;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + salary;
        return result;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public static List<Teacher> findAll() {
        if (teachers == null) {
            teachers = teacherDao.findAll();
        }
        Collections.sort(teachers);
        return teachers;
    }

    public static void setAll(List<Teacher> teachers) {
        Teacher.teachers = teachers;
    }

    public static List<Teacher> findByName(String name) {
        List<Teacher> result = new ArrayList<>();
        for (Teacher teacher : findAll()) {
            if (teacher.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(teacher);
            }
        }
        return result;
    }

    public static Teacher findById(int id) {
        return teacherDao.find(id);
    }

    public static List<Teacher> findAvailable(DayOfWeek day, TimeOfDay time) {
        return teacherDao.findAvailable(day, time);
    }


}
