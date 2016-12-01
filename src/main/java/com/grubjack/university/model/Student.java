package com.grubjack.university.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by grubjack on 28.10.2016.
 */
@NamedQueries({
        @NamedQuery(name = "Student.findStudentsByName", query = "SELECT s FROM Student s WHERE LOWER(s.firstName) LIKE CONCAT('%',LOWER(?1),'%') OR LOWER(s.lastName) LIKE CONCAT('%',LOWER(?1),'%') ORDER BY s.lastName,s.firstName"),
        @NamedQuery(name = "Student.findStudentsByNameAndGroup", query = "SELECT s FROM Student s WHERE s.group.id = ?2 AND ( LOWER(s.firstName) LIKE CONCAT('%',LOWER(?1),'%') OR LOWER(s.lastName) LIKE CONCAT('%',LOWER(?1),'%') ) ORDER BY s.lastName,s.firstName")
})
@Entity
@Table(name = "students")
public class Student extends Person {

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    @JsonIgnore
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
