package com.grubjack.university.dao.impl;

import com.grubjack.university.dao.PersonDao;
import com.grubjack.university.model.DayOfWeek;
import com.grubjack.university.model.Teacher;
import com.grubjack.university.model.TimeOfDay;
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
@Repository("teacherDao")
@Transactional
public class TeacherDaoImpl implements PersonDao<Teacher> {

    private static Logger log = LoggerFactory.getLogger(TeacherDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void create(Teacher teacher, int departmentId) {
        if (teacher != null) {
            log.info("Creating new teacher " + teacher.getName() + " by department with id " + departmentId);
            getSession().save(teacher);
            log.info("Teacher is created with id = " + teacher.getId());
        }
    }

    @Override
    public void update(Teacher teacher, int departmentId) {
        if (teacher != null) {
            Teacher teacherToUpdate = find(teacher.getId());
            if (teacherToUpdate != null) {
                teacherToUpdate.setFirstName(teacher.getFirstName());
                teacherToUpdate.setLastName(teacher.getLastName());
                teacherToUpdate.setSalary(teacher.getSalary());
                log.info("Updating teacher with id " + teacher.getId() + " by department with id " + departmentId);
                getSession().update(teacherToUpdate);
            }
        }
    }

    @Override
    public void delete(int id) {
        Teacher teacher = find(id);
        if (teacher != null) {
            log.info("Deleting teacher with id " + id);
            getSession().delete(teacher);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Teacher find(int id) {
        log.info("Finding teacher with id " + id);
        return getSession().get(Teacher.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Teacher> findAll() {
        log.info("Finding all teachers");
        return getSession().createQuery("from Teacher order by lastName,firstName").list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Teacher> findAll(int departmentId) {
        log.info("Finding all teachers from department with id " + departmentId);
        return getSession().createQuery("from Teacher t where t.department.id=:departmentId order by t.lastName,t.firstName")
                .setParameter("departmentId", departmentId).list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Teacher> findAvailable(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        if (dayOfWeek != null && timeOfDay != null) {
            log.info("Finding available teachers on " + dayOfWeek.toString() + " at " + timeOfDay.toString());
            return getSession().createQuery("from Teacher where id not in " +
                    "(SELECT t.id from Lesson l inner join l.teacher t WHERE l.dayOfWeek=:day AND l.timeOfDay=:time) order by lastName,firstName")
                    .setParameter("day", dayOfWeek)
                    .setParameter("time", timeOfDay)
                    .list();
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Teacher> findByName(String name) {
        log.info("Finding teacher with name " + name);
        return getSession().createQuery("from Teacher where lower(firstName) like :name or lower(lastName) like :name")
                .setParameter("name", "%" + name.toLowerCase() + "%").list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Teacher> findByName(String name, int departmentId) {
        log.info("Finding teacher with name " + name + " by department with id " + departmentId);
        return getSession().createQuery("from Teacher t where t.department.id=:departmentId and (lower(t.firstName) like :name or lower(t.lastName) like :name) order by t.lastName,t.firstName")
                .setParameter("departmentId", departmentId)
                .setParameter("name", "%" + name.toLowerCase() + "%").list();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
