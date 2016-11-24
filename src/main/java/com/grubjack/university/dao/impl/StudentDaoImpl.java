package com.grubjack.university.dao.impl;

import com.grubjack.university.dao.PersonDao;
import com.grubjack.university.domain.DayOfWeek;
import com.grubjack.university.domain.Student;
import com.grubjack.university.domain.TimeOfDay;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by grubjack on 03.11.2016.
 */
@Repository("studentDao")
@Transactional
public class StudentDaoImpl implements PersonDao<Student> {

    private static Logger log = LoggerFactory.getLogger(StudentDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void create(Student student, int groupId) {
        if (student != null && student.getGroup() != null && student.getGroup().getId() == groupId) {
            log.info("Creating new student " + student.getName());
            getSession().save(student);
            log.info("Student is created with id = " + student.getId());
        }
    }

    @Override
    public void update(Student student, int groupId) {
        if (student != null) {
            Student studentToUpdate = find(student.getId());
            if (studentToUpdate != null && studentToUpdate.getGroup() != null && studentToUpdate.getGroup().getId() == groupId) {
                studentToUpdate.setFirstName(student.getFirstName());
                studentToUpdate.setLastName(student.getLastName());
                log.info("Updating student with id " + student.getId());
                getSession().update(studentToUpdate);
            }
        }
    }

    @Override
    public void delete(int id) {
        Student student = find(id);
        if (student != null) {
            log.info("Deleting student with id " + id);
            getSession().delete(student);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Student find(int id) {
        log.info("Finding student with id " + id);
        return getSession().get(Student.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        log.info("Finding all students");
        return getSession().createQuery("from Student").list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAll(int groupId) {
        log.info("Finding all students from group with id " + groupId);
        return getSession().createQuery("from Student s where s.group.id=:groupId")
                .setParameter("groupId", groupId).list();
    }

    @Override
    public List<Student> findAvailable(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        throw new UnsupportedOperationException();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
