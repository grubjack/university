package com.grubjack.university.util;

import com.grubjack.university.model.Classroom;
import com.grubjack.university.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by grubjack on 30.11.2016.
 */
public class ClassroomConverter implements Converter<String, Classroom> {

    @Autowired
    private ClassroomService classroomService;

    @Override
    public Classroom convert(String s) {
        return classroomService.findById(Integer.parseInt(s));
    }
}
