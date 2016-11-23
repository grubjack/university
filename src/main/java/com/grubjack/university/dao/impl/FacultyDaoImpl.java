package com.grubjack.university.dao.impl;

import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.FacultyDao;
import com.grubjack.university.domain.Faculty;
import com.grubjack.university.domain.Group;
import com.grubjack.university.domain.Student;
import com.grubjack.university.domain.Teacher;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by grubjack on 03.11.2016.
 */
public class FacultyDaoImpl implements FacultyDao {

    private static Logger log = LoggerFactory.getLogger(FacultyDaoImpl.class);

    private Session getSession() {
        return DaoFactory.getSessionFactory().openSession();
    }

    @Override
    public void create(Faculty faculty) {
        if (faculty != null) {
            log.info("Creating new faculty " + faculty.getName());
            Session session = getSession();
            session.getTransaction().begin();
            session.save(faculty);
            session.getTransaction().commit();
            session.close();
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
                Session session = getSession();
                session.getTransaction().begin();
                session.update(facultyToUpdate);
                session.getTransaction().commit();
                session.close();
            }
        }
    }

    @Override
    public void delete(int id) {
        Faculty faculty = find(id);
        if (faculty != null) {
            log.info("Deleting faculty with id " + id);
            Session session = getSession();
            session.getTransaction().begin();
            session.delete(faculty);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override

    public Faculty find(int id) {
        log.info("Finding faculty with id " + id);
        Session session = getSession();
        Faculty result = session.get(Faculty.class, id);
        session.close();
        return result;
    }

    @Override

    public List<Faculty> findAll() {
        log.info("Finding all faculties");
        Session session = getSession();
        List<Faculty> result = session.createQuery("from Faculty").list();
        session.close();
        return result;
    }

    @Override

    public Faculty findByName(String name) {
        if (name != null) {
            log.info("Finding faculty with name " + name);
            Session session = getSession();
            Faculty result = (Faculty) session.createQuery("from Faculty where lower(name)=:name")
                    .setParameter("name", name.toLowerCase())
                    .uniqueResult();
            session.close();
            return result;
        }
        return null;
    }

    @Override

    public Faculty findByGroup(int groupId) {
        log.info("Finding faculty by group id " + groupId);
        Session session = getSession();
        Group group = session.get(Group.class, groupId);
        session.close();
        if (group != null) {
            return group.getFaculty();
        }
        return null;
    }

    @Override

    public Faculty findByStudent(int studentId) {
        log.info("Finding faculty by student id " + studentId);
        Session session = getSession();
        Student student = session.get(Student.class, studentId);
        session.close();
        if (student != null && student.getGroup() != null) {
            return student.getGroup().getFaculty();
        }
        return null;
    }

    @Override

    public Faculty findByTeacher(int teacherId) {
        log.info("Finding faculty by teacher id " + teacherId);
        Session session = getSession();
        Teacher teacher = session.get(Teacher.class, teacherId);
        session.close();
        if (teacher != null && teacher.getDepartment() != null) {
            return teacher.getDepartment().getFaculty();
        }
        return null;
    }
}
