package com.grubjack.university.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grubjack.university.dao.DepartmentDao;
import com.grubjack.university.dao.FacultyDao;
import com.grubjack.university.dao.GroupDao;
import com.grubjack.university.dao.LessonDao;
import com.grubjack.university.servlet.AbstractHttpServlet;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
@Entity
@Table(name = "faculties", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "departments_unique_name_idx")})
public class Faculty implements Comparable<Faculty> {

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "faculty", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Department> departments;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "faculty", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Group> groups;

    @Transient
    @JsonIgnore
    private Timetable timetable;

    @Transient
    private GroupDao groupDao;

    @Transient
    private DepartmentDao departmentDao;

    @Transient
    private LessonDao lessonDao;

    @Transient
    private static FacultyDao facultyDao;

    @Transient
    @JsonIgnore
    private static List<Faculty> faculties;

    public Faculty() {
        if (AbstractHttpServlet.getContext() != null) {
            this.groupDao = (GroupDao) AbstractHttpServlet.getContext().getBean("groupDaoImpl");
            this.departmentDao = (DepartmentDao) AbstractHttpServlet.getContext().getBean("departmentDaoImpl");
            this.lessonDao = (LessonDao) AbstractHttpServlet.getContext().getBean("lessonDaoImpl");
            facultyDao = (FacultyDao) AbstractHttpServlet.getContext().getBean("facultyDaoImpl");
        }
    }

    public Faculty(String name) {
        this();
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Faculty faculty = (Faculty) o;

        return name != null ? name.equals(faculty.name) : faculty.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public void createGroup(Group group) {
        if (group != null && !groups.contains(group)) {
            group.setFaculty(this);
            groups.add(group);
            Group.findAll().add(group);
            Timetable.setAll(null);
            groupDao.create(group, id);
        }
    }

    public void deleteGroup(Group group) {
        if (group != null) {
            groups.remove(group);
            Group.findAll().add(group);
            Student.findAll().removeAll(group.getStudents());
            groupDao.delete(group.getId());
        }
    }

    public Group findGroup(String name) {
        return groupDao.findByName(name);
    }

    public void updateGroup(Group group) {
        Group oldGroup = groupDao.find(group.getId());
        if (oldGroup != null) {
            int index = groups.indexOf(oldGroup);
            int index2 = Group.findAll().indexOf(oldGroup);
            if (index != -1) {
                groups.set(index, group);
            }
            if (index2 != -1) {
                Group.findAll().set(index2, group);
            }
            Timetable.setAll(null);
            groupDao.update(group, id);
        }
    }

    public void createDepartment(Department department) {
        if (department != null && !departments.contains(department)) {
            department.setFaculty(this);
            departments.add(department);
            Department.findAll().add(department);
            departmentDao.create(department, id);
        }
    }

    public void deleteDepartment(Department department) {
        if (department != null) {
            departments.remove(department);
            Department.findAll().remove(department);
            Teacher.findAll().removeAll(department.getTeachers());
            Lesson.setAll(null);
            Timetable.setAll(null);
            departmentDao.delete(department.getId());
        }
    }

    public void updateDepartment(Department department) {
        Department oldDepartment = departmentDao.find(department.getId());
        if (oldDepartment != null) {
            int index = departments.indexOf(oldDepartment);
            int index2 = Department.findAll().indexOf(oldDepartment);
            if (index != -1) {
                departments.set(index, department);
            }
            if (index2 != -1) {
                Department.findAll().set(index2, department);
            }
            departmentDao.update(department, id);
        }
    }

    public List<Department> findDepartments(String name) {
        List<Department> result = new ArrayList<>();
        for (Department department : getDepartments()) {
            if (department.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(department);
            }
        }
        return result;
    }

    public List<Group> findGroups(String name) {
        List<Group> result = new ArrayList<>();
        for (Group group : getGroups()) {
            if (group.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(group);
            }
        }
        return result;
    }

    private Timetable findTimetable() {
        Timetable timetable = new Timetable(name);
        timetable.setLessons(findLessons());
        return timetable;
    }

    public Timetable findTimetable(Group group) {
        Timetable result = new Timetable();
        if (group != null) {
            result.setName(group.getName());
            List<Lesson> lessons = new ArrayList<>();
            lessons.addAll(getTimetable().getLessons());
            Iterator<Lesson> iterator = lessons.iterator();
            while (iterator.hasNext()) {
                Lesson lesson = iterator.next();
                if (lesson.getGroup().getId() == group.getId()) {
                    result.getLessons().add(lesson);
                    iterator.remove();
                }
            }
        }
        return result;
    }

    public Timetable findTimetable(Teacher teacher) {
        Timetable result = new Timetable();
        if (teacher != null) {
            result.setName(teacher.getName());
            List<Lesson> lessons = findAllLessons();
            Iterator<Lesson> iterator = lessons.iterator();
            while (iterator.hasNext()) {
                Lesson lesson = iterator.next();
                if (lesson.getTeacher().getId() == teacher.getId()) {
                    result.getLessons().add(lesson);
                    iterator.remove();
                }
            }
        }
        return result;
    }

    public Timetable findTimetable(Student student) {
        return findTimetable(findGroupByStudent(student));
    }

    public List<Timetable> findGroupTimetables() {
        List<Timetable> result = new ArrayList<>();
        for (Group group : getGroups()) {
            result.add(findTimetable(group));
        }
        return result;
    }

    public Group findGroupByStudent(Student student) {
        if (student != null) {
            for (Group group : getGroups()) {
                if (group.getStudents().contains(student)) {
                    return group;
                }
            }
        }
        return null;
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

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public Timetable getTimetable() {
        if (timetable == null) {
            timetable = findTimetable();
        }
        return timetable;
    }

    private List<Lesson> findLessons() {
        return lessonDao.findFacultyLessons(id);
    }

    public List<Lesson> findAllLessons() {
        return lessonDao.findAll();
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    @Override
    public int compareTo(Faculty o) {
        return name.compareTo(o.getName());
    }

    public static Faculty findGroupFaculty(int groupId) {
        return facultyDao.findByGroup(groupId);
    }

    public static Faculty findStudentFaculty(int studentId) {
        return facultyDao.findByStudent(studentId);
    }

    public static Faculty findTeacherFaculty(int teacherId) {
        return facultyDao.findByTeacher(teacherId);
    }

    public static List<Faculty> findAll() {
        if (faculties == null) {
            faculties = facultyDao.findAll();
        }
        Collections.sort(faculties);
        return faculties;
    }

    public static void setAll(List<Faculty> faculties) {
        Faculty.faculties = faculties;
    }

    public static List<Faculty> findByName(String name) {
        List<Faculty> result = new ArrayList<>();
        for (Faculty faculty : findAll()) {
            if (faculty.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(faculty);
            }
        }
        return result;
    }

    public static Faculty findById(int id) {
        return facultyDao.find(id);
    }

}
