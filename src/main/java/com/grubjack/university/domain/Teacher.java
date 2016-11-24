package com.grubjack.university.domain;

import javax.persistence.*;

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


}
