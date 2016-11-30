package com.grubjack.university.controller;

import com.grubjack.university.model.Classroom;
import com.grubjack.university.service.ClassroomService;
import com.grubjack.university.service.UniversityService;
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
@RequestMapping(value = "/classroom")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private UniversityService universityService;

    @RequestMapping(value = "/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("classrooms");
        modelAndView.addObject("title", "Classrooms");
        modelAndView.addObject("classrooms", classroomService.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/delete")
    public ModelAndView delete(@RequestParam(value = "id") Integer id) {
        classroomService.delete(id);
        return list();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam(value = "id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("classroom");
        modelAndView.addObject("classroom", classroomService.findById(id));
        modelAndView.addObject("title", "Edit classroom");
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView edit(@ModelAttribute("classroom") Classroom classroom) {
        universityService.update(classroom);
        return list();
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("classroom");
        modelAndView.addObject("classroom", new Classroom());
        modelAndView.addObject("title", "Create classroom");
        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView create(@ModelAttribute("classroom") Classroom classroom) {
        universityService.create(classroom);
        return list();
    }

}
