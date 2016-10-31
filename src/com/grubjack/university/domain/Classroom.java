package com.grubjack.university.domain;

/**
 * Created by grubjack on 28.10.2016.
 */
public class Classroom {
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

    @Override
    public String toString() {
        return "Classroom{" +
                "number='" + number + "\'}";
    }
}
