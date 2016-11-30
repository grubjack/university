package com.grubjack.university.controller;

import com.grubjack.university.model.Group;
import com.grubjack.university.model.Student;
import com.grubjack.university.service.GroupService;
import com.grubjack.university.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by grubjack on 29.11.2016.
 */

@Controller
@RequestMapping(value = "/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private GroupService groupService;


    @RequestMapping(value = "/list")
    public ModelAndView list(@RequestParam(value = "gid", required = false) Integer groupId) {
        ModelAndView modelAndView = new ModelAndView("students");
        String title = "Students";
        if (groupId == null) {
            modelAndView.addObject("students", studentService.findAll());
        } else {
            Group group = groupService.findById(groupId);
            if (group != null) {
                title = String.format("Students of %s group", group.getName());
                modelAndView.addObject("groupId", groupId);
                modelAndView.addObject("students", groupService.findStudents(groupId));
            }
        }
        modelAndView.addObject("title", title);
        return modelAndView;
    }

    @RequestMapping(value = "/delete")
    public ModelAndView delete(@RequestParam(value = "id") Integer id,
                               @RequestParam(value = "gid") Integer groupId) {
        studentService.delete(id);
        return list(groupId);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView editPage(@RequestParam(value = "id") Integer id,
                                 @RequestParam(value = "gid") Integer groupId) {
        ModelAndView modelAndView = new ModelAndView("student");
        modelAndView.addObject("student", studentService.findById(id));
        modelAndView.addObject("groupId", groupId);
        modelAndView.addObject("title", "Edit student");
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView editing(@ModelAttribute("student") Student student,
                                @RequestParam(value = "gid") Integer groupId) {
        groupService.update(student, groupId);
        return list(groupId);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addPage(@RequestParam(value = "gid") Integer groupId) {
        ModelAndView modelAndView = new ModelAndView("student");
        modelAndView.addObject("student", new Student());
        modelAndView.addObject("groupId", groupId);
        modelAndView.addObject("title", "Create student");
        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView adding(@ModelAttribute("student") Student student,
                               @RequestParam(value = "gid") Integer groupId) {
        groupService.create(student, groupId);
        return list(groupId);
    }

    @RequestMapping(value = "/search")
    public ModelAndView search(@RequestParam(value = "name") String name,
                               @RequestParam(value = "gid", required = false) Integer groupId) {
        ModelAndView modelAndView = new ModelAndView("students");
        String title = "Students";
        if (groupId == null) {
            modelAndView.addObject("students", studentService.findByName(name));
        } else {
            Group group = groupService.findById(groupId);
            if (group != null) {
                title = String.format("Students of %s group", group.getName());
                modelAndView.addObject("groupId", groupId);
                modelAndView.addObject("students", groupService.findStudentsByName(name, groupId));
            }
        }
        modelAndView.addObject("title", title);
        return modelAndView;
    }

}
