package com.grubjack.university.domain;

import com.grubjack.university.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
@Service
public class University {
    private String name;

    private List<Classroom> rooms;
    private List<Faculty> faculties;
    private List<Department> departments;
    private List<Group> groups;
    private List<Student> students;
    private List<Teacher> teachers;
    private List<Lesson> lessons;
    private List<Timetable> timetables;

    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private FacultyDao facultyDao;
    @Autowired
    private ClassroomDao classroomDao;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private PersonDao<Teacher> teacherDao;
    @Autowired
    private PersonDao<Student> studentDao;
    @Autowired
    private LessonDao lessonDao;

    public University() {
    }

    public void createRoom(Classroom classroom) {
        if (classroom != null && !getRooms().contains(classroom)) {
            getRooms().add(classroom);
            lessons = null;
            timetables = null;
            classroomDao.create(classroom);
        }
    }

    public void deleteRoom(Classroom classroom) {
        if (classroom != null) {
            getRooms().remove(classroom);
            lessons = null;
            timetables = null;
            classroomDao.delete(classroom.getId());
        }
    }

    public void updateRoom(Classroom classroom) {
        Classroom oldRoom = classroomDao.find(classroom.getId());
        if (oldRoom != null) {
            getRooms().remove(oldRoom);
            getRooms().add(classroom);
            lessons = null;
            timetables = null;
            classroomDao.update(classroom);
        }
    }

    public Classroom findByNumber(String number) {
        if (number != null) {
            return classroomDao.findByNumber(number);
        }
        return null;
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
            getGroups().removeAll(faculty.getGroups());
            getDepartments().removeAll(faculty.getDepartments());
            students = null;
            teachers = null;
            lessons = null;
            timetables = null;
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

    public List<Student> getStudents() {
        if (students == null) {
            students = studentDao.findAll();
        }
        Collections.sort(students);
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Teacher> getTeachers() {
        if (teachers == null) {
            teachers = teacherDao.findAll();
        }
        Collections.sort(teachers);
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public List<Lesson> getLessons() {
        if (lessons == null) {
            lessons = lessonDao.findAll();
        }
        Collections.sort(lessons);
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public List<Department> getDepartments() {
        if (departments == null) {
            departments = departmentDao.findAll();
        }
        Collections.sort(departments);
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
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

    public Faculty findFaculty(int id) {
        return facultyDao.find(id);
    }

    public Group findGroup(int id) {
        return groupDao.find(id);
    }

    public Teacher findTeacher(int id) {
        return teacherDao.find(id);
    }

    public Student findStudent(int id) {
        return studentDao.find(id);
    }

    public Lesson findLesson(int id) {
        return lessonDao.find(id);
    }

    public Classroom findRoom(int id) {
        return classroomDao.find(id);
    }

    public Faculty findGroupFaculty(int groupId) {
        return facultyDao.findByGroup(groupId);
    }

    public Faculty findStudentFaculty(int studentId) {
        return facultyDao.findByStudent(studentId);
    }

    public Faculty findTeacherFaculty(int teacherId) {
        return facultyDao.findByTeacher(teacherId);
    }

    public Department findDepartment(int id) {
        return departmentDao.find(id);
    }

    public List<Teacher> findAvailableTeachers(DayOfWeek day, TimeOfDay time) {
        return teacherDao.findAvailable(day, time);
    }

    public List<Classroom> findAvailableRooms(DayOfWeek day, TimeOfDay time) {
        return classroomDao.findAvailable(day, time);
    }

    public List<Group> findAvailableGroups(DayOfWeek day, TimeOfDay time) {
        return groupDao.findAvailable(day, time);
    }

    public List<Group> getGroups() {
        if (groups == null) {
            groups = groupDao.findAll();
        }
        Collections.sort(groups);
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Timetable> findGroupTimetables() {
        if (timetables == null) {

            List<Lesson> lessons = new ArrayList<>();
            lessons.addAll(getLessons());

            timetables = new ArrayList<>();

            for (Group group : getGroups()) {
                Timetable timetable = new Timetable(group.getName());
                for (DayOfWeek day : DayOfWeek.values()) {
                    TimetableUnit unit = new TimetableUnit(day);
                    for (TimeOfDay time : TimeOfDay.values()) {
                        Iterator<Lesson> iterator = lessons.iterator();
                        while (iterator.hasNext()) {
                            Lesson lesson = iterator.next();
                            Group groupLesson = lesson.getGroup();
                            if (groupLesson != null && groupLesson.getId() == group.getId() && lesson.getDayOfWeek().equals(day) && lesson.getTimeOfDay().equals(time)) {
                                unit.getLessons().add(lesson);
                                iterator.remove();
                                break;
                            }
                        }
                    }
                    timetable.getUnits().add(unit);
                }
                timetables.add(timetable);
            }
        }
        return timetables;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTimetables(List<Timetable> timetables) {
        this.timetables = timetables;
    }

    public DepartmentDao getDepartmentDao() {
        return departmentDao;
    }

    public void setDepartmentDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    public FacultyDao getFacultyDao() {
        return facultyDao;
    }

    public void setFacultyDao(FacultyDao facultyDao) {
        this.facultyDao = facultyDao;
    }

    public ClassroomDao getClassroomDao() {
        return classroomDao;
    }

    public void setClassroomDao(ClassroomDao classroomDao) {
        this.classroomDao = classroomDao;
    }

    public GroupDao getGroupDao() {
        return groupDao;
    }

    public void setGroupDao(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public PersonDao<Teacher> getTeacherDao() {
        return teacherDao;
    }

    public void setTeacherDao(PersonDao<Teacher> teacherDao) {
        this.teacherDao = teacherDao;
    }

    public PersonDao<Student> getStudentDao() {
        return studentDao;
    }

    public void setStudentDao(PersonDao<Student> studentDao) {
        this.studentDao = studentDao;
    }

    public LessonDao getLessonDao() {
        return lessonDao;
    }

    public void setLessonDao(LessonDao lessonDao) {
        this.lessonDao = lessonDao;
    }

    public List<Timetable> getTimetables() {
        return timetables;
    }


}
