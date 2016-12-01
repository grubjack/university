package com.grubjack.university.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
@NamedQueries({
        @NamedQuery(name = "Group.findAvailableByDayAndTime", query = "SELECT g FROM Group g WHERE g.id NOT IN ( SELECT lg.id FROM Lesson l INNER JOIN l.group lg WHERE l.dayOfWeek = ?1 AND l.timeOfDay = ?2 ) ORDER BY g.name"),
        @NamedQuery(name = "Group.findGroupsByName", query = "SELECT g FROM Group g WHERE LOWER(g.name) LIKE CONCAT('%',LOWER(?1),'%') ORDER BY g.name"),
        @NamedQuery(name = "Group.findGroupsByNameAndFaculty", query = "SELECT g FROM Group g WHERE g.faculty.id = ?2 AND LOWER(g.name) LIKE CONCAT('%',LOWER(?1),'%') ORDER BY g.name")
})
@Entity
@Table(name = "groups", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "groups_unique_name_idx")})
public class Group {

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

}
