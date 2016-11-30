package com.grubjack.university.controller.rest;

import com.grubjack.university.model.Group;
import com.grubjack.university.model.Student;
import com.grubjack.university.service.FacultyService;
import com.grubjack.university.service.GroupService;
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
    private FacultyService facultyService;

    @Autowired
    private GroupService groupService;


    private static Logger log = LoggerFactory.getLogger(GroupRestController.class);

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Group> getAll() {
        log.info("Getting all groups");
        return groupService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Group get(@PathVariable("id") int id) {
        log.info("Getting group with id: " + id);
        return groupService.findById(id);
    }

    @RequestMapping(value = "/{id}/students", method = RequestMethod.GET)
    @ResponseBody
    public List<Student> getStudents(@PathVariable("id") int id) {
        Group group = groupService.findById(id);
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
        log.info("Deleting group with id " + id);
        groupService.delete(id);
        log.info("Group deleted with id " + id);
    }

    @RequestMapping(value = "/{id}/{facultyId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void update(@RequestBody Group group,
                       @PathVariable("id") int id,
                       @PathVariable("facultyId") int facultyId) {
        log.info("Updating group with id: " + id);
        Group oldGroup = groupService.findById(id);
        if (oldGroup != null) {
            group.setId(id);
            facultyService.update(group, facultyId);
            log.info("Group updated successfully with id: " + id);
        } else {
            log.info("Group with id " + id + " not found");
        }
    }

    @RequestMapping(value = "/{facultyId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void create(@RequestBody Group group, @PathVariable("facultyId") int facultyId) {
        log.info("Creating group: " + group.getName());
        facultyService.create(group, facultyId);
        log.info("Group created with id: " + group.getId());
    }
}
