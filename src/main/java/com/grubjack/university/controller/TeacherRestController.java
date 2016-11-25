package com.grubjack.university.controller;

import com.grubjack.university.domain.Department;
import com.grubjack.university.domain.Teacher;
import com.grubjack.university.domain.University;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by grubjack on 24.11.2016.
 */

@RestController
@RequestMapping(value = "/rest/teachers", produces = MediaType.APPLICATION_JSON_VALUE)
public class TeacherRestController {

    @Autowired
    private University university;

    private static Logger log = LoggerFactory.getLogger(TeacherRestController.class);

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Teacher> getAll() {
        log.info("Getting all teachers");
        return university.getTeachers();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Teacher get(@PathVariable("id") int id) {
        log.info("Getting teacher with id: " + id);
        return university.findTeacher(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") int id) {
        Teacher teacher = university.findTeacher(id);
        if (teacher != null && teacher.getDepartment() != null) {
            log.info("Deleting teacher with id " + id);
            teacher.getDepartment().deleteTeacher(teacher);
            log.info("Teacher deleted with id " + id);
        } else {
            log.info("Teacher with id " + id + " not found");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void update(@RequestBody Teacher teacher, @PathVariable("id") int id) {
        log.info("Updating teacher with id: " + id);
        Teacher oldTeacher = university.findTeacher(id);
        if (oldTeacher != null && oldTeacher.getDepartment() != null) {
            teacher.setId(id);
            oldTeacher.getDepartment().updateTeacher(teacher);
            log.info("Teacher updated successfully with id: " + id);
        } else {
            log.info("Teacher with id " + id + " not found");
        }
    }

    @RequestMapping(value = "/{departmentId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void create(@RequestBody Teacher teacher, @PathVariable("departmentId") int departmentId) {
        log.info("Creating teacher: " + teacher.getName());
        Department department = university.findDepartment(departmentId);
        if (department != null) {
            department.createTeacher(teacher);
            log.info("Teacher created with id: " + teacher.getId());
        } else {
            log.info("Department with id " + department + " not found");
        }
    }

}
