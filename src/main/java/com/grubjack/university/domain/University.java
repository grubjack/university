package com.grubjack.university.domain;

import com.grubjack.university.dao.ClassroomDao;
import com.grubjack.university.dao.FacultyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
@Service
public class University {

    private String name;

    private List<Classroom> rooms;
    private List<Faculty> faculties;

    @Autowired
    private FacultyDao facultyDao;
    @Autowired
    private ClassroomDao classroomDao;

    public University() {
    }

    public void createRoom(Classroom classroom) {
        if (classroom != null && !getRooms().contains(classroom)) {
            getRooms().add(classroom);
            Lesson.setAll(null);
            Timetable.setAll(null);
            classroomDao.create(classroom);
        }
    }

    public void deleteRoom(Classroom classroom) {
        if (classroom != null) {
            getRooms().remove(classroom);
            Lesson.setAll(null);
            Timetable.setAll(null);
            classroomDao.delete(classroom.getId());
        }
    }

    public void updateRoom(Classroom classroom) {
        Classroom oldRoom = classroomDao.find(classroom.getId());
        if (oldRoom != null) {
            getRooms().remove(oldRoom);
            getRooms().add(classroom);
            Lesson.setAll(null);
            Timetable.setAll(null);
            classroomDao.update(classroom);
        }
    }

    public void createFaculty(Faculty faculty) {
        if (faculty != null && !getFaculties().contains(faculty)) {
            getFaculties().add(faculty);
            facultyDao.create(faculty);
        }
    }

    public void deleteFaculty(Faculty faculty) {
        if (faculty != null) {
            getFaculties().remove(faculty);
            Group.findAll().removeAll(faculty.getGroups());
            Department.findAll().removeAll(faculty.getDepartments());
            Student.setAll(null);
            Teacher.setAll(null);
            Lesson.setAll(null);
            Timetable.setAll(null);
            facultyDao.delete(faculty.getId());
        }
    }

    public void updateFaculty(Faculty faculty) {
        Faculty oldFaculty = facultyDao.find(faculty.getId());
        if (oldFaculty != null) {
            getFaculties().remove(oldFaculty);
            getFaculties().add(faculty);
            facultyDao.update(faculty);
        }
    }

    public List<Classroom> getRooms() {
        if (rooms == null) {
            rooms = classroomDao.findAll();
        }
        Collections.sort(rooms);
        return rooms;
    }

    public void setRooms(List<Classroom> rooms) {
        this.rooms = rooms;
    }

    public List<Faculty> getFaculties() {
        if (faculties == null) {
            faculties = facultyDao.findAll();
        }
        Collections.sort(faculties);
        return faculties;
    }

    public void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}