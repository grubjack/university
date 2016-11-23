package com.grubjack.university.dao.impl;

import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.GroupDao;
import com.grubjack.university.domain.DayOfWeek;
import com.grubjack.university.domain.Group;
import com.grubjack.university.domain.Student;
import com.grubjack.university.domain.TimeOfDay;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * Created by grubjack on 03.11.2016.
 */
public class GroupDaoImpl implements GroupDao {
    private static Logger log = LoggerFactory.getLogger(GroupDaoImpl.class);

    private Session getSession() {
        return DaoFactory.getSessionFactory().openSession();
    }

    @Override
    public void create(Group group, int facultyId) {
        if (group != null && group.getFaculty() != null && group.getFaculty().getId() == facultyId) {
            log.info("Creating new group " + group.getName());
            Session session = getSession();
            session.save(group);
            session.flush();
            session.close();
            log.info("Group is created with id = " + group.getId());
        }
    }

    @Override
    public void update(Group group, int facultyId) {
        log.info("Updating group with id " + group.getId());
        if (group != null) {
            Group groupToUpdate = find(group.getId());
            if (groupToUpdate != null && groupToUpdate.getFaculty() != null && groupToUpdate.getFaculty().getId() == facultyId) {
                groupToUpdate.setName(group.getName());
                log.info("Updating group with id " + group.getId());
                Session session = getSession();
                session.update(groupToUpdate);
                session.flush();
                session.close();
            }
        }
    }

    @Override
    public void delete(int id) {
        Group group = find(id);
        if (group != null) {
            log.info("Deleting group with id " + id);
            Session session = getSession();
            session.delete(group);
            session.close();
        }
    }

    @Override
    public Group find(int id) {
        log.info("Finding group with id " + id);
        Session session = getSession();
        Group result = session.get(Group.class, id);
        session.close();
        return result;
    }

    @Override
    public Group findByStudent(Student student) {
        if (student != null) {
            log.info("Finding group by student with id " + student.getId());
            Session session = getSession();
            Student findStudent = session.get(Student.class, student.getId());
            session.close();
            if (findStudent != null) {
                return findStudent.getGroup();
            }
        }
        return null;
    }

    @Override
    public List<Group> findAll() {
        log.info("Finding all groups");
        Session session = getSession();
        List<Group> result = session.createQuery("from Group").list();
        session.close();
        return result;
    }

    @Override
    public Group findByName(String name) {
        if (name != null) {
            log.info("Finding group with name " + name);
            Session session = getSession();
            Group result = (Group) session.createQuery("from Group where lower(name)=:name")
                    .setParameter("name", name.toLowerCase()).uniqueResult();
            session.close();
            return result;
        }
        return null;
    }

    @Override
    public List<Group> findAll(int facultyId) {
        log.info("Finding all groups with facultyId " + facultyId);
        Session session = getSession();
        List<Group> result = session.createQuery("from Group g where g.faculty.id=:facultyId").setParameter("facultyId", facultyId).list();
        session.close();
        return result;
    }

    @Override
    public List<Group> findAvailable(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        if (dayOfWeek != null && timeOfDay != null) {
            log.info("Finding available groups on " + dayOfWeek.toString() + " at " + timeOfDay.toString());
            Session session = getSession();
            List<Group> result = session.createQuery("from Group where id not in " +
                    "(SELECT g.id from Lesson l inner join l.group g WHERE l.dayOfWeek=:day AND l.timeOfDay=:time)")
                    .setParameter("day", dayOfWeek)
                    .setParameter("time", timeOfDay)
                    .list();
            session.close();
            return result;
        }
        return Collections.emptyList();
    }

}
