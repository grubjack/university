package com.grubjack.university.domain;

import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.LessonDao;
import com.grubjack.university.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
public class TimetableUnit {
    private DayOfWeek dayOfWeek;
    private List<Lesson> lessons;

    private LessonDao lessonDao = DaoFactory.getInstance().getLessonDao();

    private static Logger log = LoggerFactory.getLogger(TimetableUnit.class);

    public TimetableUnit(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;

    }

    public TimetableUnit() {

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimetableUnit that = (TimetableUnit) o;

        if (dayOfWeek != that.dayOfWeek) return false;
        return lessons != null ? lessons.equals(that.lessons) : that.lessons == null;

    }

    @Override
    public int hashCode() {
        int result = dayOfWeek != null ? dayOfWeek.hashCode() : 0;
        result = 31 * result + (lessons != null ? lessons.hashCode() : 0);
        return result;
    }

    public Lesson findLesson(String time) {
        for (Lesson lesson : lessons) {
            if (lesson.getTimeOfDay().toString().equals(time)) {
                return lesson;
            }
        }
        return null;
    }

    public void createLesson(Lesson lesson) {
        if (lesson != null && !getLessons().contains(lesson)) {
            try {
                lessonDao.create(lesson);
                getLessons().add(lesson);
            } catch (DaoException e) {
                log.warn("Can't create lesson");
            }
        }
    }

    public void deleteLesson(Lesson lesson) {
        if (lesson != null) {
            try {
                lessonDao.delete(lesson.getId());
                getLessons().remove(lesson);
            } catch (DaoException e) {
                log.warn("Can't delete lesson");
            }
        }
    }

    public void updateLesson(Lesson lesson) {
        Lesson oldLesson = null;
        try {
            oldLesson = lessonDao.find(lesson.getId());
        } catch (DaoException e) {
            log.warn("Can't find lesson");
        }
        if (oldLesson != null) {
            try {
                lessonDao.update(lesson);
                getLessons().remove(oldLesson);
                getLessons().add(lesson);
            } catch (DaoException e) {
                log.warn("Can't update lesson");
            }
        }
    }


    public List<Lesson> getLessons() {
        if (lessons == null) {
            try {
                lessons = lessonDao.findByDay(dayOfWeek);
            } catch (DaoException e) {
                log.warn("Can't find lessons");
            }
        }
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }


}
