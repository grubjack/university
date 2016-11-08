package com.grubjack.university.domain;

import com.grubjack.university.exception.DaoException;
import com.grubjack.university.dao.ClassroomDao;
import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.FacultyDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static Logger log = LoggerFactory.getLogger(University.class);


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
            try {
                classroomDao.create(classroom);
                rooms.add(classroom);
            } catch (DaoException e) {
                log.warn("Can't create classroom");
            }
        }
    }

    public void deleteRoom(Classroom classroom) {
        if (classroom != null) {
            try {
                classroomDao.delete(classroom.getId());
                rooms.remove(classroom);
            } catch (DaoException e) {
                log.warn("Can't delete classroom");
            }
        }
    }

    public void updateRoom(Classroom classroom) {
        Classroom oldRoom = null;
        try {
            oldRoom = classroomDao.find(classroom.getId());
        } catch (DaoException e) {
            log.warn("Can't find classroom");
        }
        if (oldRoom != null) {
            try {
                classroomDao.update(classroom);
                rooms.remove(oldRoom);
                rooms.add(classroom);
            } catch (DaoException e) {
                log.warn("Can't update classroom");
            }
        }
    }

    public Classroom findByNumber(String number) {
        if (number != null) {
            try {
                return classroomDao.findByNumber(number);
            } catch (DaoException e) {
                log.warn("Can't find classroom by number");
            }
        }
        return null;
    }

    public void createFaculty(Faculty faculty) {
        if (faculty != null && !faculties.contains(faculty)) {
            try {
                facultyDao.create(faculty);
                faculties.add(faculty);
            } catch (DaoException e) {
                log.warn("Can't create faculty");
            }
        }
    }

    public void deleteFaculty(Faculty faculty) {
        if (faculty != null) {
            try {
                facultyDao.delete(faculty.getId());
                faculties.remove(faculty);
            } catch (DaoException e) {
                log.warn("Can't delete faculty");
            }
        }
    }

    public void updateFaculty(Faculty faculty) {
        Faculty oldFaculty = null;
        try {
            oldFaculty = facultyDao.find(faculty.getId());
        } catch (DaoException e) {
            log.warn("Can't find faculty");
        }
        if (oldFaculty != null) {
            try {
                facultyDao.update(faculty);
                faculties.remove(oldFaculty);
                faculties.add(faculty);
            } catch (DaoException e) {
                log.warn("Can't update faculty");
            }
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
