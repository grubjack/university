package com.grubjack.university.domain;

/**
 * Created by grubjack on 28.10.2016.
 */
public class Classroom extends BaseEntity {
    private String number;
    private String location;
    private int size;

    public Classroom() {
    }

    public Classroom(String number, String location, int size) {
        this.number = number;
        this.location = location;
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Classroom classroom = (Classroom) o;

        if (size != classroom.size) return false;
        if (number != null ? !number.equals(classroom.number) : classroom.number != null) return false;
        return location != null ? location.equals(classroom.location) : classroom.location == null;

    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + size;
        return result;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
