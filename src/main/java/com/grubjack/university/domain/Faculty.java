package com.grubjack.university.domain;

import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.DepartmentDao;
import com.grubjack.university.dao.GroupDao;
import com.grubjack.university.dao.LessonDao;
import com.grubjack.university.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
public class Faculty {
    private int id;
    private String name;
    private List<Department> departments;
    private List<Group> groups;
    private Timetable timetable;

    private DepartmentDao departmentDao = DaoFactory.getInstance().getDepartmentDao();
    private GroupDao groupDao = DaoFactory.getInstance().getGroupDao();
    private LessonDao lessonDao = DaoFactory.getInstance().getLessonDao();

    private static Logger log = LoggerFactory.getLogger(Faculty.class);

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
        if (group != null && !groups.contains(group)) {
            try {
                groupDao.create(group, id);
                groups.add(group);
            } catch (DaoException e) {
                log.warn("Can't create group");
            }
        }
    }

    public void deleteGroup(Group group) {
        if (group != null) {
            try {
                groupDao.delete(group.getId());
                groups.remove(group);
            } catch (DaoException e) {
                log.warn("Can't delete group");
            }
        }
    }

    public void updateGroup(Group group) {
        Group oldGroup = null;
        try {
            oldGroup = groupDao.find(group.getId());
        } catch (DaoException e) {
            log.warn("Can't find group");
        }
        if (oldGroup != null) {
            try {
                groupDao.update(group, id);
                groups.remove(oldGroup);
                groups.add(group);
            } catch (DaoException e) {
                log.warn("Can't update group");
            }
        }
    }

    public void createDepartment(Department department) {
        if (department != null && !departments.contains(department)) {
            try {
                departmentDao.create(department, id);
                departments.add(department);
            } catch (DaoException e) {
                log.warn("Can't create department");
            }
        }
    }

    public void deleteDepartment(Department department) {
        if (department != null) {
            try {
                departmentDao.delete(department.getId());
                departments.remove(department);
            } catch (DaoException e) {
                log.warn("Can't delete department");
            }
        }
    }

    public void updateDepartment(Department department) {
        Department oldDepartment = null;
        try {
            oldDepartment = departmentDao.find(department.getId());
        } catch (DaoException e) {
            log.warn("Can't find department");
        }
        if (oldDepartment != null) {
            try {
                departmentDao.update(department, id);
                departments.remove(oldDepartment);
                departments.add(department);
            } catch (DaoException e) {
                log.warn("Can't update department");
            }
        }
    }


    public TimetableUnit findDayTimetable(Student student, DayOfWeek dayOfWeek) {
        return findDayTimetable(findGroup(student), dayOfWeek);
    }

    public Timetable findTimetable(Student student) {
        return findTimetable(findGroup(student));
    }

    public TimetableUnit findDayTimetable(Teacher teacher, DayOfWeek dayOfWeek) {
        TimetableUnit result = new TimetableUnit();
        if (teacher != null) {
            try {
                result.setLessons(lessonDao.findTeacherLessons(teacher.getId(), dayOfWeek));
            } catch (DaoException e) {
                log.warn("Can't find teacher timetable unit");
            }
        }
        return result;
    }

    public Timetable findTimetable(Teacher teacher) {
        Timetable result = new Timetable();
        List<TimetableUnit> units = new ArrayList<>();
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            units.add(findDayTimetable(teacher, dayOfWeek));
        }
        result.setUnits(units);
        return result;
    }

    public TimetableUnit findDayTimetable(Group group, DayOfWeek dayOfWeek) {
        TimetableUnit result = new TimetableUnit();
        if (group != null) {
            try {
                result.setLessons(lessonDao.findGroupLessons(group.getId(), dayOfWeek));
            } catch (DaoException e) {
                log.warn("Can't find group timetable unit");
            }
        }
        return result;
    }

    public Timetable findTimetable(Group group) {
        Timetable result = new Timetable();
        List<TimetableUnit> units = new ArrayList<>();
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            units.add(findDayTimetable(group, dayOfWeek));
        }
        result.setUnits(units);
        return result;
    }

    public Group findGroup(Student student) {
        for (Group group : groups) {
            if (group.getStudents().contains(student)) {
                return group;
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
        return timetable;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }
}
