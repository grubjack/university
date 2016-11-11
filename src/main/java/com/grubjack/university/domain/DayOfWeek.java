package com.grubjack.university.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grubjack on 28.10.2016.
 */
public enum DayOfWeek {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    public static List<String> names() {
        List<String> names = new ArrayList();
        for (DayOfWeek day : DayOfWeek.values()) {
            names.add(day.toString());
        }
        return names;
    }
}
