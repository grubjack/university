package com.grubjack.university.controller;

import com.grubjack.university.model.*;
import com.grubjack.university.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grubjack on 29.11.2016.
 */

@Controller
@RequestMapping(value = "/lesson")
public class LessonController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private ClassroomService classroomService;


    @RequestMapping(value = "/list")
    public ModelAndView list(@RequestParam(value = "gid", required = false) Integer groupId,
                             @RequestParam(value = "sid", required = false) Integer studentId,
                             @RequestParam(value = "tid", required = false) Integer teacherId,
                             @RequestParam(value = "fid", required = false) Integer facultyId) {
        ModelAndView modelAndView = new ModelAndView("lessons");
        String title = "Timetable";
        String home = null;
        String homeTitle = null;
        List<Timetable> timetables = new ArrayList<>();
        if (groupId != null) {

            Group group = groupService.findById(groupId);
            if (group != null) {
                home = "group/list";
                homeTitle = "Groups";
                title = String.format("Timetable for group %s", group.getName());
                timetables.add(lessonService.findGroupTimetable(groupId));
                modelAndView.addObject("groupId", groupId);
            }

        } else if (studentId != null) {

            Student student = studentService.findById(studentId);
            if (student != null) {
                home = "student/list";
                homeTitle = "Students";
                title = String.format("Timetable for student %s", student.getName());
                timetables.add(lessonService.findStudentTimetable(studentId));
                modelAndView.addObject("studentId", studentId);
            }

        } else if (teacherId != null) {

            Teacher teacher = teacherService.findById(teacherId);
            if (teacher != null) {
                home = "teacher/list";
                homeTitle = "Teachers";
                title = String.format("Timetable for teacher %s", teacher.getName());
                timetables.add(lessonService.findTeacherTimetable(teacherId));
                modelAndView.addObject("teacherId", teacherId);
            }

        } else if (facultyId != null) {

            Faculty faculty = facultyService.findById(facultyId);
            if (faculty != null) {
                home = "faculty/list";
                homeTitle = "Faculties";
                title = String.format("Timetable for faculty %s", faculty.getName());
                timetables = lessonService.findGroupTimetables(facultyId);
                modelAndView.addObject("facultyId", facultyId);
            }

        } else {
            timetables = lessonService.findGroupTimetables();
        }
        modelAndView.addObject("days", DayOfWeek.names());
        modelAndView.addObject("times", TimeOfDay.names());
        modelAndView.addObject("timetables", timetables);
        modelAndView.addObject("home", home);
        modelAndView.addObject("homeTitle", homeTitle);
        modelAndView.addObject("title", title);
        return modelAndView;
    }

    @RequestMapping(value = "/delete")
    public ModelAndView delete(@RequestParam(value = "id") Integer id,
                               @RequestParam(value = "gid", required = false) Integer groupId,
                               @RequestParam(value = "sid", required = false) Integer studentId,
                               @RequestParam(value = "tid", required = false) Integer teacherId,
                               @RequestParam(value = "fid", required = false) Integer facultyId) {
        lessonService.delete(id);
        return list(groupId, studentId, teacherId, facultyId);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView editPage(@RequestParam(value = "id") Integer id,
                                 @RequestParam(value = "gid", required = false) Integer groupId,
                                 @RequestParam(value = "sid", required = false) Integer studentId,
                                 @RequestParam(value = "tid", required = false) Integer teacherId,
                                 @RequestParam(value = "fid", required = false) Integer facultyId) {

        ModelAndView modelAndView = new ModelAndView("lesson");

        modelAndView.addObject("groupId", groupId);
        modelAndView.addObject("studentId", studentId);
        modelAndView.addObject("teacherId", teacherId);
        modelAndView.addObject("facultyId", facultyId);

        Lesson lesson = lessonService.findById(id);
        if (lesson != null) {
            if (teacherId != null) {
                modelAndView.addObject("groups", groupService.findAvailable(lesson.getDayOfWeek(), lesson.getTimeOfDay()));
            } else {
                modelAndView.addObject("teachers", teacherService.findAvailable(lesson.getDayOfWeek(), lesson.getTimeOfDay()));
            }
            modelAndView.addObject("rooms", classroomService.findAvailable(lesson.getDayOfWeek(), lesson.getTimeOfDay()));
            modelAndView.addObject("lesson", lesson);
            modelAndView.addObject("title", "Edit lesson");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView editing(@ModelAttribute("lesson") Lesson lesson,
                                @RequestParam(value = "gid", required = false) Integer groupId,
                                @RequestParam(value = "sid", required = false) Integer studentId,
                                @RequestParam(value = "tid", required = false) Integer teacherId,
                                @RequestParam(value = "fid", required = false) Integer facultyId) {
        lessonService.update(lesson);
        return list(groupId, studentId, teacherId, facultyId);
    }


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addPage(@RequestParam(value = "gid", required = false) Integer groupId,
                                @RequestParam(value = "sid", required = false) Integer studentId,
                                @RequestParam(value = "tid", required = false) Integer teacherId,
                                @RequestParam(value = "fid", required = false) Integer facultyId,
                                @RequestParam(value = "groupname", required = false) String groupname,
                                @RequestParam(value = "day") DayOfWeek day,
                                @RequestParam(value = "time") TimeOfDay time) {

        ModelAndView modelAndView = new ModelAndView("lesson");

        modelAndView.addObject("groupId", groupId);
        modelAndView.addObject("studentId", studentId);
        modelAndView.addObject("teacherId", teacherId);
        modelAndView.addObject("facultyId", facultyId);

        Lesson lesson = new Lesson();
        if (day != null && time != null) {
            lesson.setDayOfWeek(day);
            lesson.setTimeOfDay(time);
        }

        if (groupname != null && !groupname.isEmpty()) {
            Group group = groupService.find(groupname);
            if (group != null) {
                lesson.setGroup(group);
            }
        }

        if (teacherId != null) {
            Teacher teacher = teacherService.findById(teacherId);
            if (teacher != null) {
                lesson.setTeacher(teacher);
            }

            List<Group> groups = groupService.findAvailable(day, time);
            if (groups.size() == 0) {
                modelAndView.addObject("groupNotification", "no free groups");
            } else {
                modelAndView.addObject("groups", groups);
            }

        } else {

            List<Teacher> teachers = teacherService.findAvailable(day, time);
            if (teachers.size() == 0) {
                modelAndView.addObject("teacherNotification", "no free teachers");
            } else {
                modelAndView.addObject("teachers", teachers);
            }
        }

        if (groupId != null) {
            Group group = groupService.findById(groupId);
            if (group != null) {
                lesson.setGroup(group);
            }
        }

        if (studentId != null) {
            Student student = studentService.findById(studentId);
            if (student != null && student.getGroup() != null) {
                lesson.setGroup(student.getGroup());
            }
        }

        List<Classroom> rooms = classroomService.findAvailable(day, time);
        if (rooms.size() == 0) {
            modelAndView.addObject("roomNotification", "no free rooms");
        } else {
            modelAndView.addObject("rooms", rooms);
        }

        modelAndView.addObject("lesson", lesson);
        modelAndView.addObject("title", "Create lesson");
        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView adding(@ModelAttribute("lesson") Lesson lesson,
                               @RequestParam(value = "gid", required = false) Integer groupId,
                               @RequestParam(value = "sid", required = false) Integer studentId,
                               @RequestParam(value = "tid", required = false) Integer teacherId,
                               @RequestParam(value = "fid", required = false) Integer facultyId) {
        lessonService.create(lesson);
        return list(groupId, studentId, teacherId, facultyId);
    }

}
