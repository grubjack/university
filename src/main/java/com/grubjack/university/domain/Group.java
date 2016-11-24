package com.grubjack.university.domain;

import com.grubjack.university.dao.PersonDao;
import com.grubjack.university.servlet.AbstractHttpServlet;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
@Entity
@Table(name = "groups")
public class Group implements Comparable<Group> {
    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "group", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Student> students;

    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

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

        return name != null ? name.equals(group.name) : group.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public void createStudent(Student student) {
        if (student != null && !students.contains(student)) {
            University university = (University) AbstractHttpServlet.getContext().getBean("university");
            PersonDao<Student> studentDao = (PersonDao<Student>) AbstractHttpServlet.getContext().getBean("studentDao");
            student.setGroup(this);
            students.add(student);
            university.getStudents().add(student);
            studentDao.create(student, id);
        }
    }

    public void deleteStudent(Student student) {
        if (student != null) {
            University university = (University) AbstractHttpServlet.getContext().getBean("university");
            PersonDao<Student> studentDao = (PersonDao<Student>) AbstractHttpServlet.getContext().getBean("studentDao");
            students.remove(student);
            university.getStudents().remove(student);
            studentDao.delete(student.getId());
        }
    }

    public void updateStudent(Student student) {
        University university = (University) AbstractHttpServlet.getContext().getBean("university");
        PersonDao<Student> studentDao = (PersonDao<Student>) AbstractHttpServlet.getContext().getBean("studentDao");
        Student oldStudent = studentDao.find(student.getId());
        if (oldStudent != null) {
            int index = students.indexOf(oldStudent);
            int index2 = university.getStudents().indexOf(oldStudent);
            if (index != -1) {
                students.set(index, student);
            }
            if (index2 != -1) {
                university.getStudents().set(index2, student);
            }
            studentDao.update(student, id);
        }
    }


    public List<Student> findStudentsByFirstName(String firstName) {
        PersonDao<Student> studentDao = (PersonDao<Student>) AbstractHttpServlet.getContext().getBean("studentDao");
        return studentDao.findByFirstName(firstName);
    }

    public List<Student> findStudentsByLastName(String lastName) {
        PersonDao<Student> studentDao = (PersonDao<Student>) AbstractHttpServlet.getContext().getBean("studentDao");
        return studentDao.findByLastName(lastName);
    }

    public List<Student> findStudentsByName(String firstName, String lastName) {
        PersonDao<Student> studentDao = (PersonDao<Student>) AbstractHttpServlet.getContext().getBean("studentDao");
        return studentDao.findByName(firstName, lastName);
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
}
