package com.grubjack.university.service;

import com.grubjack.university.dao.ClassroomDao;
import com.grubjack.university.dao.FacultyDao;
import com.grubjack.university.model.Classroom;
import com.grubjack.university.model.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by grubjack on 29.11.2016.
 */
@Service
public class UniversityService {

    @Autowired
    private ClassroomDao classroomDao;

    @Autowired
    private FacultyDao facultyDao;

    public void create(Classroom classroom) {
        if (classroom != null && !classroomDao.findAll().contains(classroom)) {
            classroomDao.create(classroom);
        }
    }

    public void update(Classroom classroom) {
        if (classroom != null) {
            classroomDao.update(classroom);
        }
    }

    public void create(Faculty faculty) {
        if (faculty != null && !facultyDao.findAll().contains(faculty)) {
            facultyDao.create(faculty);
        }
    }

    public void update(Faculty faculty) {
        if (faculty != null) {
            facultyDao.update(faculty);
        }
    }
}
