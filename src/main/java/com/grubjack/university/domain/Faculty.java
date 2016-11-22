package com.grubjack.university.domain;

import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.DepartmentDao;
import com.grubjack.university.dao.GroupDao;
import com.grubjack.university.dao.LessonDao;
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

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "faculty",fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Department> departments;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "faculty",fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Group> groups;

    @Transient
    private Timetable timetable;

    @Transient
    private DepartmentDao departmentDao = DaoFactory.getInstance().getDepartmentDao();

    @Transient
    private GroupDao groupDao = DaoFactory.getInstance().getGroupDao();

    @Transient
    private LessonDao lessonDao = DaoFactory.getInstance().getLessonDao();

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

        if (name != null ? !name.equals(faculty.name) : faculty.name != null) return false;
        if (departments != null ? !departments.equals(faculty.departments) : faculty.departments != null) return false;
        if (groups != null ? !groups.equals(faculty.groups) : faculty.groups != null) return false;
        return timetable != null ? timetable.equals(faculty.timetable) : faculty.timetable == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (departments != null ? departments.hashCode() : 0);
        result = 31 * result + (groups != null ? groups.hashCode() : 0);
        result = 31 * result + (timetable != null ? timetable.hashCode() : 0);
        return result;
    }

    public void createGroup(Group group) {
        if (group != null && !getGroups().contains(group)) {
            getGroups().add(group);
            University.getInstance().getGroups().add(group);
            University.getInstance().setTimetables(null);
            groupDao.create(group, id);
        }
    }

    public void deleteGroup(Group group) {
        if (group != null) {
            getGroups().remove(group);
            University.getInstance().getGroups().remove(group);
            University.getInstance().getStudents().removeAll(group.getStudents());
            University.getInstance().setTimetables(null);
            groupDao.delete(group.getId());
        }
    }

    public Group findGroup(String name) {
        return groupDao.findByName(name);
    }

    public void updateGroup(Group group) {
        Group oldGroup = groupDao.find(group.getId());
        if (oldGroup != null) {
            int index = getGroups().indexOf(oldGroup);
            int index2 = University.getInstance().getGroups().indexOf(oldGroup);
            if (index != -1) {
                getGroups().set(index, group);
            }
            if (index2 != -1) {
                University.getInstance().getGroups().set(index2, group);
            }
            groupDao.update(group, id);
        }
    }

    public void createDepartment(Department department) {
        if (department != null && !getDepartments().contains(department)) {
            getDepartments().add(department);
            University.getInstance().getDepartments().add(department);
            departmentDao.create(department, id);
        }
    }

    public void deleteDepartment(Department department) {
        if (department != null) {
            getDepartments().remove(department);
            University.getInstance().getDepartments().remove(department);
            University.getInstance().getTeachers().removeAll(department.getTeachers());
            University.getInstance().setLessons(null);
            University.getInstance().setTimetables(null);
            departmentDao.delete(department.getId());
        }
    }

    public void updateDepartment(Department department) {
        Department oldDepartment = departmentDao.find(department.getId());
        if (oldDepartment != null) {
            getDepartments().remove(oldDepartment);
            getDepartments().add(department);

            int index = getDepartments().indexOf(oldDepartment);
            int index2 = University.getInstance().getDepartments().indexOf(oldDepartment);
            if (index != -1) {
                getDepartments().set(index, department);
            }
            if (index2 != -1) {
                University.getInstance().getDepartments().set(index2, department);
            }
            departmentDao.update(department, id);
        }
    }


    public TimetableUnit findDayTimetable(Student student, DayOfWeek dayOfWeek) {
        return findDayTimetable(findGroupByStudent(student), dayOfWeek);
    }

    public Timetable findTimetable(Student student) {
        return findTimetable(findGroupByStudent(student));
    }

    public TimetableUnit findDayTimetable(Teacher teacher, DayOfWeek dayOfWeek) {
        TimetableUnit result = new TimetableUnit();
        if (teacher != null) {
            result.setLessons(lessonDao.findTeacherLessons(teacher.getId(), dayOfWeek));
        }
        return result;
    }

    public Timetable findTimetable(Teacher teacher) {
        Timetable result = new Timetable();
        if (teacher != null) {
            result.setName(teacher.getName());
            List<Lesson> lessons = lessonDao.findTeacherLessons(teacher.getId());
            if (lessons != null) {
                for (DayOfWeek day : DayOfWeek.values()) {
                    TimetableUnit unit = new TimetableUnit(day);
                    Iterator<Lesson> iterator = lessons.iterator();
                    while (iterator.hasNext()) {
                        Lesson lesson = iterator.next();
                        if (lesson.getDayOfWeek().equals(day)) {
                            unit.getLessons().add(lesson);
                            iterator.remove();
                        }
                    }
                    result.getUnits().add(unit);
                }
            }
        }
        return result;
    }

    public TimetableUnit findDayTimetable(Group group, DayOfWeek dayOfWeek) {
        TimetableUnit result = new TimetableUnit();
        if (group != null) {
            result.setLessons(lessonDao.findGroupLessons(group.getId(), dayOfWeek));
        }
        return result;
    }

    public Timetable findTimetable(Group group) {
        Timetable result = new Timetable();
        if (group != null) {
            result.setName(group.getName());
            List<Lesson> lessons = lessonDao.findGroupLessons(group.getId());
            if (lessons != null) {
                for (DayOfWeek day : DayOfWeek.values()) {
                    TimetableUnit unit = new TimetableUnit(day);
                    Iterator<Lesson> iterator = lessons.iterator();
                    while (iterator.hasNext()) {
                        Lesson lesson = iterator.next();
                        if (lesson.getDayOfWeek().equals(day)) {
                            unit.getLessons().add(lesson);
                            iterator.remove();
                        }
                    }
                    result.getUnits().add(unit);
                }
            }
        }
        return result;
    }


    public Group findGroupByStudent(Student student) {
        if (student != null) {
            return groupDao.findByStudent(student);
        }
        return null;
    }

    public Group findGroupByName(String name) {
        if (name != null && !name.isEmpty()) {
            return groupDao.findByName(name);
        }
        return null;
    }

    private Timetable findTimetable() {
        timetable = new Timetable();
        timetable.setName(name);
        List<Lesson> lessons = lessonDao.findFacultyLessons(id);
        if (lessons != null) {
            for (DayOfWeek day : DayOfWeek.values()) {
                TimetableUnit unit = new TimetableUnit(day);
                Iterator<Lesson> iterator = lessons.iterator();
                while (iterator.hasNext()) {
                    Lesson lesson = iterator.next();
                    if (lesson.getDayOfWeek().equals(day)) {
                        unit.getLessons().add(lesson);
                        iterator.remove();
                    }
                }
                timetable.getUnits().add(unit);
            }
        }
        return timetable;
    }

    public List<Timetable> findGroupTimetables() {
        List<Timetable> timetables = new ArrayList<>();

        for (Group group : getGroups()) {
            timetables.add(findTimetable(group));
        }

        return timetables;
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
        if (departments == null) {
            departments = departmentDao.findAll(id);
        }
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<Group> getGroups() {
        if (groups == null) {
            groups = groupDao.findAll(id);
        }
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
