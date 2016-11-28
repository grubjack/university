package com.grubjack.university.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grubjack.university.dao.GroupDao;
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
@Table(name = "groups", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "groups_unique_name_idx")})
public class Group implements Comparable<Group> {
    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "group", fetch = FetchType.EAGER)
    private List<Student> students;

    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    @JsonIgnore
    private Faculty faculty;

    @Transient
    private University university;

    @Transient
    PersonDao<Student> studentDao;

    @Transient
    private static GroupDao groupDao;

    @Transient
    @JsonIgnore
    private static List<Group> groups;

    public Group() {
        if (AbstractHttpServlet.getContext() != null) {
            this.university = (University) AbstractHttpServlet.getContext().getBean("university");
            this.studentDao = (PersonDao<Student>) AbstractHttpServlet.getContext().getBean("studentDao");
            groupDao = (GroupDao) AbstractHttpServlet.getContext().getBean("groupDaoImpl");
        }
    }

    public Group(String name) {
        this();
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        return name != null ? name.equals(group.name) : group.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public void createStudent(Student student) {
        if (student != null && !students.contains(student)) {
            student.setGroup(this);
            students.add(student);
            Student.findAll().add(student);
            studentDao.create(student, id);
        }
    }

    public void deleteStudent(Student student) {
        if (student != null) {
            students.remove(student);
            Student.findAll().remove(student);
            studentDao.delete(student.getId());
        }
    }

    public void updateStudent(Student student) {
        Student oldStudent = studentDao.find(student.getId());
        if (oldStudent != null) {
            int index = students.indexOf(oldStudent);
            int index2 = Student.findAll().indexOf(oldStudent);
            if (index != -1) {
                students.set(index, student);
            }
            if (index2 != -1) {
                Student.findAll().set(index2, student);
            }
            studentDao.update(student, id);
        }
    }

    public List<Student> findStudents(String name) {
        List<Student> result = new ArrayList<>();
        for (Student student : getStudents()) {
            if (student.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(student);
            }
        }
        return result;
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
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    @Override
    public int compareTo(Group o) {
        return name.compareTo(o.getName());
    }

    public static List<Group> findAll() {
        if (groups == null) {
            groups = groupDao.findAll();
        }
        Collections.sort(groups);
        return groups;
    }

    public static void setAll(List<Group> groups) {
        Group.groups = groups;
    }

    public static List<Group> findByName(String name) {
        List<Group> result = new ArrayList<>();
        for (Group group : findAll()) {
            if (group.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(group);
            }
        }
        return result;
    }

    public static Group findById(int id) {
        return groupDao.find(id);
    }

    public static List<Group> findAvailable(DayOfWeek day, TimeOfDay time) {
        return groupDao.findAvailable(day, time);
    }

}
