package com.grubjack.university.util;

import com.grubjack.university.model.Classroom;
import com.grubjack.university.model.Group;
import com.grubjack.university.service.ClassroomService;
import com.grubjack.university.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by grubjack on 30.11.2016.
 */
public class GroupConverter implements Converter<String, Group> {

    @Autowired
    private GroupService groupService;

    @Override
    public Group convert(String s) {
        return groupService.findById(Integer.parseInt(s));
    }
}
