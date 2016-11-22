package com.grubjack.university.dao.impl;

import com.grubjack.university.dao.GroupDao;
import com.grubjack.university.domain.DayOfWeek;
import com.grubjack.university.domain.Group;
import com.grubjack.university.domain.Student;
import com.grubjack.university.domain.TimeOfDay;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Created by grubjack on 03.11.2016.
 */

@Repository("groupDao")
@Transactional
public class GroupDaoImpl implements GroupDao {
    private static Logger log = LoggerFactory.getLogger(GroupDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void create(Group group, int facultyId) {
        if (group != null && group.getFaculty() != null && group.getFaculty().getId() == facultyId) {
            log.info("Creating new group " + group.getName());
            getCurrentSession().save(group);
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
                getCurrentSession().update(groupToUpdate);
            }
        }
    }

    @Override
    public void delete(int id) {
        Group group = find(id);
        if (group != null) {
            log.info("Deleting group with id " + id);
            getCurrentSession().delete(group);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Group find(int id) {
        log.info("Finding group with id " + id);
        return getCurrentSession().get(Group.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Group findByStudent(Student student) {
        if (student != null) {
            log.info("Finding group by student with id " + student.getId());
            Student findStudent = getCurrentSession().get(Student.class, student.getId());
            if (findStudent != null) {
                return findStudent.getGroup();
            }
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Group> findAll() {
        log.info("Finding all groups");
        return getCurrentSession().createQuery("from Group").list();
    }

    @Override
    @Transactional(readOnly = true)
    public Group findByName(String name) {
        if (name != null) {
            log.info("Finding group with name " + name);
            return (Group) getCurrentSession().createQuery("from Group where lower(name)=:name")
                    .setParameter("name", name.toLowerCase()).uniqueResult();
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Group> findAll(int facultyId) {
        log.info("Finding all groups with facultyId " + facultyId);
        return getCurrentSession().createQuery("from Group g where g.faculty.id=:facultyId").setParameter("facultyId", facultyId).list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Group> findAvailable(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        if (dayOfWeek != null && timeOfDay != null) {
            log.info("Finding available groups on " + dayOfWeek.toString() + " at " + timeOfDay.toString());
            return getCurrentSession().createQuery("from Group where id not in " +
                    "(SELECT g.id from Lesson l inner join l.group g WHERE l.dayOfWeek=:day AND l.timeOfDay=:time)")
                    .setParameter("day", dayOfWeek)
                    .setParameter("time", timeOfDay)
                    .list();
        }
        return Collections.emptyList();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
