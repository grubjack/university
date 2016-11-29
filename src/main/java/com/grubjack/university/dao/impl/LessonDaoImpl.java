package com.grubjack.university.dao.impl;

import com.grubjack.university.dao.LessonDao;
import com.grubjack.university.model.Lesson;
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
public class LessonDaoImpl implements LessonDao {
    private static Logger log = LoggerFactory.getLogger(LessonDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void create(Lesson lesson) {
        if (lesson != null) {
            log.info("Creating new lesson");
            getSession().save(lesson);
            log.info("Lesson is created with id = " + lesson.getId());
        }
    }

    @Override
    public void update(Lesson lesson) {
        if (lesson != null) {
            Lesson lessonToUpdate = find(lesson.getId());
            lessonToUpdate.setSubject(lesson.getSubject());
            lessonToUpdate.setDayOfWeek(lesson.getDayOfWeek());
            lessonToUpdate.setTimeOfDay(lesson.getTimeOfDay());
            lessonToUpdate.setClassroom(lesson.getClassroom());
            lessonToUpdate.setTeacher(lesson.getTeacher());
            lessonToUpdate.setGroup(lesson.getGroup());
            getSession().update(lessonToUpdate);
            log.info("Updating lesson with id " + lesson.getId());
        }
    }

    @Override
    public void delete(int id) {
        Lesson lesson = find(id);
        if (lesson != null) {
            log.info("Deleting lesson with id " + id);
            getSession().delete(lesson);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Lesson find(int id) {
        log.info("Finding lesson with id " + id);
        return getSession().get(Lesson.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lesson> findAll() {
        log.info("Finding all lessons");
        return getSession().createQuery("from Lesson").list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lesson> findGroupLessons(int groupId) {
        log.info("Finding lessons for groupId " + groupId);
        return getSession().createQuery("from Lesson l where l.group.id=:groupId")
                .setParameter("groupId", groupId)
                .list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lesson> findTeacherLessons(int teacherId) {
        log.info("Finding lessons for teacherId " + teacherId);
        return getSession().createQuery("from Lesson l where l.teacher.id=:teacherId")
                .setParameter("teacherId", teacherId)
                .list();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
