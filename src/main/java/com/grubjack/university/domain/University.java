package com.grubjack.university.domain;

import com.grubjack.university.dao.*;
import com.grubjack.university.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
public class University {
    private static University instance;
    private String name;

    private List<Classroom> rooms;
    private List<Faculty> faculties;

    private List<Department> allDepartments;
    private List<Group> allGroups;
    private List<Student> allStudents;
    private List<Teacher> allTeachers;
    private List<Lesson> allLessons;
    private List<Timetable> allTimetables;

    private DepartmentDao departmentDao = DaoFactory.getInstance().getDepartmentDao();
    private FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
    private ClassroomDao classroomDao = DaoFactory.getInstance().getClassroomDao();
    private GroupDao groupDao = DaoFactory.getInstance().getGroupDao();
    private PersonDao<Teacher> teacherDao = DaoFactory.getInstance().getTeacherDao();
    private PersonDao<Student> studentDao = DaoFactory.getInstance().getStudentDao();
    private LessonDao lessonDao = DaoFactory.getInstance().getLessonDao();

    private static Logger log = LoggerFactory.getLogger(University.class);

    private University() {
    }

    public static University getInstance() {
        if (instance == null) {
            instance = new University();
        }
        return instance;
    }

    public void createRoom(Classroom classroom) {
        if (classroom != null && !getRooms().contains(classroom)) {
            try {
                classroomDao.create(classroom);
                getRooms().add(classroom);
            } catch (DaoException e) {
                log.warn("Can't create classroom");
            }
        }
    }

    public void deleteRoom(Classroom classroom) {
        if (classroom != null) {
            try {
                classroomDao.delete(classroom.getId());
                getRooms().remove(classroom);
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
                getRooms().remove(oldRoom);
                getRooms().add(classroom);
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
        if (faculty != null && !getFaculties().contains(faculty)) {
            try {
                facultyDao.create(faculty);
                getFaculties().add(faculty);
            } catch (DaoException e) {
                log.warn("Can't create faculty");
            }
        }
    }

    public void deleteFaculty(Faculty faculty) {
        if (faculty != null) {
            try {
                facultyDao.delete(faculty.getId());
                getFaculties().remove(faculty);
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
                getFaculties().remove(oldFaculty);
                getFaculties().add(faculty);
            } catch (DaoException e) {
                log.warn("Can't update faculty");
            }
        }
    }

    public List<Student> getAllStudents() {
        if (allStudents == null) {
            try {
                allStudents = studentDao.findAll();
            } catch (DaoException e) {
                log.warn("Can't find all students");
            }
        }
        Collections.sort(allStudents);
        return allStudents;
    }

    public List<Teacher> getAllTeachers() {
        if (allTeachers == null) {
            try {
                allTeachers = teacherDao.findAll();
            } catch (DaoException e) {
                log.warn("Can't find all teachers");
            }
        }
        Collections.sort(allTeachers);
        return allTeachers;
    }

    public List<Lesson> getAllLessons() {
        if (allLessons == null) {
            try {
                allLessons = lessonDao.findAll();
            } catch (DaoException e) {
                log.warn("Can't find all lessons");
            }
        }
        Collections.sort(allLessons);
        return allLessons;
    }

    public List<Department> getAllDepartments() {
        if (allDepartments == null) {
            try {
                allDepartments = departmentDao.findAll();
            } catch (DaoException e) {
                log.warn("Can't find all departments");
            }
        }
        Collections.sort(allDepartments);
        return allDepartments;
    }


    public List<Classroom> getRooms() {
        if (rooms == null) {
            try {
                rooms = classroomDao.findAll();
            } catch (DaoException e) {
                log.warn("Can't find classrooms");
            }
        }
        Collections.sort(rooms);
        return rooms;
    }

    public void setRooms(List<Classroom> rooms) {
        this.rooms = rooms;
    }

    public List<Faculty> getFaculties() {
        if (faculties == null) {
            try {
                faculties = facultyDao.findAll();
            } catch (DaoException e) {
                log.warn("Can't find faculties");
            }
        }
        Collections.sort(faculties);
        return faculties;
    }

    public void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;
    }

    public List<Group> getAllGroups() {
        if (allGroups == null) {
            try {
                allGroups = groupDao.findAll();
            } catch (DaoException e) {
                log.warn("Can't find all groups");
            }
        }
        Collections.sort(allGroups);
        return allGroups;
    }

    public List<Timetable> findGroupTimetables() {
        if (allTimetables == null) {

            List<Lesson> lessons = new ArrayList<>();
            lessons.addAll(getAllLessons());

            allTimetables = new ArrayList<>();

            for (Group group : getAllGroups()) {
                Timetable timetable = new Timetable(group.getName());
                timetable.setUnits(new ArrayList<TimetableUnit>());
                for (DayOfWeek day : DayOfWeek.values()) {
                    TimetableUnit unit = new TimetableUnit(day);
                    unit.setLessons(new ArrayList<Lesson>());
                    for (TimeOfDay time : TimeOfDay.values()) {
                        Iterator<Lesson> iterator = lessons.iterator();
                        while (iterator.hasNext()) {
                            Lesson lesson = iterator.next();
                            if (lesson.getGroup().getId() == group.getId() && lesson.getDayOfWeek().equals(day) && lesson.getTimeOfDay().equals(time)) {
                                unit.getLessons().add(lesson);
                                iterator.remove();
                                break;
                            }
                        }
                    }
                    timetable.getUnits().add(unit);
                }
                allTimetables.add(timetable);
            }
        }
        return allTimetables;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
