package com.grubjack.university.util;

import com.grubjack.university.model.DayOfWeek;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by grubjack on 30.11.2016.
 */
public class DayOfWeekConverter implements Converter<String, DayOfWeek> {

    @Override
    public DayOfWeek convert(String s) {
        return DayOfWeek.valueOf(s.trim().toUpperCase());
    }
}
