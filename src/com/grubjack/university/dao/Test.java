package com.grubjack.university.dao;

import com.grubjack.university.domain.Group;
import com.grubjack.university.domain.Student;

import java.util.List;

/**
 * Created by grubjack on 03.11.2016.
 */
public class Test {

    public static void main(String[] args) {

        GroupDao groupDao = DaoFactory.getInstance().getGroupDao();

        Group group = groupDao.find(1027);

        List<Student> students = group.getStudents();

        System.out.println(students.size());


    }
}
