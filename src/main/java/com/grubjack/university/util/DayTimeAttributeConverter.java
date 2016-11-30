package com.grubjack.university.util;

import com.grubjack.university.model.TimeOfDay;

import javax.persistence.AttributeConverter;

/**
 * Created by grubjack on 18.11.2016.
 */
public class DayTimeAttributeConverter implements AttributeConverter<TimeOfDay, String> {

    @Override
    public String convertToDatabaseColumn(TimeOfDay timeOfDay) {
        return timeOfDay.toString();
    }

    @Override
    public TimeOfDay convertToEntityAttribute(String s) {
        return TimeOfDay.convert(s);
    }
}
