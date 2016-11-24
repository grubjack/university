package com.grubjack.university.domain;

import javax.persistence.AttributeConverter;

/**
 * Created by grubjack on 18.11.2016.
 */
public class DayTimeEnumConverter implements AttributeConverter<TimeOfDay, String> {

    @Override
    public String convertToDatabaseColumn(TimeOfDay timeOfDay) {
        return timeOfDay.toString();
    }

    @Override
    public TimeOfDay convertToEntityAttribute(String s) {
        return TimeOfDay.convert(s);
    }
}
