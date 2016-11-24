package com.grubjack.university.domain;

import com.grubjack.university.dao.DepartmentDao;
import com.grubjack.university.dao.GroupDao;
import com.grubjack.university.dao.LessonDao;
import com.grubjack.university.servlet.AbstractHttpServlet;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
@Entity
@Table(name = "faculties")
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
    private Timetable timetable;

    public Faculty() {
    }

    public Faculty(String name) {
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
            University university = (University) AbstractHttpServlet.getContext().getBean("university");
            GroupDao groupDao = (GroupDao) AbstractHttpServlet.getContext().getBean("groupDaoImpl");
            group.setFaculty(this);
            groups.add(group);
            university.getGroups().add(group);
            university.setTimetables(null);
            groupDao.create(group, id);
        }
    }

    public void deleteGroup(Group group) {
        if (group != null) {
            University university = (University) AbstractHttpServlet.getContext().getBean("university");
            GroupDao groupDao = (GroupDao) AbstractHttpServlet.getContext().getBean("groupDaoImpl");
            groups.remove(group);
            university.getGroups().remove(group);
            university.getStudents().removeAll(group.getStudents());
            university.setTimetables(null);
            groupDao.delete(group.getId());
        }
    }

    public Group findGroup(String name) {
        GroupDao groupDao = (GroupDao) AbstractHttpServlet.getContext().getBean("groupDaoImpl");
        return groupDao.findByName(name);
    }

    public void updateGroup(Group group) {
        University university = (University) AbstractHttpServlet.getContext().getBean("university");
        GroupDao groupDao = (GroupDao) AbstractHttpServlet.getContext().getBean("groupDaoImpl");
        Group oldGroup = groupDao.find(group.getId());
        if (oldGroup != null) {
            int index = groups.indexOf(oldGroup);
            int index2 = university.getGroups().indexOf(oldGroup);
            if (index != -1) {
                groups.set(index, group);
            }
            if (index2 != -1) {
                university.getGroups().set(index2, group);
            }
            university.setTimetables(null);
            groupDao.update(group, id);
        }
    }

    public void createDepartment(Department department) {
        if (department != null && !departments.contains(department)) {
            University university = (University) AbstractHttpServlet.getContext().getBean("university");
            DepartmentDao departmentDao = (DepartmentDao) AbstractHttpServlet.getContext().getBean("departmentDaoImpl");
            department.setFaculty(this);
            departments.add(department);
            university.getDepartments().add(department);
            departmentDao.create(department, id);
        }
    }

    public void deleteDepartment(Department department) {
        if (department != null) {
            University university = (University) AbstractHttpServlet.getContext().getBean("university");
            DepartmentDao departmentDao = (DepartmentDao) AbstractHttpServlet.getContext().getBean("departmentDaoImpl");
            departments.remove(department);
            university.getDepartments().remove(department);
            university.getTeachers().removeAll(department.getTeachers());
            university.setLessons(null);
            university.setTimetables(null);
            departmentDao.delete(department.getId());
        }
    }

    public void updateDepartment(Department department) {
        University university = (University) AbstractHttpServlet.getContext().getBean("university");
        DepartmentDao departmentDao = (DepartmentDao) AbstractHttpServlet.getContext().getBean("departmentDaoImpl");
        Department oldDepartment = departmentDao.find(department.getId());
        if (oldDepartment != null) {
            int index = departments.indexOf(oldDepartment);
            int index2 = university.getDepartments().indexOf(oldDepartment);
            if (index != -1) {
                departments.set(index, department);
            }
            if (index2 != -1) {
                university.getDepartments().set(index2, department);
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
        timetable = new Timetable();
        timetable.setName(name);
        LessonDao lessonDao = (LessonDao) AbstractHttpServlet.getContext().getBean("lessonDaoImpl");
        List<Lesson> lessons = lessonDao.findFacultyLessons(id);
        if (lessons != null) {
            timetable.setLessons(lessons);
        }
        return timetable;
    }

    public Timetable findTimetable(Group group) {
        Timetable result = new Timetable();
        if (group != null) {
            result.setName(group.getName());
            List<Lesson> lessons = getTimetable().getLessons();
            if (lessons != null) {

                Iterator<Lesson> iterator = lessons.iterator();
                while (iterator.hasNext()) {
                    Lesson lesson = iterator.next();
                    if (lesson.getGroup().getId() == group.getId()) {
                        result.getLessons().add(lesson);
                        iterator.remove();
                    }
                }
            }
        }
        return result;
    }

    public Timetable findTimetable(Teacher teacher) {
        Timetable result = new Timetable();
        if (teacher != null) {
            result.setName(teacher.getName());
            List<Lesson> lessons = getTimetable().getLessons();
            if (lessons != null) {
                Iterator<Lesson> iterator = lessons.iterator();
                while (iterator.hasNext()) {
                    Lesson lesson = iterator.next();
                    if (lesson.getTeacher().getId() == teacher.getId()) {
                        result.getLessons().add(lesson);
                        iterator.remove();
                    }
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
        if (timetable != null) {
            return timetable;
        }
        return findTimetable();
    }

    public List<Lesson> findLessons() {
        LessonDao lessonDao = (LessonDao) AbstractHttpServlet.getContext().getBean("lessonDaoImpl");
        return lessonDao.findFacultyLessons(id);
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    @Override
    public int compareTo(Faculty o) {
        return name.compareTo(o.getName());
    }
}
