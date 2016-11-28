package com.grubjack.university.controller;

import com.grubjack.university.domain.Department;
import com.grubjack.university.domain.Faculty;
import com.grubjack.university.domain.Group;
import com.grubjack.university.domain.University;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Created by grubjack on 24.11.2016.
 */

@RestController
@RequestMapping(value = "/rest/faculties", produces = MediaType.APPLICATION_JSON_VALUE)
public class FacultyRestController {

    @Autowired
    private University university;

    private static Logger log = LoggerFactory.getLogger(FacultyRestController.class);

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Faculty> getAll() {
        log.info("Getting all faculties");
        return university.getFaculties();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Faculty get(@PathVariable("id") int id) {
        log.info("Getting faculty with id: " + id);
        return Faculty.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") int id) {
        log.info("Deleting faculty with id " + id);
        university.deleteFaculty(Faculty.findById(id));
        log.info("Faculty deleted with id " + id);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void update(@RequestBody Faculty faculty) {
        log.info("Updating faculty with id: " + faculty.getId());
        university.updateFaculty(faculty);
        log.info("faculty updated successfully with id: " + faculty.getId());
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void create(@RequestBody Faculty faculty) {
        log.info("Creating faculty: " + faculty.getName());
        university.createFaculty(faculty);
        log.info("faculty created with id: " + faculty.getId());
    }

    @RequestMapping(value = "/{id}/groups", method = RequestMethod.GET)
    @ResponseBody
    public List<Group> getGroups(@PathVariable("id") int id) {
        Faculty faculty = Faculty.findById(id);
        if (faculty != null) {
            log.info("Getting groups from faculty with id: " + id);
            return faculty.getGroups();
        } else {
            log.info("Faculty with id " + id + " not found");
        }
        return Collections.emptyList();
    }

    @RequestMapping(value = "/{id}/departments", method = RequestMethod.GET)
    @ResponseBody
    public List<Department> getDepartments(@PathVariable("id") int id) {
        Faculty faculty = Faculty.findById(id);
        if (faculty != null) {
            log.info("Getting departments from faculty with id: " + id);
            return faculty.getDepartments();
        } else {
            log.info("Faculty with id " + id + " not found");
        }
        return Collections.emptyList();
    }
}