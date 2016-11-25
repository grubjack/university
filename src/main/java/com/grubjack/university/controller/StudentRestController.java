package com.grubjack.university.controller;

import com.grubjack.university.domain.Group;
import com.grubjack.university.domain.Student;
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
@RequestMapping(value = "/rest/students", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentRestController {

    @Autowired
    private University university;

    private static Logger log = LoggerFactory.getLogger(StudentRestController.class);

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Student> getAll() {
        log.info("Getting all students");
        return university.getStudents();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Student get(@PathVariable("id") int id) {
        log.info("Getting student with id: " + id);
        return university.findStudent(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") int id) {
        Student student = university.findStudent(id);
        if (student != null && student.getGroup() != null) {
            log.info("Deleting student with id " + id);
            student.getGroup().deleteStudent(student);
            log.info("Student deleted with id " + id);
        } else {
            log.info("Student with id " + id + " not found");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void update(@RequestBody Student student, @PathVariable("id") int id) {
        log.info("Updating student with id: " + id);
        Student oldStudent = university.findStudent(id);
        if (oldStudent != null && oldStudent.getGroup() != null) {
            student.setId(id);
            oldStudent.getGroup().updateStudent(student);
            log.info("Student updated successfully with id: " + id);
        } else {
            log.info("Student with id " + id + " not found");
        }
    }

    @RequestMapping(value = "/{groupId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void create(@RequestBody Student student, @PathVariable("groupId") int groupId) {
        log.info("Creating student: " + student.getName());
        Group group = university.findGroup(groupId);
        if (group != null) {
            group.createStudent(student);
            log.info("Student created with id: " + student.getId());
        } else {
            log.info("Group with id " + groupId + " not found");
        }
    }

}
