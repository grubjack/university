package com.grubjack.university.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
@NamedQueries({
        @NamedQuery(name = "Department.findDepartmentsByName", query = "SELECT d FROM Department d WHERE LOWER(d.name) LIKE CONCAT('%',LOWER(?1),'%') ORDER BY d.name"),
        @NamedQuery(name = "Department.findDepartmentsByNameAndFaculty", query = "SELECT d FROM Department d WHERE d.faculty.id = ?2 AND LOWER(d.name) LIKE CONCAT('%',LOWER(?1),'%') ORDER BY d.name")
})
@Entity
@Table(name = "departments", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "faculties_unique_name_idx")})
public class Department {

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
