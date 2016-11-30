package com.grubjack.university.util;

import com.grubjack.university.model.TimeOfDay;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by grubjack on 30.11.2016.
 */
public class TimeOfDayConverter implements Converter<String, TimeOfDay> {

    @Override
    public TimeOfDay convert(String s) {
        System.out.println("try to convert '"+s+"'");
        return TimeOfDay.convert(s);
    }
}
