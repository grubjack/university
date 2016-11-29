package com.grubjack.university.controller;

import com.grubjack.university.model.Lesson;
import com.grubjack.university.service.LessonService;
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
@RequestMapping(value = "/rest/lessons", produces = MediaType.APPLICATION_JSON_VALUE)
public class LessonRestController {

    @Autowired
    private LessonService lessonService;

    private static Logger log = LoggerFactory.getLogger(LessonRestController.class);

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Lesson> getAll() {
        log.info("Getting all lessons");
        return lessonService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Lesson get(@PathVariable("id") int id) {
        log.info("Getting lesson with id: " + id);
        return lessonService.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") int id) {
        log.info("Deleting lesson with id " + id);
        lessonService.delete(id);
        log.info("Lesson deleted with id " + id);
    }

    @RequestMapping(value = "/{id}/{teacherId}/{classroomId}/{groupId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void update(@RequestBody Lesson lesson,
                       @PathVariable("id") int id,
                       @PathVariable("teacherId") int teacherId,
                       @PathVariable("classroomId") int classroomId,
                       @PathVariable("groupId") int groupId) {
        log.info("Updating lesson with id: " + id);
        Lesson oldLesson = lessonService.findById(id);
        if (oldLesson != null) {
            lesson.setId(id);
            lessonService.update(lesson, teacherId, classroomId, groupId);
            log.info("Lesson updated with id " + id);
        } else {
            log.info("Lesson with id " + id + " not found");
        }

    }

    @RequestMapping(value = "/{teacherId}/{classroomId}/{groupId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void create(@RequestBody Lesson lesson,
                       @PathVariable("teacherId") int teacherId,
                       @PathVariable("classroomId") int classroomId,
                       @PathVariable("groupId") int groupId) {
        log.info("Creating lesson: " + lesson.getSubject());
        lessonService.create(lesson, teacherId, classroomId, groupId);
        log.info("Lesson created with id: " + lesson.getId());
    }
}