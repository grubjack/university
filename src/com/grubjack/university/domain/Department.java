package com.grubjack.university.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
public class Department {
    private String name;
    private List<Teacher> teachers;

    public Department() {
        teachers = new ArrayList<>();
    }

    public Department(String name) {
        this.name = name;
        teachers = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return teachers != null ? teachers.equals(that.teachers) : that.teachers == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (teachers != null ? teachers.hashCode() : 0);
        return result;
    }

    public void createTeacher(Teacher teacher) {
        if (teacher != null && !teachers.contains(teacher)) {
            teachers.add(teacher);
        }
    }

    public void deleteTeacher(Teacher teacher) {
        teachers.remove(teacher);
    }

    public void updateTeacher(Teacher teacher) {
        int index = teachers.indexOf(teacher);
        if (index != -1) {
            teachers.remove(teacher);
            teachers.add(index, teacher);
        }
    }

    public List<Teacher> findTeachersByFirstName(String firstName) {
        List<Teacher> result = new ArrayList<>();
        for (Teacher teacher : teachers) {
            if (teacher.getFirstName().equalsIgnoreCase(firstName)) {
                result.add(teacher);
            }
        }
        return result;
    }

    public List<Teacher> findTeachersByLastName(String lastName) {
        List<Teacher> result = new ArrayList<>();
        for (Teacher teacher : teachers) {
            if (teacher.getLastName().equalsIgnoreCase(lastName)) {
                result.add(teacher);
            }
        }
        return result;
    }

    public List<Teacher> findTeachersByName(String firstName, String lastName) {
        List<Teacher> result = new ArrayList<>();

        for (Teacher teacher : teachers) {
            if (teacher.getFirstName().equalsIgnoreCase(firstName) && teacher.getLastName().equalsIgnoreCase(lastName)) {
                result.add(teacher);
            }
        }
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }
}
