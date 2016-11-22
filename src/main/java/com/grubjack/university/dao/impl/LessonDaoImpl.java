package com.grubjack.university.dao.impl;

import com.grubjack.university.dao.LessonDao;
import com.grubjack.university.domain.DayOfWeek;
import com.grubjack.university.domain.Lesson;
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
@Repository("lessonDao")
@Transactional
public class LessonDaoImpl implements LessonDao {
    private static Logger log = LoggerFactory.getLogger(LessonDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void create(Lesson lesson) {
        if (lesson != null) {
            log.info("Creating new lesson");
            getCurrentSession().save(lesson);
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
            getCurrentSession().update(lessonToUpdate);
            log.info("Updating lesson with id " + lesson.getId());
        }
    }

    @Override
    public void delete(int id) {
        Lesson lesson = find(id);
        if (lesson != null) {
            log.info("Deleting lesson with id " + id);
            getCurrentSession().delete(lesson);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Lesson find(int id) {
        log.info("Finding lesson with id " + id);
        return getCurrentSession().get(Lesson.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lesson> findFacultyLessons(int facultyId) {
        log.info("Finding lessons for facultyId " + facultyId);
        return getCurrentSession().createQuery("from Lesson l where l.group.faculty.id=:facultyId")
                .setParameter("facultyId", facultyId)
                .list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lesson> findAll() {
        log.info("Finding all lessons");
        return getCurrentSession().createQuery("from Lesson").list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lesson> findGroupLessons(int groupId, DayOfWeek dayOfWeek) {
        if (dayOfWeek != null) {
            log.info("Finding lessons for groupId " + groupId + " on " + dayOfWeek);
            return getCurrentSession().createQuery("from Lesson l where l.group.id=:groupId and l.dayOfWeek=:day")
                    .setParameter("groupId", groupId)
                    .setParameter("day", dayOfWeek)
                    .list();
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lesson> findTeacherLessons(int teacherId, DayOfWeek dayOfWeek) {
        if (dayOfWeek != null) {
            log.info("Finding lessons for teacherId " + teacherId + " on " + dayOfWeek);
            return getCurrentSession().createQuery("from Lesson l where l.teacher.id=:teacherId and l.dayOfWeek=:day")
                    .setParameter("teacherId", teacherId)
                    .setParameter("day", dayOfWeek)
                    .list();
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lesson> findGroupLessons(int groupId) {
        log.info("Finding lessons for groupId " + groupId);
        return getCurrentSession().createQuery("from Lesson l where l.group.id=:groupId")
                .setParameter("groupId", groupId)
                .list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lesson> findTeacherLessons(int teacherId) {
        log.info("Finding lessons for teacherId " + teacherId);
        return getCurrentSession().createQuery("from Lesson l where l.teacher.id=:teacherId")
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
