package com.grubjack.university.controller;

import com.grubjack.university.model.Department;
import com.grubjack.university.model.Teacher;
import com.grubjack.university.service.DepartmentService;
import com.grubjack.university.service.TeacherService;
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
@RequestMapping(value = "/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private DepartmentService departmentService;


    @RequestMapping(value = "/list")
    public ModelAndView list(@RequestParam(value = "did", required = false) Integer departmentId) {
        ModelAndView modelAndView = new ModelAndView("teachers");
        String title = "Teachers";
        if (departmentId == null) {
            modelAndView.addObject("teachers", teacherService.findAll());
        } else {
            Department department = departmentService.findById(departmentId);
            if (department != null) {
                title = String.format("Teachers of %s department", department.getName());
                modelAndView.addObject("departmentId", departmentId);
                modelAndView.addObject("teachers", departmentService.findTeachers(departmentId));
            }
        }
        modelAndView.addObject("title", title);
        return modelAndView;
    }

    @RequestMapping(value = "/delete")
    public ModelAndView delete(@RequestParam(value = "id") Integer id,
                               @RequestParam(value = "did") Integer departmentId) {
        teacherService.delete(id);
        return list(departmentId);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam(value = "id") Integer id,
                                 @RequestParam(value = "did") Integer departmentId) {
        ModelAndView modelAndView = new ModelAndView("teacher");
        Teacher teacher = teacherService.findById(id);
        System.out.println("name=" + teacher.getName());
        modelAndView.addObject("teacher", teacherService.findById(id));
        modelAndView.addObject("departmentId", departmentId);
        modelAndView.addObject("title", "Edit teacher");
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView edit(@ModelAttribute("teacher") Teacher teacher,
                                @RequestParam(value = "did") Integer departmentId) {
        System.out.println("name=" + teacher.getName());
        departmentService.update(teacher, departmentId);
        return list(departmentId);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView create(@RequestParam(value = "did") Integer departmentId) {
        ModelAndView modelAndView = new ModelAndView("teacher");
        modelAndView.addObject("teacher", new Teacher());
        modelAndView.addObject("departmentId", departmentId);
        modelAndView.addObject("title", "Create teacher");
        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView create(@ModelAttribute("teacher") Teacher teacher,
                               @RequestParam(value = "did") Integer departmentId) {
        departmentService.create(teacher, departmentId);
        return list(departmentId);
    }

    @RequestMapping(value = "/search")
    public ModelAndView search(@RequestParam(value = "name") String name,
                               @RequestParam(value = "did", required = false) Integer departmentId) {
        ModelAndView modelAndView = new ModelAndView("teachers");
        String title = "Teachers";
        if (departmentId == null) {
            modelAndView.addObject("teachers", teacherService.findByName(name));
        } else {
            Department department = departmentService.findById(departmentId);
            if (department != null) {
                title = String.format("Teachers of %s department", department.getName());
                modelAndView.addObject("departmentId", departmentId);
                modelAndView.addObject("teachers", departmentService.findTeachersByName(name, departmentId));
            }
        }
        modelAndView.addObject("title", title);
        return modelAndView;
    }

}
