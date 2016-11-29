package com.grubjack.university.dao.impl;

import com.grubjack.university.dao.FacultyDao;
import com.grubjack.university.model.Faculty;
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
@Repository
@Transactional
public class FacultyDaoImpl implements FacultyDao {

    private static Logger log = LoggerFactory.getLogger(FacultyDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void create(Faculty faculty) {
        if (faculty != null) {
            log.info("Creating new faculty " + faculty.getName());
            getSession().save(faculty);
            log.info("Faculty is created with id = " + faculty.getId());
        }
    }

    @Override
    public void update(Faculty faculty) {
        if (faculty != null) {
            Faculty facultyToUpdate = find(faculty.getId());
            if (facultyToUpdate != null) {
                facultyToUpdate.setName(faculty.getName());
                log.info("Updating faculty with id " + faculty.getId());
                getSession().update(facultyToUpdate);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Faculty> findByName(String name) {
        log.info("Finding faculty with name " + name);
        return getSession().createQuery("from Faculty where lower(name) like :name order by name")
                .setParameter("name", "%" + name.toLowerCase() + "%").list();
    }

    @Override
    public void delete(int id) {
        Faculty faculty = find(id);
        if (faculty != null) {
            log.info("Deleting faculty with id " + id);
            getSession().delete(faculty);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Faculty find(int id) {
        log.info("Finding faculty with id " + id);
        return getSession().get(Faculty.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Faculty> findAll() {
        log.info("Finding all faculties");
        return getSession().createQuery("from Faculty order by name").list();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
