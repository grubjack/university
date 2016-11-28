package com.grubjack.university.controller;

import com.grubjack.university.domain.Faculty;
import com.grubjack.university.domain.Lesson;
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
@RequestMapping(value = "/rest/lessons", produces = MediaType.APPLICATION_JSON_VALUE)
public class LessonRestController {

    @Autowired
    private University university;

    private static Logger log = LoggerFactory.getLogger(LessonRestController.class);

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Lesson> getAll() {
        log.info("Getting all lessons");
        return Lesson.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Lesson get(@PathVariable("id") int id) {
        log.info("Getting lesson with id: " + id);
        return Lesson.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") int id) {
        log.info("Deleting lesson with id " + id);
        Lesson lesson = Lesson.findById(id);
        if (lesson != null && lesson.getGroup() != null) {
            Faculty faculty = Faculty.findGroupFaculty(lesson.getGroup().getId());
            if (faculty != null) {
                faculty.getTimetable().deleteLesson(lesson);
                log.info("Lesson deleted with id " + id);
            }
        } else {
            log.info("Lesson with id " + id + " not found");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void update(@RequestBody Lesson lesson, @PathVariable("id") int id) {
        log.info("Updating lesson with id: " + id);
        Lesson oldLesson = Lesson.findById(id);
        if (oldLesson != null) {
            lesson.setId(id);
            Faculty faculty = Faculty.findGroupFaculty(lesson.getGroup().getId());
            if (faculty != null) {
                faculty.getTimetable().updateLesson(lesson);
                log.info("Lesson updated with id " + id);
            }
        } else {
            log.info("Lesson with id " + id + " not found");
        }
    }

    @RequestMapping(value = "/{facultyId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void create(@RequestBody Lesson lesson, @PathVariable("facultyId") int facultyId) {
        log.info("Creating lesson: " + lesson.getSubject());
        Faculty faculty = Faculty.findById(facultyId);
        if (faculty != null) {
            faculty.getTimetable().createLesson(lesson);
            log.info("Lesson created with id: " + lesson.getId());
        } else {
            log.info("Faculty with id " + facultyId + " not found");
        }
    }
}