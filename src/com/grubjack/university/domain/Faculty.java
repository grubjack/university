package com.grubjack.university.domain;

import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.DepartmentDao;
import com.grubjack.university.dao.GroupDao;
import com.grubjack.university.dao.LessonDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by grubjack on 28.10.2016.
 */
public class Faculty {
    private int id;
    private String name;
    private Timetable timetable;
    private List<Department> departments;
    private List<Group> groups;

    private LessonDao lessonDao = DaoFactory.getInstance().getLessonDao();

    private DepartmentDao departmentDao = DaoFactory.getInstance().getDepartmentDao();
    private GroupDao groupDao = DaoFactory.getInstance().getGroupDao();

    public Faculty() {
        departments = new ArrayList<>();
        groups = new ArrayList<>();
    }

    public Faculty(String name) {
        this.name = name;
        departments = new ArrayList<>();
        groups = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Faculty faculty = (Faculty) o;

        if (name != null ? !name.equals(faculty.name) : faculty.name != null) return false;
        if (timetable != null ? !timetable.equals(faculty.timetable) : faculty.timetable != null) return false;
        if (departments != null ? !departments.equals(faculty.departments) : faculty.departments != null) return false;
        return groups != null ? groups.equals(faculty.groups) : faculty.groups == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (timetable != null ? timetable.hashCode() : 0);
        result = 31 * result + (departments != null ? departments.hashCode() : 0);
        result = 31 * result + (groups != null ? groups.hashCode() : 0);
        return result;
    }

    public void createGroup(Group group) {
        if (group != null && !groups.contains(group)) {
            group.setFaculty(this);
            groupDao.create(group);
            groups.add(group);
        }
    }

    public void deleteGroup(Group group) {
        if (group != null) {
            groupDao.delete(group.getId());
            group.setFaculty(null);
            groups.remove(group);
        }
    }

    public void updateGroup(Group group) {
        Group oldGroup = groupDao.find(group.getId());
        if (oldGroup != null) {
            groups.remove(oldGroup);
            group.setFaculty(this);
            groups.add(group);
            groupDao.update(group);
        }
    }


    public void createDepartment(Department department) {
        if (department != null && !departments.contains(department)) {
            department.setFaculty(this);
            departmentDao.create(department);
            departments.add(department);
        }
    }

    public void deleteDepartment(Department department) {
        if (department != null) {
            departmentDao.delete(department.getId());
            department.setFaculty(null);
            departments.remove(department);
        }
    }

    public void updateDepartment(Department department) {
        Department oldDepartment = departmentDao.find(department.getId());
        if (oldDepartment != null) {
            departments.remove(oldDepartment);
            department.setFaculty(this);
            departments.add(department);
            departmentDao.update(department);
        }
    }


    public TimetableUnit findDayTimetable(Student student, DayOfWeek dayOfWeek) {
        return findDayTimetable(findGroup(student), dayOfWeek);
    }

    public Timetable findTimetable(Student student) {
        Timetable result = findTimetable(findGroup(student));
        if (student != null) {
            result.setName("Timetable " + student.getFirstName() + " " + student.getLastName());
        }
        return result;
    }

    public TimetableUnit findDayTimetable(Teacher teacher, DayOfWeek dayOfWeek) {
        TimetableUnit result = new TimetableUnit();
        if (teacher != null) {
            List<Lesson> lessons = lessonDao.findAllByDayForFacultyTeacher(id, teacher.getId(), dayOfWeek);
            result.setLessons(lessons);
        }
        return result;
    }

    public Timetable findTimetable(Teacher teacher) {
        Timetable result = new Timetable();
        if (teacher != null) {
            result.setName("Timetable " + teacher.getFirstName() + " " + teacher.getLastName());
            Map<DayOfWeek, TimetableUnit> units = new HashMap<>();
            for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
                TimetableUnit unit = findDayTimetable(teacher, dayOfWeek);
                units.put(dayOfWeek, unit);
            }
            result.setUnits(units);
        }
        return result;


    }

    public TimetableUnit findDayTimetable(Group group, DayOfWeek dayOfWeek) {
        TimetableUnit result = new TimetableUnit();
        if (group != null) {
            List<Lesson> lessons = lessonDao.findAllByDayForFacultyGroup(id, group.getId(), dayOfWeek);
            result.setLessons(lessons);
        }
        return result;
    }

    public Timetable findTimetable(Group group) {
        Timetable result = new Timetable();
        if (group != null) {
            result.setName("Timetable " + group.getName());
            Map<DayOfWeek, TimetableUnit> units = new HashMap<>();
            for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
                TimetableUnit unit = findDayTimetable(group, dayOfWeek);
                units.put(dayOfWeek, unit);
            }
            result.setUnits(units);
        }
        return result;
    }

    public Group findGroup(Student student) {
        if (student != null)
            return student.getGroup();
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
