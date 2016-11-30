package com.grubjack.university.util;

import com.grubjack.university.model.Teacher;
import com.grubjack.university.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by grubjack on 30.11.2016.
 */
public class TeacherConverter implements Converter<String, Teacher> {

    @Autowired
    private TeacherService teacherService;

    @Override
    public Teacher convert(String s) {
        return teacherService.findById(Integer.parseInt(s));
    }
}
