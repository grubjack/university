package com.grubjack.university.domain;

import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.PersonDao;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
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


    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "department",fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Teacher> teachers;

    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    @Transient
    private PersonDao<Teacher> teacherDao = DaoFactory.getInstance().getTeacherDao();

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
            getTeachers().add(teacher);
            teacherDao.create(teacher, id);
        }
    }

    public void deleteTeacher(Teacher teacher) {
        if (teacher != null) {
            getTeachers().remove(teacher);
            teacherDao.delete(teacher.getId());
        }
    }

    public void updateTeacher(Teacher teacher) {
        Teacher oldTeacher = teacherDao.find(teacher.getId());
        if (oldTeacher != null) {
            int index = getTeachers().indexOf(oldTeacher);
            if (index != -1) {
                getTeachers().set(index, teacher);
            }
            teacherDao.update(teacher, id);
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
        if (teachers == null) {
            teachers = teacherDao.findAll(id);
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
