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

    @Transient
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
            University.getInstance().getStudents().add(student);
            studentDao.create(student, id);
        }
    }

    public void deleteStudent(Student student) {
        if (student != null) {
            students.remove(student);
            University.getInstance().getStudents().remove(student);
            studentDao.delete(student.getId());
        }
    }

    public void updateStudent(Student student) {
        Student oldStudent = studentDao.find(student.getId());
        if (oldStudent != null) {
            int index = students.indexOf(oldStudent);
            int index2 = University.getInstance().getStudents().indexOf(oldStudent);
            if (index != -1) {
                students.set(index, student);
            }
            if (index2 != -1) {
                University.getInstance().getStudents().set(index2, student);
            }
            studentDao.update(student, id);
        }
    }


    public List<Student> findStudentsByFirstName(String firstName) {
        return studentDao.findByFirstName(firstName);
    }

    public List<Student> findStudentsByLastName(String lastName) {
        return studentDao.findByLastName(lastName);
    }

    public List<Student> findStudentsByName(String firstName, String lastName) {
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
