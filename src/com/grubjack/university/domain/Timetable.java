package com.grubjack.university.domain;

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

    public void deleteUnit(DayOfWeek dayOfWeek) {
        units.remove(dayOfWeek);
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
}
