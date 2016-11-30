package com.grubjack.university.controller;

import com.grubjack.university.model.Faculty;
import com.grubjack.university.service.FacultyService;
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
@RequestMapping(value = "/faculty")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private UniversityService universityService;

    @RequestMapping(value = "/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("faculties");
        modelAndView.addObject("title", "Faculties");
        modelAndView.addObject("faculties", facultyService.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/delete")
    public ModelAndView delete(@RequestParam(value = "id") Integer id) {
        facultyService.delete(id);
        return list();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView editPage(@RequestParam(value = "id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("faculty");
        modelAndView.addObject("faculty", facultyService.findById(id));
        modelAndView.addObject("title", "Edit faculty");
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView editing(@ModelAttribute("faculty") Faculty faculty) {
        universityService.update(faculty);
        return list();
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addPage() {
        ModelAndView modelAndView = new ModelAndView("faculty");
        modelAndView.addObject("faculty", new Faculty());
        modelAndView.addObject("title", "Create faculty");
        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView adding(@ModelAttribute("faculty") Faculty faculty) {
        universityService.create(faculty);
        return list();
    }

    @RequestMapping(value = "/search")
    public ModelAndView search(@RequestParam(value = "name") String name) {
        ModelAndView modelAndView = new ModelAndView("faculties");
        modelAndView.addObject("title", "Faculties");
        modelAndView.addObject("faculties", facultyService.findByName(name));
        return modelAndView;
    }

}
