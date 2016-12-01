package com.grubjack.university.controller;

import com.grubjack.university.model.Faculty;
import com.grubjack.university.model.Group;
import com.grubjack.university.service.FacultyService;
import com.grubjack.university.service.GroupService;
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
@RequestMapping(value = "/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private FacultyService facultyService;


    @RequestMapping(value = "/list")
    public ModelAndView list(@RequestParam(value = "fid", required = false) Integer facultyId) {
        ModelAndView modelAndView = new ModelAndView("groups");
        String title = "Groups";
        if (facultyId == null) {
            modelAndView.addObject("groups", groupService.findAll());
        } else {
            Faculty faculty = facultyService.findById(facultyId);
            if (faculty != null) {
                title = String.format("Groups of %s faculty", faculty.getName());
                modelAndView.addObject("facultyId", facultyId);
                modelAndView.addObject("groups", facultyService.findGroups(facultyId));
            }
        }
        modelAndView.addObject("title", title);
        return modelAndView;
    }

    @RequestMapping(value = "/delete")
    public ModelAndView delete(@RequestParam(value = "id") Integer id,
                               @RequestParam(value = "fid") Integer facultyId) {
        groupService.delete(id);
        return list(facultyId);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam(value = "id") Integer id,
                                 @RequestParam(value = "fid") Integer facultyId) {
        ModelAndView modelAndView = new ModelAndView("group");
        modelAndView.addObject("group", groupService.findById(id));
        modelAndView.addObject("facultyId", facultyId);
        modelAndView.addObject("title", "Edit group");
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView edit(@ModelAttribute("group") Group group,
                                @RequestParam(value = "fid") Integer facultyId) {
        facultyService.update(group, facultyId);
        return list(facultyId);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView create(@RequestParam(value = "fid") Integer facultyId) {
        ModelAndView modelAndView = new ModelAndView("group");
        modelAndView.addObject("group", new Group());
        modelAndView.addObject("facultyId", facultyId);
        modelAndView.addObject("title", "Create group");
        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView create(@ModelAttribute("group") Group group,
                               @RequestParam(value = "fid") Integer facultyId) {
        facultyService.create(group, facultyId);
        return list(facultyId);
    }

    @RequestMapping(value = "/search")
    public ModelAndView search(@RequestParam(value = "name") String name,
                               @RequestParam(value = "fid", required = false) Integer facultyId) {
        ModelAndView modelAndView = new ModelAndView("groups");
        String title = "Groups";
        if (facultyId == null) {
            modelAndView.addObject("groups", groupService.findByName(name));
        } else {
            Faculty faculty = facultyService.findById(facultyId);
            if (faculty != null) {
                title = String.format("Groups of %s faculty", faculty.getName());
                modelAndView.addObject("facultyId", facultyId);
                modelAndView.addObject("groups", facultyService.findGroupsByName(name, facultyId));
            }
        }
        modelAndView.addObject("title", title);
        return modelAndView;
    }

}