package com.grubjack.university.service;

import com.grubjack.university.dao.ClassroomDao;
import com.grubjack.university.dao.GroupDao;
import com.grubjack.university.dao.LessonDao;
import com.grubjack.university.dao.PersonDao;
import com.grubjack.university.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by grubjack on 29.11.2016.
 */
@Service
public class LessonService implements BaseService<Lesson> {

    @Autowired
    private LessonDao lessonDao;

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private PersonDao<Teacher> teacherDao;

    @Autowired
    private PersonDao<Student> studentDao;

    @Autowired
    private ClassroomDao classroomDao;

    @Override
    public List<Lesson> findAll() {
        return lessonDao.findAll();
    }

    @Override
    public Lesson findById(int id) {
        return lessonDao.find(id);
    }

    @Override
    public void delete(int id) {
        lessonDao.delete(id);
    }


    public void create(Lesson lesson, int teacherId, int classroomId, int groupId) {
        if (lesson != null && lesson.getDayOfWeek() != null && lesson.getTimeOfDay() != null) {

            DayOfWeek day = lesson.getDayOfWeek();
            TimeOfDay time = lesson.getTimeOfDay();

            Teacher teacher = teacherDao.find(teacherId);
            Classroom classroom = classroomDao.find(classroomId);
            Group group = groupDao.find(groupId);

            lesson.setClassroom(classroom);
            lesson.setTeacher(teacher);
            lesson.setGroup(group);

            if (classroomDao.findAvailable(day, time).contains(classroom) &&
                    teacherDao.findAvailable(day, time).contains(teacher) &&
                    groupDao.findAvailable(day, time).contains(group)) {
                lessonDao.create(lesson);
            }
        }
    }

    public void create(Lesson lesson) {
        if (lesson != null && lesson.getDayOfWeek() != null && lesson.getTimeOfDay() != null) {

            DayOfWeek day = lesson.getDayOfWeek();
            TimeOfDay time = lesson.getTimeOfDay();

            Teacher teacher = lesson.getTeacher();
            Classroom classroom = lesson.getClassroom();
            Group group = lesson.getGroup();

            if (teacher != null && classroom != null && group != null) {
                if (classroomDao.findAvailable(day, time).contains(classroom) &&
                        teacherDao.findAvailable(day, time).contains(teacher) &&
                        groupDao.findAvailable(day, time).contains(group)) {
                    lessonDao.create(lesson);
                }
            }
        }
    }

    public void update(Lesson lesson) {
        if (lesson != null) {
            Lesson oldLesson = lessonDao.find(lesson.getId());
            DayOfWeek day = lesson.getDayOfWeek();
            TimeOfDay time = lesson.getTimeOfDay();


            if (oldLesson != null && day != null && time != null) {

                if ((!oldLesson.getClassroom().equals(lesson.getClassroom()) && !classroomDao.findAvailable(day, time).contains(lesson.getClassroom())) ||
                        (!oldLesson.getGroup().equals(lesson.getGroup()) && !groupDao.findAvailable(day, time).contains(lesson.getGroup())) ||
                        (!oldLesson.getTeacher().equals(lesson.getTeacher()) && !teacherDao.findAvailable(day, time).contains(lesson.getTeacher()))) {
                    return;
                }
            }
            lessonDao.update(lesson);
        }
    }

    public void update(Lesson lesson, int teacherId, int classroomId, int groupId) {
        if (lesson != null) {

            Lesson oldLesson = lessonDao.find(lesson.getId());
            DayOfWeek day = lesson.getDayOfWeek();
            TimeOfDay time = lesson.getTimeOfDay();

            Teacher teacher = teacherDao.find(teacherId);
            Classroom classroom = classroomDao.find(classroomId);
            Group group = groupDao.find(groupId);

            if (teacher != null && classroom != null && group != null) {
                if (oldLesson != null && day != null && time != null) {
                    if ((!oldLesson.getClassroom().equals(lesson.getClassroom()) && !classroomDao.findAvailable(day, time).contains(lesson.getClassroom())) ||
                            (!oldLesson.getGroup().equals(lesson.getGroup()) && !groupDao.findAvailable(day, time).contains(lesson.getGroup())) ||
                            (!oldLesson.getTeacher().equals(lesson.getTeacher()) && !teacherDao.findAvailable(day, time).contains(lesson.getTeacher()))) {
                        return;
                    }
                }
                lesson.setClassroom(classroom);
                lesson.setGroup(group);
                lesson.setTeacher(teacher);
                lessonDao.update(lesson);
            }

        }
    }


    public List<Timetable> findGroupTimetables() {
        List<Timetable> result = new ArrayList<>();
        List<Lesson> lessons = lessonDao.findAll();
        for (Group group : groupDao.findAll()) {
            Timetable timetable = new Timetable(group.getName());
            Iterator<Lesson> iterator = lessons.iterator();
            while (iterator.hasNext()) {
                Lesson lesson = iterator.next();
                if (lesson.getGroup().getId() == group.getId()) {
                    timetable.getLessons().add(lesson);
                    iterator.remove();
                }
            }
            result.add(timetable);
        }
        return result;
    }

    public List<Timetable> findGroupTimetables(int facultyId) {
        List<Timetable> result = new ArrayList<>();
        for (Group group : groupDao.findAll(facultyId)) {
            Timetable timetable = new Timetable(group.getName());
            timetable.setLessons(lessonDao.findGroupLessons(group.getId()));
            result.add(timetable);
        }
        return result;
    }

    public Timetable findGroupTimetable(int groupId) {
        Timetable result = new Timetable();
        Group group = groupDao.find(groupId);
        if (group != null) {
            result.setName(group.getName());
            result.setLessons(lessonDao.findGroupLessons(group.getId()));
        }
        return result;
    }

    public Timetable findTeacherTimetable(int teacherId) {
        Timetable result = new Timetable();
        Teacher teacher = teacherDao.find(teacherId);
        if (teacher != null) {
            result.setName(teacher.getName());
            result.setLessons(lessonDao.findTeacherLessons(teacher.getId()));
        }
        return result;
    }

    public Timetable findStudentTimetable(int studentId) {
        Timetable result = new Timetable();
        Student student = studentDao.find(studentId);
        if (student != null && student.getGroup() != null) {
            result = findGroupTimetable(student.getGroup().getId());
            result.setName(student.getName());
        }
        return result;
    }
}
