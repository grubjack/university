package com.grubjack.university.domain;

import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.PersonDao;
import com.grubjack.university.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
@Entity
@Table(name = "departments")
public class Department implements Comparable<Department> {

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;


    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "department")
    private List<Teacher> teachers;

    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    @Transient
    private PersonDao<Teacher> teacherDao = DaoFactory.getInstance().getTeacherDao();

    private static Logger log = LoggerFactory.getLogger(Department.class);

    public Department() {
    }

    public Department(String name) {
        this.name = name;

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
        if (teacher != null && !getTeachers().contains(teacher)) {
            try {
                getTeachers().add(teacher);
                University.getInstance().getTeachers().add(teacher);
                University.getInstance().setLessons(null);
                University.getInstance().setTimetables(null);
                teacherDao.create(teacher, id);
            } catch (DaoException e) {
                log.warn("Can't create teacher");
            }
        }
    }

    public void deleteTeacher(Teacher teacher) {
        if (teacher != null) {
            try {
                getTeachers().remove(teacher);
                University.getInstance().getTeachers().remove(teacher);
                University.getInstance().setLessons(null);
                University.getInstance().setTimetables(null);
                teacherDao.delete(teacher.getId());
            } catch (DaoException e) {
                log.warn("Can't delete teacher");
            }
        }
    }

    public void updateTeacher(Teacher teacher) {
        Teacher oldTeacher = null;
        try {
            oldTeacher = teacherDao.find(teacher.getId());
        } catch (DaoException e) {
            log.warn("Can't find teacher");
        }
        if (oldTeacher != null) {
            try {
                int index = getTeachers().indexOf(oldTeacher);
                int index2 = University.getInstance().getTeachers().indexOf(oldTeacher);
                if (index != -1) {
                    getTeachers().set(index, teacher);
                }
                if (index2 != -1) {
                    University.getInstance().getTeachers().set(index2, teacher);
                }
                teacherDao.update(teacher, id);
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
        if (teachers == null) {
            try {
                teachers = teacherDao.findAll(id);
            } catch (DaoException e) {
                log.warn("Can't find teachers");
            }
        }
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

    @Override
    public int compareTo(Department o) {
        return name.compareTo(o.getName());
    }
}
