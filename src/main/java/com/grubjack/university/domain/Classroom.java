package com.grubjack.university.domain;

import javax.persistence.*;

/**
 * Created by grubjack on 28.10.2016.
 */
@Entity
@Table(name = "classrooms", uniqueConstraints = {@UniqueConstraint(columnNames = "number", name = "classrooms_unique_number_idx")})
public class Classroom implements Comparable<Classroom> {

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private int id;

    @Column(name = "number", nullable = false, unique = true)
    private String number;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    public Classroom() {
    }

    public Classroom(String number, String location, int capacity) {
        this.number = number;
        this.location = location;
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Classroom classroom = (Classroom) o;

        if (capacity != classroom.capacity) return false;
        if (number != null ? !number.equals(classroom.number) : classroom.number != null) return false;
        return location != null ? location.equals(classroom.location) : classroom.location == null;

    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + capacity;
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public int compareTo(Classroom o) {
        return number.compareTo(o.getNumber());
    }
}
