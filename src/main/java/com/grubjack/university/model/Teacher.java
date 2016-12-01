package com.grubjack.university.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by grubjack on 28.10.2016.
 */
@NamedQueries({
        @NamedQuery(name = "Teacher.findAvailableByDayAndTime", query = "SELECT t FROM Teacher t WHERE t.id NOT IN (SELECT lt.id FROM Lesson l INNER JOIN l.teacher lt WHERE l.dayOfWeek = ?1 AND l.timeOfDay = ?2) ORDER BY t.lastName,t.firstName"),
        @NamedQuery(name = "Teacher.findTeachersByName", query = "SELECT t FROM Teacher t WHERE LOWER(t.firstName) LIKE  CONCAT('%',LOWER(?1),'%') OR LOWER(t.lastName) LIKE CONCAT('%',LOWER(?1),'%') ORDER BY t.lastName, t.firstName"),
        @NamedQuery(name = "Teacher.findTeachersByNameAndDepartment", query = "SELECT t FROM Teacher t WHERE t.department.id = ?2 AND (LOWER(t.firstName) LIKE CONCAT('%',LOWER(?1),'%') OR LOWER(t.lastName) LIKE CONCAT('%',LOWER(?1),'%')) ORDER BY t.lastName,t.firstName")
})
@Entity
@Table(name = "teachers")
public class Teacher extends Person {

    @Column(name = "salary", nullable = false)
    private int salary;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    @JsonIgnore
    private Department department;

    public Teacher() {
    }

    public Teacher(String firstName, String lastName, int salary) {
        super(firstName, lastName);
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Teacher teacher = (Teacher) o;

        return getSalary() == teacher.getSalary();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getSalary();
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
}
