package com.grubjack.university.dao.impl;

import com.grubjack.university.dao.PersonDao;
import com.grubjack.university.model.DayOfWeek;
import com.grubjack.university.model.Student;
import com.grubjack.university.model.TimeOfDay;
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
        if (student != null) {
            log.info("Creating new student " + student.getName() + " by group with id " + groupId);
            getSession().save(student);
            log.info("Student is created with id = " + student.getId());
        }
    }

    @Override
    public void update(Student student, int groupId) {
        if (student != null) {
            Student studentToUpdate = find(student.getId());
            if (studentToUpdate != null) {
                studentToUpdate.setFirstName(student.getFirstName());
                studentToUpdate.setLastName(student.getLastName());
                log.info("Updating student with id " + student.getId() + " by group with id " + groupId);
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
        return getSession().createQuery("from Student order by lastName,firstName").list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAll(int groupId) {
        log.info("Finding all students from group with id " + groupId);
        return getSession().createQuery("from Student s where s.group.id=:groupId order by s.lastName,s.firstName")
                .setParameter("groupId", groupId).list();
    }

    @Override
    public List<Student> findAvailable(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findByName(String name) {
        log.info("Finding student with name " + name);
        return getSession().createQuery("from Student where lower(firstName) like :name or lower(lastName) like :name order by lastName,firstName")
                .setParameter("name", "%" + name.toLowerCase() + "%").list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findByName(String name, int groupId) {
        log.info("Finding student with name " + name + " by group with id " + groupId);
        return getSession().createQuery("from Student s where s.group.id=:groupId and (lower(s.firstName) like :name or lower(s.lastName) like :name) order by s.lastName,s.firstName")
                .setParameter("groupId", groupId)
                .setParameter("name", "%" + name.toLowerCase() + "%").list();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
