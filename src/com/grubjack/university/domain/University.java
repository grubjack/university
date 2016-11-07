package com.grubjack.university.domain;

import com.grubjack.university.dao.ClassroomDao;
import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.FacultyDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
public class University {
    private static University instance;
    private String name;
    private List<Classroom> rooms;
    private List<Faculty> faculties;

    private FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
    private ClassroomDao classroomDao = DaoFactory.getInstance().getClassroomDao();

    private University() {
        rooms = new ArrayList<>();
        faculties = new ArrayList<>();
    }

    public static University getInstance() {
        if (instance == null) {
            instance = new University();
        }
        return instance;
    }

    public void createRoom(Classroom classroom) {
        if (classroom != null && !rooms.contains(classroom)) {
            classroomDao.create(classroom);
            rooms.add(classroom);
        }
    }

    public void deleteRoom(Classroom classroom) {
        if (classroom != null) {
            classroomDao.delete(classroom.getId());
            rooms.remove(classroom);
        }
    }

    public void updateRoom(Classroom classroom) {
        Classroom oldRoom = classroomDao.find(classroom.getId());
        if (oldRoom != null) {
            rooms.remove(oldRoom);
            rooms.add(classroom);
            classroomDao.update(classroom);
        }
    }

    public void createFaculty(Faculty faculty) {
        if (faculty != null && !faculties.contains(faculty)) {
            facultyDao.create(faculty);
            faculties.add(faculty);
        }
    }

    public void deleteFaculty(Faculty faculty) {
        if (faculty != null) {
            facultyDao.delete(faculty.getId());
            faculties.remove(faculty);
        }
    }

    public void updateFaculty(Faculty faculty) {
        Faculty oldFaculty = facultyDao.find(faculty.getId());
        if (oldFaculty != null) {
            faculties.remove(oldFaculty);
            faculties.add(faculty);
            facultyDao.update(faculty);
        }
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
