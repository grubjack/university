package com.grubjack.university.domain;

import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.PersonDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
public class Department {
    private int id;
    private String name;
    private List<Teacher> teachers;
    private Faculty faculty;
    private PersonDao<Teacher> teacherDao = DaoFactory.getInstance().getTeacherDao();

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
            teacher.setDepartment(this);
//            teacherDao.create(teacher);
            teachers.add(teacher);
        }
    }

    public void deleteTeacher(Teacher teacher) {
        if (teacher != null) {
//            teacherDao.delete(teacher.getId());
            teacher.setDepartment(null);
            teachers.remove(teacher);
        }
    }

    public void updateTeacher(Teacher teacher) {
        if (teacher != null) {
            Teacher oldTeacher = null;
            for (Teacher t : teachers) {
                if (t.getId() == teacher.getId()) {
                    oldTeacher = t;
                    break;
                }
            }
            if (oldTeacher != null) {
                teacherDao.update(teacher);
                oldTeacher.setFirstName(teacher.getFirstName());
                oldTeacher.setLastName(teacher.getLastName());
                oldTeacher.setSalary(teacher.getSalary());
                oldTeacher.setDepartment(teacher.getDepartment());

            } else {
                teacherDao.delete(teacher.getId());
                teacher.setDepartment(null);
                teachers.remove(teacher);
            }
        }
    }

    public List<Teacher> findTeachersByFirstName(String firstName) {
        return teacherDao.findByFirstName(id, firstName);
    }

    public List<Teacher> findTeachersByLastName(String lastName) {
        return teacherDao.findByLastName(id, lastName);
    }

    public List<Teacher> findTeachersByName(String firstName, String lastName) {
        return teacherDao.findByName(id, firstName, lastName);
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

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
}
