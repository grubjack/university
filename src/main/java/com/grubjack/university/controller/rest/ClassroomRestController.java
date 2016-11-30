package com.grubjack.university.controller.rest;

import com.grubjack.university.model.Classroom;
import com.grubjack.university.service.ClassroomService;
import com.grubjack.university.service.UniversityService;
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
@RequestMapping(value = "/rest/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClassroomRestController {

    @Autowired
    private UniversityService universityService;

    @Autowired
    private ClassroomService classroomService;


    private static Logger log = LoggerFactory.getLogger(ClassroomRestController.class);

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Classroom> getAll() {
        log.info("Getting all classrooms");
        return classroomService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Classroom get(@PathVariable("id") int id) {
        log.info("Getting classroom with id: " + id);
        return classroomService.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") int id) {
        log.info("Deleting classroom with id " + id);
        classroomService.delete(id);
        log.info("Classroom deleted with id " + id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void update(@RequestBody Classroom room, @PathVariable("id") int id) {
        log.info("Updating classroom with id: " + id);
        Classroom oldRoom = classroomService.findById(id);
        if (oldRoom != null) {
            room.setId(id);
            universityService.update(room);
            log.info("Classroom updated successfully with id: " + id);
        } else {
            log.info("Classroom with id " + id + " not found");
        }
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void create(@RequestBody Classroom room) {
        log.info("Creating classroom: " + room.getNumber());
        universityService.create(room);
        log.info("Classroom created with id: " + room.getId());
    }
}