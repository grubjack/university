package com.grubjack.university.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by grubjack on 28.10.2016.
 */
public class Faculty extends BaseEntity {
    private String name;
    private Timetable timetable;
    private List<Department> departments;
    private List<Group> groups;


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
            groups.add(group);
        }
    }

    public void deleteGroup(Group group) {
        groups.remove(group);
    }

    public void updateGroup(Group group) {
        int index = groups.indexOf(group);
        if (index != -1) {
            groups.remove(group);
            groups.add(index, group);
        }
    }


    public void createDepartment(Department department) {
        if (department != null && !departments.contains(department)) {
            departments.add(department);
        }
    }

    public void deleteDepartment(Department department) {
        departments.remove(department);
    }

    public void updateDepartment(Department department) {
        int index = departments.indexOf(department);
        if (index != -1) {
            departments.remove(department);
            departments.add(index, department);
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
        TimetableUnit unit = timetable.findUnit(dayOfWeek);
        if (unit != null) {
            List<Lesson> lessons = new ArrayList<>();
            for (Lesson lesson : unit.getLessons()) {
                if (lesson.getTeacher().equals(teacher)) {
                    lessons.add(lesson);
                }
            }
            result.setLessons(lessons);
        }
        return result;
    }

    public Timetable findTimetable(Teacher teacher) {
        Timetable result = new Timetable();
        if (teacher != null) {
            result.setName("Timetable " + teacher.getFirstName() + " " + teacher.getLastName());
            Map<DayOfWeek, TimetableUnit> units = new HashMap<>();
            for (DayOfWeek dayOfWeek : timetable.getUnits().keySet()) {
                TimetableUnit unit = findDayTimetable(teacher, dayOfWeek);
                if (unit.getLessons().size() > 0) {
                    units.put(dayOfWeek, unit);
                }
            }
            result.setUnits(units);
        }
        return result;
    }

    public TimetableUnit findDayTimetable(Group group, DayOfWeek dayOfWeek) {
        TimetableUnit result = new TimetableUnit();
        TimetableUnit unit = timetable.findUnit(dayOfWeek);
        if (group != null && unit != null) {
            List<Lesson> lessons = new ArrayList<>();
            for (Lesson lesson : unit.getLessons()) {
                if (lesson.getGroup().equals(group)) {
                    lessons.add(lesson);
                }
            }
            result.setLessons(lessons);
        }
        return result;
    }

    public Timetable findTimetable(Group group) {
        Timetable result = new Timetable();
        if (group != null) {
            result.setName("Timetable " + group.getName());
            Map<DayOfWeek, TimetableUnit> units = new HashMap<>();
            for (DayOfWeek dayOfWeek : timetable.getUnits().keySet()) {
                TimetableUnit unit = findDayTimetable(group, dayOfWeek);
                if (unit.getLessons().size() > 0) {
                    units.put(dayOfWeek, unit);
                }
            }
            result.setUnits(units);
        }
        return result;
    }

    public Group findGroup(Student student) {
        for (Group group : groups) {
            if (group.getStudents().contains(student))
                return group;
        }
        return null;
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
