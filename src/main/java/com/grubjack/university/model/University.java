package com.grubjack.university.model;

import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
public class University {
    private String name;
    private List<Classroom> rooms;
    private List<Faculty> faculties;

    public University() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Classroom> getRooms() {
        return rooms;
    }

    public void setRooms(List<Classroom> rooms) {
        this.rooms = rooms;
    }

    public List<Faculty> getFaculties() {
        return faculties;
    }

    public void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;
    }
}