package com.grubjack.university.dao.impl;

import com.grubjack.university.dao.ClassroomDao;
import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.domain.Classroom;
import com.grubjack.university.domain.DayOfWeek;
import com.grubjack.university.domain.TimeOfDay;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;


/**
 * Created by grubjack on 03.11.2016.
 */
public class ClassroomDaoImpl implements ClassroomDao {
    private static Logger log = LoggerFactory.getLogger(ClassroomDaoImpl.class);

    private Session getSession() {
        return DaoFactory.getSessionFactory().openSession();
    }

    @Override
    public void create(Classroom classroom) {
        if (classroom != null) {
            log.info("Creating new classroom " + classroom.getNumber());
            Session session = getSession();
            session.getTransaction().begin();
            session.save(classroom);
            session.getTransaction().commit();
            session.close();
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
            Session session = getSession();
            session.getTransaction().begin();
            session.update(classroomToUpdate);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public void delete(int id) {
        Classroom classroom = find(id);
        if (classroom != null) {
            log.info("Deleting classroom with id " + id);
            Session session = getSession();
            session.getTransaction().begin();
            session.delete(classroom);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public Classroom find(int id) {
        log.info("Finding classroom with id " + id);
        Session session = getSession();
        Classroom result = session.get(Classroom.class, id);
        session.close();
        return result;
    }

    @Override
    public List<Classroom> findAll() {
        log.info("Finding all classrooms");
        Session session = getSession();
        List<Classroom> result = session.createQuery("from Classroom").list();
        session.close();
        return result;
    }

    @Override
    public Classroom findByNumber(String number) {
        if (number != null) {
            log.info("Finding classroom with number " + number);
            Session session = getSession();
            Classroom result = (Classroom) session.createQuery("from Classroom where lower(number)=:number")
                    .setParameter("number", number.toLowerCase())
                    .uniqueResult();
            session.close();
            return result;
        }
        return null;
    }

    @Override
    public List<Classroom> findAvailable(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        if (dayOfWeek != null && timeOfDay != null) {
            log.info("Finding available classrooms on " + dayOfWeek.toString() + " at " + timeOfDay.toString());
            Session session = getSession();
            List<Classroom> result = session.createQuery("from Classroom where id not in " +
                    "(SELECT r.id from Lesson l inner join l.classroom r WHERE l.dayOfWeek=:day AND l.timeOfDay=:time)")
                    .setParameter("day", dayOfWeek)
                    .setParameter("time", timeOfDay)
                    .list();
            session.close();
            return result;
        }
        return Collections.emptyList();
    }
}
