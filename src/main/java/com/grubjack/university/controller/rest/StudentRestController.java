package com.grubjack.university.controller.rest;

import com.grubjack.university.model.Student;
import com.grubjack.university.service.GroupService;
import com.grubjack.university.service.StudentService;
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
    private GroupService groupService;

    @Autowired
    private StudentService studentService;


    private static Logger log = LoggerFactory.getLogger(StudentRestController.class);

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Student> getAll() {
        log.info("Getting all students");
        return studentService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Student get(@PathVariable("id") int id) {
        log.info("Getting student with id: " + id);
        return studentService.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") int id) {
        log.info("Deleting student with id " + id);
        studentService.delete(id);
        log.info("Student deleted with id " + id);
    }

    @RequestMapping(value = "/{id}/{groupId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void update(@RequestBody Student student,
                       @PathVariable("id") int id,
                       @PathVariable("groupId") int groupId) {
        log.info("Updating student with id: " + id);
        Student oldStudent = studentService.findById(id);
        if (oldStudent != null) {
            student.setId(id);
            groupService.update(student, groupId);
            log.info("Student updated successfully with id: " + id);
        } else {
            log.info("Student with id " + id + " not found");
        }
    }

    @RequestMapping(value = "/{groupId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void create(@RequestBody Student student, @PathVariable("groupId") int groupId) {
        log.info("Creating student: " + student.getName());
        groupService.create(student, groupId);
        log.info("Student created with id: " + student.getId());
    }

}
