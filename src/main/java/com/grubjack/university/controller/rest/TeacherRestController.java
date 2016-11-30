package com.grubjack.university.controller.rest;

import com.grubjack.university.model.Teacher;
import com.grubjack.university.service.DepartmentService;
import com.grubjack.university.service.TeacherService;
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
    private DepartmentService departmentService;

    @Autowired
    private TeacherService teacherService;

    private static Logger log = LoggerFactory.getLogger(TeacherRestController.class);

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Teacher> getAll() {
        log.info("Getting all teachers");
        return teacherService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Teacher get(@PathVariable("id") int id) {
        log.info("Getting teacher with id: " + id);
        return teacherService.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") int id) {
        log.info("Deleting teacher with id " + id);
        teacherService.delete(id);
        log.info("Teacher deleted with id " + id);
    }

    @RequestMapping(value = "/{id}/{departmentId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void update(@RequestBody Teacher teacher,
                       @PathVariable("id") int id,
                       @PathVariable("departmentId") int departmentId) {
        log.info("Updating teacher with id: " + id);
        Teacher oldTeacher = teacherService.findById(id);
        if (oldTeacher != null) {
            teacher.setId(id);
            departmentService.update(teacher, departmentId);
            log.info("Teacher updated successfully with id: " + id);
        } else {
            log.info("Teacher with id " + id + " not found");
        }
    }

    @RequestMapping(value = "/{departmentId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void create(@RequestBody Teacher teacher, @PathVariable("departmentId") int departmentId) {
        log.info("Creating teacher: " + teacher.getName());
        departmentService.create(teacher, departmentId);
        log.info("Teacher created with id: " + teacher.getId());
    }

}
