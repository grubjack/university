package com.grubjack.university.controller;

import com.grubjack.university.model.Department;
import com.grubjack.university.model.Faculty;
import com.grubjack.university.service.DepartmentService;
import com.grubjack.university.service.FacultyService;
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
@RequestMapping(value = "/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private FacultyService facultyService;


    @RequestMapping(value = "/list")
    public ModelAndView list(@RequestParam(value = "fid", required = false) Integer facultyId) {
        ModelAndView modelAndView = new ModelAndView("departments");
        String title = "Departments";
        if (facultyId == null) {
            modelAndView.addObject("departments", departmentService.findAll());
        } else {
            Faculty faculty = facultyService.findById(facultyId);
            if (faculty != null) {
                title = String.format("Groups of %s faculty", faculty.getName());
                modelAndView.addObject("facultyId", facultyId);
                modelAndView.addObject("departments", facultyService.findDepartments(facultyId));
            }
        }
        modelAndView.addObject("title", title);
        return modelAndView;
    }

    @RequestMapping(value = "/delete")
    public ModelAndView delete(@RequestParam(value = "id") Integer id,
                               @RequestParam(value = "fid") Integer facultyId) {
        departmentService.delete(id);
        return list(facultyId);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam(value = "id") Integer id,
                                 @RequestParam(value = "fid") Integer facultyId) {
        ModelAndView modelAndView = new ModelAndView("department");
        modelAndView.addObject("department", departmentService.findById(id));
        modelAndView.addObject("facultyId", facultyId);
        modelAndView.addObject("title", "Edit department");
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView edit(@ModelAttribute("department") Department department,
                                @RequestParam(value = "fid") Integer facultyId) {
        facultyService.update(department, facultyId);
        return list(facultyId);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView create(@RequestParam(value = "fid") Integer facultyId) {
        ModelAndView modelAndView = new ModelAndView("department");
        modelAndView.addObject("department", new Department());
        modelAndView.addObject("facultyId", facultyId);
        modelAndView.addObject("title", "Create department");
        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView create(@ModelAttribute("department") Department department,
                               @RequestParam(value = "fid") Integer facultyId) {
        facultyService.create(department, facultyId);
        return list(facultyId);
    }

    @RequestMapping(value = "/search")
    public ModelAndView search(@RequestParam(value = "name") String name,
                               @RequestParam(value = "fid", required = false) Integer facultyId) {
        ModelAndView modelAndView = new ModelAndView("departments");
        String title = "Departments";
        if (facultyId == null) {
            modelAndView.addObject("departments", departmentService.findByName(name));
        } else {
            Faculty faculty = facultyService.findById(facultyId);
            if (faculty != null) {
                title = String.format("Departments of %s faculty", faculty.getName());
                modelAndView.addObject("facultyId", facultyId);
                modelAndView.addObject("departments", facultyService.findDepartmentsByName(name, facultyId));
            }
        }
        modelAndView.addObject("title", title);
        return modelAndView;
    }

}
