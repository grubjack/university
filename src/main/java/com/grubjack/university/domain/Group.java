package com.grubjack.university.domain;

import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.PersonDao;
import com.grubjack.university.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
public class Group implements Comparable<Group> {
    private int id;
    private String name;
    private List<Student> students;

    private static Logger log = LoggerFactory.getLogger(Group.class);

    private PersonDao<Student> studentDao = DaoFactory.getInstance().getStudentDao();

    public Group() {
    }


    public Group(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (name != null ? !name.equals(group.name) : group.name != null) return false;
        return students != null ? students.equals(group.students) : group.students == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public void createStudent(Student student) {
        if (student != null && !getStudents().contains(student)) {
            try {
                getStudents().add(student);
                University.getInstance().getStudents().add(student);
                studentDao.create(student, id);
            } catch (DaoException e) {
                log.warn("Can't create student");
            }
        }
    }

    public void deleteStudent(Student student) {
        if (student != null) {
            try {
                getStudents().remove(student);
                University.getInstance().getStudents().remove(student);
                studentDao.delete(student.getId());
            } catch (DaoException e) {
                log.warn("Can't delete student");
            }
        }
    }

    public void updateStudent(Student student) {
        Student oldStudent = null;
        try {
            oldStudent = studentDao.find(student.getId());
        } catch (DaoException e) {
            log.warn("Can't find student");
        }
        if (oldStudent != null) {
            try {
                int index = getStudents().indexOf(oldStudent);
                int index2 = University.getInstance().getStudents().indexOf(oldStudent);
                if (index != -1) {
                    getStudents().set(index, student);
                }
                if (index2 != -1) {
                    University.getInstance().getStudents().set(index2, student);
                }
                studentDao.update(student, id);
            } catch (DaoException e) {
                log.warn("Can't update student");
            }
        }
    }


    public List<Student> findStudentsByFirstName(String firstName) {
        try {
            return studentDao.findByFirstName(firstName);
        } catch (DaoException e) {
            log.warn("Can't find students by firstname");
        }
        return Collections.emptyList();
    }

    public List<Student> findStudentsByLastName(String lastName) {
        try {
            return studentDao.findByLastName(lastName);
        } catch (DaoException e) {
            log.warn("Can't find students by lastname");
        }
        return Collections.emptyList();
    }

    public List<Student> findStudentsByName(String firstName, String lastName) {
        try {
            return studentDao.findByName(firstName, lastName);
        } catch (DaoException e) {
            log.warn("Can't find students by name");
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

    public List<Student> getStudents() {
        if (students == null) {
            try {
                students = studentDao.findAll(id);
            } catch (DaoException e) {
                log.warn("Can't find students");
            }
        }
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public int compareTo(Group o) {
        return name.compareTo(o.getName());
    }
}
