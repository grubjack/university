package com.grubjack.university.dao.impl;

import com.grubjack.university.dao.FacultyDao;
import com.grubjack.university.domain.Faculty;
import com.grubjack.university.domain.Group;
import com.grubjack.university.domain.Student;
import com.grubjack.university.domain.Teacher;
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

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void create(Faculty faculty) {
        if (faculty != null) {
            log.info("Creating new faculty " + faculty.getName());
            getCurrentSession().save(faculty);
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
                getCurrentSession().update(facultyToUpdate);
            }
        }
    }

    @Override
    public void delete(int id) {
        Faculty faculty = find(id);
        if (faculty != null) {
            log.info("Deleting faculty with id " + id);
            getCurrentSession().delete(faculty);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Faculty find(int id) {
        log.info("Finding faculty with id " + id);
        return getCurrentSession().get(Faculty.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Faculty> findAll() {
        log.info("Finding all faculties");
        return getCurrentSession().createQuery("from Faculty").list();
    }

    @Override
    @Transactional(readOnly = true)
    public Faculty findByName(String name) {
        if (name != null) {
            log.info("Finding faculty with name " + name);
            return (Faculty) getCurrentSession().createQuery("from Faculty where lower(name)=:name")
                    .setParameter("name", name.toLowerCase())
                    .uniqueResult();
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Faculty findByGroup(int groupId) {
        log.info("Finding faculty by group id " + groupId);
        Group group = getCurrentSession().get(Group.class, groupId);
        if (group != null) {
            return group.getFaculty();
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Faculty findByStudent(int studentId) {
        log.info("Finding faculty by student id " + studentId);
        Student student = getCurrentSession().get(Student.class, studentId);
        if (student != null && student.getGroup() != null) {
            return student.getGroup().getFaculty();
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Faculty findByTeacher(int teacherId) {
        log.info("Finding faculty by teacher id " + teacherId);
        Teacher teacher = getCurrentSession().get(Teacher.class, teacherId);
        if (teacher != null && teacher.getDepartment() != null) {
            return teacher.getDepartment().getFaculty();
        }
        return null;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
