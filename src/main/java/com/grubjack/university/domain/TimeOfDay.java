package com.grubjack.university.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
public enum TimeOfDay {
    FIRST("08.00-10.00"),
    SECOND("10.00-12.00"),
    THIRD("12.00-14.00"),
    FOURTH("14.00-16.00"),
    FIFTH("16.00-18.00"),
    SIXTH("18.00-20.00");

    private final String text;

    private TimeOfDay(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static TimeOfDay convert(String string) {
        for (TimeOfDay time : TimeOfDay.values()) {
            if (time.toString().equals(string))
                return time;
        }
        return null;
    }

    public static List<String> names() {
        List<String> names = new ArrayList();
        for (TimeOfDay time : TimeOfDay.values()) {
            names.add(time.toString());
        }
        return names;
    }
}
