package com.grubjack.university.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
public class Timetable {
    private String name;
    private List<TimetableUnit> units = new ArrayList<>();

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
        return units;
    }

    public void setUnits(List<TimetableUnit> units) {
        this.units = units;
    }
}
