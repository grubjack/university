package com.grubjack.university.domain;

/**
 * Created by grubjack on 28.10.2016.
 */
public class Teacher extends Person {
    private int salary;

    public Teacher() {
    }

    public Teacher(String firstName, String lastName, int salary) {
        super(firstName, lastName);
        this.salary = salary;
    }


    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
