package com.grubjack.university.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
public class University {
    private String name;
    private List<Classroom> rooms;
    private List<Faculty> faculties;

    public University() {
        rooms = new ArrayList<>();
        faculties = new ArrayList<>();
    }

    public University(String name) {
        this.name = name;
        rooms = new ArrayList<>();
        faculties = new ArrayList<>();
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


    public void createRoom(Classroom classroom) {
        if (classroom != null && !rooms.contains(classroom))
            rooms.add(classroom);
    }

    public void deleteRoom(Classroom classroom) {
        rooms.remove(classroom);
    }

    public void updateRoom(Classroom classroom) {
        int index = rooms.indexOf(classroom);
        if (index != -1) {
            rooms.remove(classroom);
            rooms.add(index, classroom);
        }
    }

    public void createFaculty(Faculty faculty) {
        if (faculty != null && !faculties.contains(faculty))
            faculties.add(faculty);
    }

    public void deleteFaculty(Faculty faculty) {
        faculties.remove(faculty);
    }

    public void updateFaculty(Faculty faculty) {
        int index = faculties.indexOf(faculty);
        if (index != -1) {
            faculties.remove(faculty);
            faculties.add(index, faculty);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
