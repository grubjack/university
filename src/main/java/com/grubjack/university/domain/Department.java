package com.grubjack.university.domain;

import com.grubjack.university.exception.DaoException;
import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.PersonDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
public class Department {
    private int id;
    private String name;
    private List<Teacher> teachers;
    private PersonDao<Teacher> teacherDao = DaoFactory.getInstance().getTeacherDao();

    private static Logger log = LoggerFactory.getLogger(Department.class);

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

    public void createTeacher(Teacher teacher, int departmentId) {
        if (teacher != null && !teachers.contains(teacher)) {
            try {
                teacherDao.create(teacher, departmentId);
                teachers.add(teacher);
            } catch (DaoException e) {
                log.warn("Can't create teacher");
            }
        }
    }

    public void deleteTeacher(Teacher teacher) {
        if (teacher != null) {
            try {
                teacherDao.delete(teacher.getId());
                teachers.remove(teacher);
            } catch (DaoException e) {
                log.warn("Can't delete teacher");
            }
        }
    }

    public void updateTeacher(Teacher teacher, int departmentId) {
        Teacher oldTeacher = null;
        try {
            oldTeacher = teacherDao.find(teacher.getId());
        } catch (DaoException e) {
            log.warn("Can't find teacher");
        }
        if (oldTeacher != null) {
            try {
                teacherDao.update(teacher, departmentId);
                teachers.remove(oldTeacher);
                teachers.add(teacher);
            } catch (DaoException e) {
                log.warn("Can't update teacher");
            }
        }
    }

    public List<Teacher> findTeachersByFirstName(String firstName) {
        try {
            return teacherDao.findByFirstName(id, firstName);
        } catch (DaoException e) {
            log.warn("Can't find teacher by firstname");
        }
        return Collections.emptyList();
    }

    public List<Teacher> findTeachersByLastName(String lastName) {
        try {
            return teacherDao.findByLastName(id, lastName);
        } catch (DaoException e) {
            log.warn("Can't find teacher by lastname");
        }
        return Collections.emptyList();
    }

    public List<Teacher> findTeachersByName(String firstName, String lastName) {
        try {
            return teacherDao.findByName(id, firstName, lastName);
        } catch (DaoException e) {
            log.warn("Can't find teacher by name");
        }
        return Collections.emptyList();
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

}
