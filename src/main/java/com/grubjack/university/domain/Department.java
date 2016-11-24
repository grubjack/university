package com.grubjack.university.domain;

import com.grubjack.university.dao.PersonDao;
import com.grubjack.university.service.University;
import com.grubjack.university.servlet.AbstractHttpServlet;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
@Entity
@Table(name = "departments")
public class Department implements Comparable<Department>,Serializable {

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "department", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Teacher> teachers;

    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

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

        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public void createTeacher(Teacher teacher) {
        if (teacher != null && !teachers.contains(teacher)) {
            University university = (University) AbstractHttpServlet.getContext().getBean("university");
            PersonDao<Teacher> teacherDao = (PersonDao<Teacher>) AbstractHttpServlet.getContext().getBean("teacherDao");
            teacher.setDepartment(this);
            teachers.add(teacher);
            university.getTeachers().add(teacher);
            university.setLessons(null);
            university.setTimetables(null);
            teacherDao.create(teacher, id);
        }
    }

    public void deleteTeacher(Teacher teacher) {
        if (teacher != null) {
            University university = (University) AbstractHttpServlet.getContext().getBean("university");
            PersonDao<Teacher> teacherDao = (PersonDao<Teacher>) AbstractHttpServlet.getContext().getBean("teacherDao");
            teachers.remove(teacher);
            university.getTeachers().remove(teacher);
            university.setLessons(null);
            university.setTimetables(null);
            teacherDao.delete(teacher.getId());
        }
    }

    public void updateTeacher(Teacher teacher) {
        University university = (University) AbstractHttpServlet.getContext().getBean("university");
        PersonDao<Teacher> teacherDao = (PersonDao<Teacher>) AbstractHttpServlet.getContext().getBean("teacherDao");
        Teacher oldTeacher = teacherDao.find(teacher.getId());
        if (oldTeacher != null) {
            int index = teachers.indexOf(oldTeacher);
            int index2 = university.getTeachers().indexOf(oldTeacher);
            if (index != -1) {
                teachers.set(index, teacher);
            }
            if (index2 != -1) {
                university.getTeachers().set(index2, teacher);
            }
            teacherDao.update(teacher, id);
        }
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

    public List<Teacher> findTeachers(String name) {
        List<Teacher> result = new ArrayList<>();
        for (Teacher teacher : getTeachers()) {
            if (teacher.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(teacher);
            }
        }
        return result;
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
