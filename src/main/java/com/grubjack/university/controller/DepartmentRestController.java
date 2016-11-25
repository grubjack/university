package com.grubjack.university.controller;

import com.grubjack.university.domain.Department;
import com.grubjack.university.domain.Faculty;
import com.grubjack.university.domain.Teacher;
import com.grubjack.university.domain.University;
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
    private University university;

    private static Logger log = LoggerFactory.getLogger(DepartmentRestController.class);

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Department> getAll() {
        log.info("Getting all departments");
        return university.getDepartments();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Department get(@PathVariable("id") int id) {
        log.info("Getting department with id: " + id);
        return university.findDepartment(id);
    }

    @RequestMapping(value = "/{id}/teachers", method = RequestMethod.GET)
    @ResponseBody
    public List<Teacher> getTeachers(@PathVariable("id") int id) {
        Department department = university.findDepartment(id);
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
        Department department = university.findDepartment(id);
        if (department != null && department.getFaculty() != null) {
            department.getFaculty().deleteDepartment(department);
            log.info("Department deleted with id " + id);
        } else {
            log.info("Department with id " + id + " not found");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void update(@RequestBody Department department, @PathVariable("id") int id) {
        log.info("Updating department with id: " + id);
        Department oldDepartment = university.findDepartment(id);
        if (oldDepartment != null && oldDepartment.getFaculty() != null) {
            department.setId(id);
            oldDepartment.getFaculty().updateDepartment(department);
            log.info("Department updated successfully with id: " + id);
        } else {
            log.info("Department with id " + id + " not found");
        }
    }

    @RequestMapping(value = "/{facultyId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void create(@RequestBody Department department, @PathVariable("facultyId") int facultyId) {
        log.info("Creating department: " + department.getName());
        Faculty faculty = university.findFaculty(facultyId);
        if (faculty != null) {
            faculty.createDepartment(department);
            log.info("Department created with id: " + department.getId());
        } else {
            log.info("Faculty with id " + facultyId + " not found");
        }
    }

}
