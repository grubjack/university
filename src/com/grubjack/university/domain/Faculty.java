package com.grubjack.university.domain;

import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.DepartmentDao;
import com.grubjack.university.dao.GroupDao;
import com.grubjack.university.dao.LessonDao;

import java.util.*;

/**
 * Created by grubjack on 28.10.2016.
 */
public class Faculty {
    private int id;
    private String name;
    private List<Department> departments;
    private List<Group> groups;
    private List<Lesson> lessons;

    private DepartmentDao departmentDao = DaoFactory.getInstance().getDepartmentDao();
    private GroupDao groupDao = DaoFactory.getInstance().getGroupDao();
    private LessonDao lessonDao = DaoFactory.getInstance().getLessonDao();

    public Faculty() {
        departments = new ArrayList<>();
        groups = new ArrayList<>();
    }

    public Faculty(String name) {
        this.name = name;
        departments = new ArrayList<>();
        groups = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Faculty faculty = (Faculty) o;

        if (name != null ? !name.equals(faculty.name) : faculty.name != null) return false;
        if (departments != null ? !departments.equals(faculty.departments) : faculty.departments != null) return false;
        return groups != null ? groups.equals(faculty.groups) : faculty.groups == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (departments != null ? departments.hashCode() : 0);
        result = 31 * result + (groups != null ? groups.hashCode() : 0);
        return result;
    }

    public void createGroup(Group group) {
        if (group != null && !groups.contains(group)) {
            group.setFaculty(this);
            groupDao.create(group);
            groups.add(group);
        }
    }

    public void deleteGroup(Group group) {
        if (group != null) {
            groupDao.delete(group.getId());
            group.setFaculty(null);
            groups.remove(group);
        }
    }

    public void updateGroup(Group group) {
        Group oldGroup = groupDao.find(group.getId());
        if (oldGroup != null) {
            groups.remove(oldGroup);
            group.setFaculty(this);
            groups.add(group);
            groupDao.update(group);
        }
    }

    public void createDepartment(Department department) {
        if (department != null && !departments.contains(department)) {
            department.setFaculty(this);
            departmentDao.create(department);
            departments.add(department);
        }
    }

    public void deleteDepartment(Department department) {
        if (department != null) {
            departmentDao.delete(department.getId());
            department.setFaculty(null);
            departments.remove(department);
        }
    }

    public void updateDepartment(Department department) {
        Department oldDepartment = departmentDao.find(department.getId());
        if (oldDepartment != null) {
            departments.remove(oldDepartment);
            department.setFaculty(this);
            departments.add(department);
            departmentDao.update(department);
        }
    }

    public void createLesson(Lesson lesson) {
        if (lesson != null && !lessons.contains(lesson)) {
            lesson.setFaculty(this);
            lessonDao.create(lesson);
            lessons.add(lesson);
        }
    }

    public void deleteLesson(Lesson lesson) {
        if (lesson != null) {
            lessonDao.delete(lesson.getId());
            lesson.setFaculty(null);
            lessons.remove(lesson);
        }
    }

    public void updateLesson(Lesson lesson) {
        Lesson oldLesson = lessonDao.find(lesson.getId());
        if (oldLesson != null) {
            lessons.remove(oldLesson);
            lesson.setFaculty(this);
            lessons.add(lesson);
            lessonDao.update(lesson);
        }
    }

    public List<Lesson> findDayTimetable(Student student, DayOfWeek dayOfWeek) {
        return findDayTimetable(findGroup(student), dayOfWeek);
    }

    public Map<DayOfWeek, List<Lesson>> findTimetable(Student student) {
        return findTimetable(findGroup(student));
    }

    public List<Lesson> findDayTimetable(Teacher teacher, DayOfWeek dayOfWeek) {
        if (teacher != null) {
            return lessonDao.findAllByDayForFacultyTeacher(id, teacher.getId(), dayOfWeek);
        }
        return Collections.emptyList();
    }

    public Map<DayOfWeek, List<Lesson>> findTimetable(Teacher teacher) {
        Map<DayOfWeek, List<Lesson>> result = new HashMap<>();
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            result.put(dayOfWeek, findDayTimetable(teacher, dayOfWeek));
        }
        return result;
    }

    public List<Lesson> findDayTimetable(Group group, DayOfWeek dayOfWeek) {
        if (group != null) {
            return lessonDao.findAllByDayForFacultyGroup(id, group.getId(), dayOfWeek);
        }
        return Collections.emptyList();
    }

    public Map<DayOfWeek, List<Lesson>> findTimetable(Group group) {
        Map<DayOfWeek, List<Lesson>> result = new HashMap<>();
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            result.put(dayOfWeek, findDayTimetable(group, dayOfWeek));
        }
        return result;
    }

    public Group findGroup(Student student) {
        if (student != null)
            return student.getGroup();
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

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}
