package com.grubjack.university.servlet;

import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.GroupDao;
import com.grubjack.university.dao.LessonDao;
import com.grubjack.university.domain.*;
import com.grubjack.university.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by grubjack on 09.11.2016.
 */
@WebServlet("/timetable")
public class TimetableServlet extends HttpServlet {
    private static Logger log = LoggerFactory.getLogger(TimetableServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LessonDao lessonDao = DaoFactory.getInstance().getLessonDao();
        GroupDao groupDao = DaoFactory.getInstance().getGroupDao();
        List<Timetable> timetables = new ArrayList<>();

        List<Group> groups = new ArrayList<>();
        List<Lesson> lessons = new ArrayList();
        try {
            groups = groupDao.findAll();
            lessons = lessonDao.findAll();
        } catch (DaoException e) {
            log.error("Can't find groups or lessons", e);
        }

        for (Group group : groups) {
            Timetable groupTimetable = new Timetable(group.getName());
            timetables.add(groupTimetable);
            for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
                TimetableUnit groupDayUnit = new TimetableUnit(dayOfWeek);
                for (TimeOfDay timeOfDay : TimeOfDay.values()) {
                    Iterator<Lesson> iterator = lessons.iterator();
                    while (iterator.hasNext()) {
                        Lesson lesson = iterator.next();
                        if (lesson.getGroup().getId() == group.getId() && lesson.getDayOfWeek().equals(dayOfWeek) && lesson.getTimeOfDay().equals(timeOfDay)) {
                            groupDayUnit.getLessons().add(lesson);
                            iterator.remove();
                            break;
                        }
                    }

                }
                groupTimetable.getUnits().add(groupDayUnit);
            }
        }
        req.setAttribute("days", DayOfWeek.names());
        req.setAttribute("times", TimeOfDay.names());
        req.setAttribute("timetables", timetables);
        req.getRequestDispatcher("timetable.jsp").forward(req, resp);
    }
}
