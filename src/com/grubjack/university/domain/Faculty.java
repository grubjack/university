package com.grubjack.university.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by grubjack on 28.10.2016.
 */
public class Faculty {
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


    public TimetableUnit findStudentTimetableUnit(Student student, DayOfWeek dayOfWeek) {
        return findGroupTimetableUnit(findStudentGroup(student), dayOfWeek);
    }

    public Timetable findStudentTimetable(Student student) {
        Timetable result = findGroupTimetable(findStudentGroup(student));
        if (student != null) {
            result.setName("Timetable " + student.getFirstName() + " " + student.getLastName());
        }
        return result;
    }

    public TimetableUnit findTeacherTimetableUnit(Teacher teacher, DayOfWeek dayOfWeek) {
        TimetableUnit result = new TimetableUnit();
        TimetableUnit unit = timetable.findUnit(dayOfWeek);
        if (unit != null) {
            List<Lesson> lessons = new ArrayList<>();
            for (Lesson lesson : unit.getLessons()) {
                if (lesson.getTeacher() == teacher) {
                    lessons.add(lesson);
                }
            }
            result.setLessons(lessons);
        }
        return result;
    }

    public Timetable findTeacherTimetable(Teacher teacher) {
        Timetable result = new Timetable();
        if (teacher != null) {
            result.setName("Timetable " + teacher.getFirstName() + " " + teacher.getLastName());
            Map<DayOfWeek, TimetableUnit> units = new HashMap<>();
            for (DayOfWeek dayOfWeek : timetable.getUnits().keySet()) {
                TimetableUnit unit = findTeacherTimetableUnit(teacher, dayOfWeek);
                if (unit.getLessons().size() > 0) {
                    units.put(dayOfWeek, unit);
                }
            }
            result.setUnits(units);
        }
        return result;
    }

    public TimetableUnit findGroupTimetableUnit(Group group, DayOfWeek dayOfWeek) {
        TimetableUnit result = new TimetableUnit();
        TimetableUnit unit = timetable.findUnit(dayOfWeek);
        if (group != null && unit != null) {
            List<Lesson> lessons = new ArrayList<>();
            for (Lesson lesson : unit.getLessons()) {
                if (lesson.getGroup() == group) {
                    lessons.add(lesson);
                }
            }
            result.setLessons(lessons);
        }
        return result;
    }

    public Timetable findGroupTimetable(Group group) {
        Timetable result = new Timetable();
        if (group != null) {
            result.setName("Timetable " + group.getName());
            Map<DayOfWeek, TimetableUnit> units = new HashMap<>();
            for (DayOfWeek dayOfWeek : timetable.getUnits().keySet()) {
                TimetableUnit unit = findGroupTimetableUnit(group, dayOfWeek);
                if (unit.getLessons().size() > 0) {
                    units.put(dayOfWeek, unit);
                }
            }
            result.setUnits(units);
        }
        return result;
    }

    public Group findStudentGroup(Student student) {
        for (Group group : groups) {
            if (group.getStudents().contains(student))
                return group;
        }
        return null;
    }
}
