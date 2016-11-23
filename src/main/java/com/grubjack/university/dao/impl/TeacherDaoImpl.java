package com.grubjack.university.dao.impl;

import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.PersonDao;
import com.grubjack.university.domain.DayOfWeek;
import com.grubjack.university.domain.Teacher;
import com.grubjack.university.domain.TimeOfDay;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;


/**
 * Created by grubjack on 03.11.2016.
 */
public class TeacherDaoImpl implements PersonDao<Teacher> {

    private static Logger log = LoggerFactory.getLogger(TeacherDaoImpl.class);

    private Session getSession() {
        return DaoFactory.getSessionFactory().openSession();
    }

    @Override
    public void create(Teacher teacher, int departmentId) {
        if (teacher != null && teacher.getDepartment() != null && teacher.getDepartment().getId() == departmentId) {
            log.info("Creating new teacher " + teacher.getName());
            Session session = getSession();
            session.getTransaction().begin();
            session.save(teacher);
            session.getTransaction().commit();
            session.close();
            log.info("Teacher is created with id = " + teacher.getId());
        }
    }

    @Override
    public void update(Teacher teacher, int departmentId) {
        if (teacher != null) {
            Teacher teacherToUpdate = find(teacher.getId());
            if (teacherToUpdate != null && teacherToUpdate.getDepartment() != null && teacherToUpdate.getDepartment().getId() == departmentId) {
                teacherToUpdate.setFirstName(teacher.getFirstName());
                teacherToUpdate.setLastName(teacher.getLastName());
                teacherToUpdate.setSalary(teacher.getSalary());
                log.info("Updating teacher with id " + teacher.getId());
                Session session = getSession();
                session.getTransaction().begin();
                session.update(teacherToUpdate);
                session.getTransaction().commit();
                session.close();
            }
        }
    }

    @Override
    public void delete(int id) {
        Teacher teacher = find(id);
        if (teacher != null) {
            log.info("Deleting teacher with id " + id);
            Session session = getSession();
            session.getTransaction().begin();
            session.delete(teacher);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public Teacher find(int id) {
        log.info("Finding teacher with id " + id);
        Session session = getSession();
        Teacher result = session.get(Teacher.class, id);
        session.close();
        return result;
    }

    @Override
    public List<Teacher> findAll() {
        log.info("Finding all teachers");
        Session session = getSession();
        List<Teacher> result = session.createQuery("from Teacher").list();
        session.close();
        return result;
    }

    @Override
    public List<Teacher> findByFirstName(String firstName) {
        if (firstName != null) {
            log.info("Finding teacher with firstname " + firstName);
            Session session = getSession();
            List<Teacher> result = session.createQuery("from Teacher where lower(firstName) like :firstName")
                    .setParameter("firstName", "%" + firstName.toLowerCase() + "%").list();
            session.close();
            return result;
        }
        return Collections.emptyList();
    }


    @Override
    public List<Teacher> findByFirstName(int departmentId, String firstName) {
        if (firstName != null) {
            log.info("Finding teachers with firstname " + firstName + " from department with id " + departmentId);
            Session session = getSession();
            List<Teacher> result = session.createQuery("from Teacher t where t.department.id=:departmentId and lower(firstName) like :firstName")
                    .setParameter("departmentId", departmentId)
                    .setParameter("firstName", "%" + firstName.toLowerCase() + "%").list();
            session.close();
            return result;
        }
        return Collections.emptyList();
    }

    @Override
    public List<Teacher> findByLastName(String lastName) {
        if (lastName != null) {
            log.info("Finding teacher with lastname " + lastName);
            Session session = getSession();
            List<Teacher> result = session.createQuery("from Teacher where lower(lastName) like :lastName")
                    .setParameter("lastName", "%" + lastName.toLowerCase() + "%").list();
            session.close();
            return result;
        }
        return Collections.emptyList();
    }

    @Override
    public List<Teacher> findByLastName(int departmentId, String lastName) {
        if (lastName != null) {
            log.info("Finding teachers with lastname " + lastName + " from department with id " + departmentId);
            Session session = getSession();
            List<Teacher> result = session.createQuery("from Teacher t where t.department.id=:departmentId and lower(lastName) like :lastName")
                    .setParameter("departmentId", departmentId)
                    .setParameter("lastName", "%" + lastName.toLowerCase() + "%").list();
            session.close();
            return result;

        }
        return Collections.emptyList();
    }

    @Override
    public List<Teacher> findByName(String firstName, String lastName) {
        if (firstName != null && lastName != null) {
            log.info("Finding teachers with firstname " + lastName + " and lastname " + lastName);
            Session session = getSession();
            List<Teacher> result = session.createQuery("from Teacher where lower(firstName) like :firstName and lower(lastName) like :lastName")
                    .setParameter("firstName", "%" + firstName.toLowerCase() + "%")
                    .setParameter("lastName", "%" + lastName.toLowerCase() + "%")
                    .list();
            session.close();
            return result;
        }
        return Collections.emptyList();
    }

    @Override
    public List<Teacher> findByName(int departmentId, String firstName, String lastName) {
        if (firstName != null && lastName != null) {
            log.info("Finding teachers with firstname " + lastName + " and lastname " + lastName + " from department with id " + departmentId);
            Session session = getSession();
            List<Teacher> result = session.createQuery("from Teacher t where t.department.id=:departmentId and lower(firstName) like :firstName and lower(lastName) like :lastName")
                    .setParameter("departmentId", departmentId)
                    .setParameter("firstName", "%" + firstName.toLowerCase() + "%")
                    .setParameter("lastName", "%" + lastName.toLowerCase() + "%")
                    .list();
            session.close();
            return result;
        }
        return Collections.emptyList();
    }

    @Override
    public List<Teacher> findAll(int departmentId) {
        log.info("Finding all teachers from department with id " + departmentId);
        Session session = getSession();
        List<Teacher> result = session.createQuery("from Teacher t where t.department.id=:departmentId")
                .setParameter("departmentId", departmentId).list();
        session.close();
        return result;
    }

    @Override
    public List<Teacher> findAvailable(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        if (dayOfWeek != null && timeOfDay != null) {
            Session session = getSession();
            List<Teacher> result = session.createQuery("from Teacher where id not in " +
                    "(SELECT t.id from Lesson l inner join l.teacher t WHERE l.dayOfWeek=:day AND l.timeOfDay=:time)")
                    .setParameter("day", dayOfWeek)
                    .setParameter("time", timeOfDay)
                    .list();
            session.close();
            return result;
        }
        return Collections.emptyList();
    }
}
