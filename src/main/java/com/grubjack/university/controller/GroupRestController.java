package com.grubjack.university.controller;

import com.grubjack.university.domain.Faculty;
import com.grubjack.university.domain.Group;
import com.grubjack.university.domain.Student;
import com.grubjack.university.service.University;
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
@RequestMapping(value = "/rest/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupRestController {

    @Autowired
    private University university;

    private static Logger log = LoggerFactory.getLogger(GroupRestController.class);

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Group> getAll() {
        log.info("Getting all groups");
        return university.getGroups();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Group get(@PathVariable("id") int id) {
        log.info("Getting group with id: " + id);
        return university.findGroup(id);
    }

    @RequestMapping(value = "/{id}/students", method = RequestMethod.GET)
    @ResponseBody
    public List<Student> getStudents(@PathVariable("id") int id) {
        Group group = university.findGroup(id);
        if (group != null) {
            log.info("Getting students from group with id: " + id);
            return group.getStudents();
        } else {
            log.info("Group with id " + id + " not found");
        }
        return Collections.emptyList();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") int id) {
        Group group = university.findGroup(id);
        if (group != null && group.getFaculty() != null) {
            log.info("Deleting group with id " + id);
            group.getFaculty().deleteGroup(group);
            log.info("Group deleted with id " + id);
        } else {
            log.info("Group with id " + id + " not found");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void update(@RequestBody Group group, @PathVariable("id") int id) {
        log.info("Updating group with id: " + id);
        Group oldGroup = university.findGroup(id);
        if (oldGroup != null && oldGroup.getFaculty() != null) {
            group.setId(id);
            oldGroup.getFaculty().updateGroup(group);
            log.info("Group updated successfully with id: " + id);
        } else {
            log.info("Group with id " + id + " not found");
        }
    }

    @RequestMapping(value = "/{facultyId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void create(@RequestBody Group group, @PathVariable("facultyId") int facultyId) {
        log.info("Creating group: " + group.getName());
        Faculty faculty = university.findFaculty(facultyId);
        if (faculty != null) {
            faculty.createGroup(group);
            log.info("Group created with id: " + group.getId());
        } else {
            log.info("Faculty with id " + facultyId + " not found");
        }
    }

}
