package com.grubjack.university.domain;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by grubjack on 31.10.2016.
 */
public class TimetableTest {

    Timetable timetable = new Timetable();


    @Test
    public void testNoUnits() {
        Assert.assertEquals(0, timetable.getUnits().size());
    }

    @Test
    public void testCreateUnit() {
        TimetableUnit unit1 = new TimetableUnit();
        TimetableUnit unit2 = new TimetableUnit();
        TimetableUnit unit3 = new TimetableUnit();
        timetable.createUnit(DayOfWeek.MONDAY, unit1);
        timetable.createUnit(DayOfWeek.MONDAY, unit2);
        timetable.createUnit(DayOfWeek.TUESDAY, unit3);
        Assert.assertEquals(2, timetable.getUnits().size());
    }

    @Test
    public void testCreateNullUnit() {
        timetable.createUnit(null, null);
        timetable.createUnit(DayOfWeek.FRIDAY, null);
        timetable.createUnit(null, new TimetableUnit());
        Assert.assertEquals(0, timetable.getUnits().size());
    }

    @Test
    public void testDeleteUnit() {
        TimetableUnit unit1 = new TimetableUnit();
        TimetableUnit unit2 = new TimetableUnit();
        timetable.createUnit(DayOfWeek.MONDAY, unit1);
        timetable.createUnit(DayOfWeek.TUESDAY, unit1);
        timetable.createUnit(DayOfWeek.WEDNESDAY, unit1);
        timetable.createUnit(DayOfWeek.FRIDAY, unit2);
        timetable.createUnit(DayOfWeek.SATURDAY, unit2);
        timetable.deleteUnit(DayOfWeek.MONDAY);
        Assert.assertEquals(4, timetable.getUnits().size());
    }

    @Test
    public void testDeleteNullUnit() {
        timetable.deleteUnit(null);
        Assert.assertEquals(0, timetable.getUnits().size());
    }

    @Test
    public void testDeleteDayOfWeekUnit() {
        TimetableUnit unit = new TimetableUnit();
        timetable.createUnit(DayOfWeek.MONDAY, unit);
        timetable.createUnit(DayOfWeek.TUESDAY, unit);
        timetable.deleteDayOfWeekUnit(DayOfWeek.TUESDAY);
        Assert.assertEquals(1, timetable.getUnits().size());
    }

    @Test
    public void testUpdateUnit() {
        TimetableUnit unit = new TimetableUnit();
        timetable.createUnit(DayOfWeek.MONDAY, unit);
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson());
        lessons.add(new Lesson());
        lessons.add(new Lesson());
        unit.setLessons(lessons);
        timetable.updateUnit(DayOfWeek.MONDAY, unit);

        Map<DayOfWeek, TimetableUnit> units = timetable.getUnits();
        if (units != null && units.containsKey(DayOfWeek.MONDAY)) {
            TimetableUnit dayUnit = units.get(DayOfWeek.MONDAY);
            if (dayUnit != null && dayUnit.getLessons() != null)
                Assert.assertEquals(3, dayUnit.getLessons().size());
        }
    }

    @Test
    public void testUpdateNullUnit() {
        TimetableUnit unit = new TimetableUnit();
        timetable.createUnit(DayOfWeek.MONDAY, unit);
        unit = null;
        timetable.updateUnit(DayOfWeek.MONDAY, unit);
        Assert.assertEquals(1, timetable.getUnits().size());
    }

    @Test
    public void testFindUnit() {
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson());
        lessons.add(new Lesson());
        lessons.add(new Lesson());
        TimetableUnit unit = new TimetableUnit();
        unit.setLessons(lessons);
        timetable.createUnit(DayOfWeek.MONDAY, unit);
        timetable.createUnit(DayOfWeek.TUESDAY, unit);
        Assert.assertEquals(3, timetable.findUnit(DayOfWeek.MONDAY).getLessons().size());
    }

    @Test
    public void testFindEmptyUnit() {
        Assert.assertEquals(0, timetable.findUnit(DayOfWeek.MONDAY).getLessons().size());

    }

}