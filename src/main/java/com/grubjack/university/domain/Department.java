package com.grubjack.university.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grubjack.university.dao.DepartmentDao;
import com.grubjack.university.dao.PersonDao;
import com.grubjack.university.servlet.AbstractHttpServlet;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
@Entity
@Table(name = "departments", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "faculties_unique_name_idx")})
public class Department implements Comparable<Department> {

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "department", fetch = FetchType.EAGER)
    private List<Teacher> teachers;

    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    @JsonIgnore
    private Faculty faculty;

    @Transient
    private PersonDao<Teacher> teacherDao;

    @Transient
    private static DepartmentDao departmentDao;

    @Transient
    @JsonIgnore
    private static List<Department> departments;


    public Department() {
        if (AbstractHttpServlet.getContext() != null) {
            this.teacherDao = (PersonDao<Teacher>) AbstractHttpServlet.getContext().getBean("teacherDao");
            departmentDao = (DepartmentDao) AbstractHttpServlet.getContext().getBean("departmentDaoImpl");
        }
    }

    public Department(String name) {
        this();
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
            teacher.setDepartment(this);
            teachers.add(teacher);
            Teacher.findAll().add(teacher);
            Lesson.setAll(null);
            Timetable.setAll(null);
            teacherDao.create(teacher, id);
        }
    }

    public void deleteTeacher(Teacher teacher) {
        if (teacher != null) {
            teachers.remove(teacher);
            Teacher.findAll().remove(teacher);
            Lesson.setAll(null);
            Timetable.setAll(null);
            teacherDao.delete(teacher.getId());
        }
    }

    public void updateTeacher(Teacher teacher) {
        Teacher oldTeacher = teacherDao.find(teacher.getId());
        if (oldTeacher != null) {
            int index = teachers.indexOf(oldTeacher);
            int index2 = Teacher.findAll().indexOf(oldTeacher);
            if (index != -1) {
                teachers.set(index, teacher);
            }
            if (index2 != -1) {
                Teacher.findAll().set(index2, teacher);
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


    public static List<Department> findAll() {
        if (departments == null) {
            departments = departmentDao.findAll();
        }
        Collections.sort(departments);
        return departments;
    }

    public static void setAll(List<Department> departments) {
        Department.departments = departments;
    }

    public static List<Department> findByName(String name) {
        List<Department> result = new ArrayList<>();
        for (Department department : findAll()) {
            if (department.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(department);
            }
        }
        return result;
    }

    public static Department findById(int id) {
        return departmentDao.find(id);
    }
}
