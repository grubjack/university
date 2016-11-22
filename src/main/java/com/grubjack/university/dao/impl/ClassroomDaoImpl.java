package com.grubjack.university.dao.impl;

import com.grubjack.university.dao.ClassroomDao;
import com.grubjack.university.domain.Classroom;
import com.grubjack.university.domain.DayOfWeek;
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

@Repository("classroomDao")
@Transactional
public class ClassroomDaoImpl implements ClassroomDao {

    private static Logger log = LoggerFactory.getLogger(ClassroomDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void create(Classroom classroom) {
        if (classroom != null) {
            log.info("Creating new classroom " + classroom.getNumber());
            getCurrentSession().save(classroom);
            log.info("Classroom is created with id = " + classroom.getId());
        }
    }

    @Override
    public void update(Classroom classroom) {
        if (classroom != null) {
            Classroom classroomToUpdate = find(classroom.getId());
            classroomToUpdate.setNumber(classroom.getNumber());
            classroomToUpdate.setLocation(classroom.getLocation());
            classroomToUpdate.setCapacity(classroom.getCapacity());
            log.info("Updating classroom with id " + classroom.getId());
            getCurrentSession().update(classroomToUpdate);
        }
    }

    @Override
    public void delete(int id) {
        Classroom classroom = find(id);
        if (classroom != null) {
            log.info("Deleting classroom with id " + id);
            getCurrentSession().delete(classroom);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Classroom find(int id) {
        log.info("Finding classroom with id " + id);
        return getCurrentSession().get(Classroom.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Classroom> findAll() {
        log.info("Finding all classrooms");
        return getCurrentSession().createQuery("from Classroom").list();
    }

    @Override
    @Transactional(readOnly = true)
    public Classroom findByNumber(String number) {
        if (number != null) {
            log.info("Finding classroom with number " + number);
            return (Classroom) getCurrentSession().createQuery("from Classroom where lower(number)=:number")
                    .setParameter("number", number.toLowerCase())
                    .uniqueResult();
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Classroom> findAvailable(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        if (dayOfWeek != null && timeOfDay != null) {
            log.info("Finding available classrooms on " + dayOfWeek.toString() + " at " + timeOfDay.toString());
            return getCurrentSession().createQuery("from Classroom where id not in " +
                    "(SELECT r.id from Lesson l inner join l.classroom r WHERE l.dayOfWeek=:day AND l.timeOfDay=:time)")
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
