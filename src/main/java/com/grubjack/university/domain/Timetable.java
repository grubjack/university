package com.grubjack.university.domain;

import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.LessonDao;
import com.grubjack.university.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
public class Timetable {
    private String name;
    private List<TimetableUnit> units;

    private static Logger log = LoggerFactory.getLogger(Timetable.class);

    private LessonDao lessonDao = DaoFactory.getInstance().getLessonDao();

    public Timetable() {
    }

    public Timetable(String name) {
        this.name = name;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Timetable timetable = (Timetable) o;

        if (name != null ? !name.equals(timetable.name) : timetable.name != null) return false;
        return units != null ? units.equals(timetable.units) : timetable.units == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (units != null ? units.hashCode() : 0);
        return result;
    }

    public void createUnit(TimetableUnit unit) {
        if (unit != null && !getUnits().contains(unit)) {
            try {
                for (Lesson lesson : unit.getLessons()) {
                    lessonDao.create(lesson);
                }
                getUnits().add(unit);
            } catch (DaoException e) {
                log.warn("Can't create timetable unit");
            }
        }
    }

    public void deleteUnit(TimetableUnit unit) {
        if (unit != null) {
            try {
                lessonDao.delete(unit.getLessons());
                getUnits().remove(unit);
            } catch (DaoException e) {
                log.warn("Can't delete timetable unit");
            }
        }
    }

    public void updateUnit(TimetableUnit unit) {
        TimetableUnit oldUnit = null;
        if (unit != null) {
            try {
                oldUnit = new TimetableUnit(unit.getDayOfWeek());
                oldUnit.setLessons(lessonDao.findByDay(unit.getDayOfWeek()));
            } catch (DaoException e) {
                log.warn("Can't find unit");
            }
        }
        if (oldUnit != null) {
            try {
                lessonDao.update(unit.getLessons());
                getUnits().remove(oldUnit);
                getUnits().add(unit);
            } catch (DaoException e) {
                log.warn("Can't update timetable unit");
            }
        }
    }

    public TimetableUnit findUnit(String day) {
        for (TimetableUnit unit : units) {
            if (unit.getDayOfWeek().toString().equals(day)) {
                return unit;
            }
        }
        return null;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TimetableUnit> getUnits() {
        if (units == null) {
            List<Lesson> lessons = null;
            try {
                lessons = lessonDao.findAll();
            } catch (DaoException e) {
                log.warn("Can't find lessons");
            }
            if (lessons != null) {
                units = new ArrayList<>();
                for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
                    TimetableUnit unit = new TimetableUnit(dayOfWeek);
                    unit.setLessons(new ArrayList<Lesson>());
                    Iterator<Lesson> iterator = lessons.iterator();
                    while (iterator.hasNext()) {
                        Lesson lesson = iterator.next();
                        if (lesson.getDayOfWeek().equals(dayOfWeek)) {
                            unit.getLessons().add(lesson);
                            iterator.remove();
                        }
                    }
                    units.add(unit);
                }
            }
        }
        return units;
    }

    public void setUnits(List<TimetableUnit> units) {
        this.units = units;
    }
}
