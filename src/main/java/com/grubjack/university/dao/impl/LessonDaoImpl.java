package com.grubjack.university.dao.impl;

import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.LessonDao;
import com.grubjack.university.domain.DayOfWeek;
import com.grubjack.university.domain.Lesson;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * Created by grubjack on 03.11.2016.
 */
public class LessonDaoImpl implements LessonDao {
    private SessionFactory sessionFactory;
    private static Logger log = LoggerFactory.getLogger(LessonDaoImpl.class);

    private Session getSession() {
        return DaoFactory.getSessionFactory().openSession();
    }

    @Override
    public void create(Lesson lesson) {
        if (lesson != null) {
            log.info("Creating new lesson");
            Session session = getSession();
            session.save(lesson);
            session.flush();
            session.close();
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
            Session session = getSession();
            session.update(lessonToUpdate);
            session.flush();
            session.close();
            log.info("Updating lesson with id " + lesson.getId());
        }
    }

    @Override
    public void delete(int id) {
        Lesson lesson = find(id);
        if (lesson != null) {
            log.info("Deleting lesson with id " + id);
            Session session = getSession();
            session.delete(lesson);
            session.close();
        }
    }

    @Override
    public Lesson find(int id) {
        log.info("Finding lesson with id " + id);
        Session session = getSession();
        Lesson result = session.get(Lesson.class, id);
        session.close();
        return result;
    }

    @Override
    public List<Lesson> findFacultyLessons(int facultyId) {
        log.info("Finding lessons for facultyId " + facultyId);
        Session session = getSession();
        List<Lesson> result = session.createQuery("from Lesson l where l.group.faculty.id=:facultyId")
                .setParameter("facultyId", facultyId)
                .list();
        session.close();
        return result;
    }

    @Override
    public List<Lesson> findAll() {
        log.info("Finding all lessons");
        Session session = getSession();
        List<Lesson> result = session.createQuery("from Lesson").list();
        session.close();
        return result;
    }

    @Override
    public List<Lesson> findGroupLessons(int groupId, DayOfWeek dayOfWeek) {
        if (dayOfWeek != null) {
            log.info("Finding lessons for groupId " + groupId + " on " + dayOfWeek);
            Session session = getSession();
            List<Lesson> result = session.createQuery("from Lesson l where l.group.id=:groupId and l.dayOfWeek=:day")
                    .setParameter("groupId", groupId)
                    .setParameter("day", dayOfWeek)
                    .list();
            session.close();
            return result;
        }
        return Collections.emptyList();
    }

    @Override
    public List<Lesson> findTeacherLessons(int teacherId, DayOfWeek dayOfWeek) {
        if (dayOfWeek != null) {
            log.info("Finding lessons for teacherId " + teacherId + " on " + dayOfWeek);
            Session session = getSession();
            List<Lesson> result = session.createQuery("from Lesson l where l.teacher.id=:teacherId and l.dayOfWeek=:day")
                    .setParameter("teacherId", teacherId)
                    .setParameter("day", dayOfWeek)
                    .list();
            session.close();
            return result;
        }
        return Collections.emptyList();
    }

    @Override
    public List<Lesson> findGroupLessons(int groupId) {
        log.info("Finding lessons for groupId " + groupId);
        Session session = getSession();
        List<Lesson> result = session.createQuery("from Lesson l where l.group.id=:groupId")
                .setParameter("groupId", groupId)
                .list();
        session.close();
        return result;
    }

    @Override
    public List<Lesson> findTeacherLessons(int teacherId) {
        log.info("Finding lessons for teacherId " + teacherId);
        Session session = getSession();
        List<Lesson> result = session.createQuery("from Lesson l where l.teacher.id=:teacherId")
                .setParameter("teacherId", teacherId)
                .list();
        session.close();
        return result;
    }
}
