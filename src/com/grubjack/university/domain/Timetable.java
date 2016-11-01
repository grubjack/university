package com.grubjack.university.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by grubjack on 28.10.2016.
 */
public class Timetable {
    private String name;
    private Map<DayOfWeek, TimetableUnit> units;

    public Timetable() {
        units = new HashMap<>();
    }

    public Timetable(String name) {
        this.name = name;
        units = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<DayOfWeek, TimetableUnit> getUnits() {
        return units;
    }

    public void setUnits(Map<DayOfWeek, TimetableUnit> units) {
        this.units = units;
    }

    public void createUnit(DayOfWeek dayOfWeek, TimetableUnit unit) {
        if (unit != null && dayOfWeek != null && !units.containsKey(dayOfWeek)) {
            units.put(dayOfWeek, unit);
        }
    }

    public void updateUnit(DayOfWeek dayOfWeek, TimetableUnit unit) {
        if (dayOfWeek != null && unit != null) {
            units.put(dayOfWeek, unit);
        }
    }

    public void deleteUnit(TimetableUnit unit) {
        units.values().removeAll(Collections.singleton(unit));
    }

    public void deleteDayOfWeekUnit(DayOfWeek dayOfWeek) {
        units.remove(dayOfWeek);
    }


    public TimetableUnit findUnit(DayOfWeek dayOfWeek) {
        TimetableUnit result = units.get(dayOfWeek);

        if (result != null) {
            return result;
        }

        return new TimetableUnit();
    }
}
