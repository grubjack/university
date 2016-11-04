package com.grubjack.university.domain;

/**
 * Created by grubjack on 28.10.2016.
 */
public class Student extends Person {
    private Group group;

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
}
