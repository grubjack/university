package com.grubjack.university.controller.rest;

import com.grubjack.university.model.Department;
import com.grubjack.university.model.Teacher;
import com.grubjack.university.service.DepartmentService;
import com.grubjack.university.service.FacultyService;
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
@RequestMapping(value = "/rest/departments", produces = MediaType.APPLICATION_JSON_VALUE)
public class DepartmentRestController {

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private DepartmentService departmentService;

    private static Logger log = LoggerFactory.getLogger(DepartmentRestController.class);

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Department> getAll() {
        log.info("Getting all departments");
        return departmentService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Department get(@PathVariable("id") int id) {
        log.info("Getting department with id: " + id);
        return departmentService.findById(id);
    }

    @RequestMapping(value = "/{id}/teachers", method = RequestMethod.GET)
    @ResponseBody
    public List<Teacher> getTeachers(@PathVariable("id") int id) {
        Department department = departmentService.findById(id);
        if (department != null) {
            log.info("Getting teachers from department with id " + id);
            return department.getTeachers();
        } else {
            log.info("Department with id " + id + " not found");
        }
        return Collections.emptyList();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") int id) {
        log.info("Deleting department with id " + id);
        departmentService.delete(id);
        log.info("Department deleted with id " + id);
    }

    @RequestMapping(value = "/{id}/{facultyId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void update(@RequestBody Department department,
                       @PathVariable("id") int id,
                       @PathVariable("facultyId") int facultyId) {
        log.info("Updating department with id: " + id);
        Department oldDepartment = departmentService.findById(id);
        if (oldDepartment != null) {
            department.setId(id);
            facultyService.update(department, facultyId);
            log.info("Department updated successfully with id: " + id);
        } else {
            log.info("Department with id " + id + " not found");
        }
    }

    @RequestMapping(value = "/{facultyId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void create(@RequestBody Department department, @PathVariable("facultyId") int facultyId) {
        log.info("Creating department: " + department.getName());
        facultyService.create(department, facultyId);
        log.info("Department created with id: " + department.getId());
    }

}
