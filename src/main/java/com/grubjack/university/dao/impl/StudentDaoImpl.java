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

import java.util.Collections;
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

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void create(Student student, int groupId) {
        if (student != null && student.getGroup() != null && student.getGroup().getId() == groupId) {
            log.info("Creating new student " + student.getName());
            getCurrentSession().save(student);
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
                getCurrentSession().update(studentToUpdate);
            }
        }
    }

    @Override
    public void delete(int id) {
        Student student = find(id);
        if (student != null) {
            log.info("Deleting student with id " + id);
            getCurrentSession().delete(student);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Student find(int id) {
        log.info("Finding student with id " + id);
        return getCurrentSession().get(Student.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        log.info("Finding all students");
        return getCurrentSession().createQuery("from Student").list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findByFirstName(String firstName) {
        if (firstName != null) {
            log.info("Finding student with firstname " + firstName);
            return getCurrentSession().createQuery("from Student where lower(firstName) like :firstName")
                    .setParameter("firstName", "%" + firstName.toLowerCase() + "%").list();
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findByFirstName(int groupId, String firstName) {
        if (firstName != null) {
            log.info("Finding students with firstname " + firstName + " from group with id " + groupId);
            return getCurrentSession().createQuery("from Student s where s.group.id=:groupId and lower(firstName) like :firstName")
                    .setParameter("groupId", groupId)
                    .setParameter("firstName", "%" + firstName.toLowerCase() + "%").list();
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findByLastName(String lastName) {
        if (lastName != null) {
            log.info("Finding students with lastname " + lastName);
            return getCurrentSession().createQuery("from Student where lower(lastName) like :lastName")
                    .setParameter("lastName", "%" + lastName.toLowerCase() + "%").list();
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findByLastName(int groupId, String lastName) {
        if (lastName != null) {
            log.info("Finding students with lastname " + lastName + " from group with id " + groupId);
            return getCurrentSession().createQuery("from Student s where s.group.id=:groupId and lower(lastName) like :lastName")
                    .setParameter("groupId", groupId)
                    .setParameter("lastName", "%" + lastName.toLowerCase() + "%").list();
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findByName(String firstName, String lastName) {
        if (firstName != null && lastName != null) {
            log.info("Finding students with firstname " + lastName + " and lastname " + lastName);
            return getCurrentSession().createQuery("from Student where lower(firstName) like :firstName and lower(lastName) like :lastName")
                    .setParameter("firstName", "%" + firstName.toLowerCase() + "%")
                    .setParameter("lastName", "%" + lastName.toLowerCase() + "%")
                    .list();
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findByName(int groupId, String firstName, String lastName) {
        if (firstName != null && lastName != null) {
            log.info("Finding students with firstname " + lastName + " and lastname " + lastName + " from group with id " + groupId);
            return getCurrentSession().createQuery("from Student s where s.group.id=:groupId and lower(firstName) like :firstName and lower(lastName) like :lastName")
                    .setParameter("groupId", groupId)
                    .setParameter("firstName", "%" + firstName.toLowerCase() + "%")
                    .setParameter("lastName", "%" + lastName.toLowerCase() + "%")
                    .list();
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAll(int groupId) {
        log.info("Finding all students from group with id " + groupId);
        return getCurrentSession().createQuery("from Student s where s.group.id=:groupId")
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
