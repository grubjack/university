package com.grubjack.university.servlet;

import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.LessonDao;
import com.grubjack.university.domain.DayOfWeek;
import com.grubjack.university.domain.Lesson;
import com.grubjack.university.domain.TimeOfDay;
import com.grubjack.university.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by grubjack on 09.11.2016.
 */
@WebServlet("/timetable")
public class TimetableServlet extends HttpServlet {
    private static final int GROUP_ID = 1031;

    private static Logger log = LoggerFactory.getLogger(TimetableServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LessonDao lessonDao = DaoFactory.getInstance().getLessonDao();
        Map<String, Map<String, Lesson>> timetable = new HashMap<>();

        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            try {
                List<Lesson> dayLessons = lessonDao.findGroupLessons(GROUP_ID, dayOfWeek);

                Map<String, Lesson> timeLessons = new HashMap<>();
                for (TimeOfDay timeOfDay : TimeOfDay.values()) {
                    Lesson timeLesson = null;
                    for (Lesson lesson : dayLessons) {
                        if (lesson.getTimeOfDay().equals(timeOfDay)) {
                            timeLesson = lesson;
                            break;
                        }
                    }
                    timeLessons.put(timeOfDay.toString(), timeLesson);
                }
                timetable.put(dayOfWeek.toString(), timeLessons);

            } catch (DaoException e) {
                log.error("Can't find lessons", e);
            }
        }

        req.setAttribute("days", DayOfWeek.names());
        req.setAttribute("times", TimeOfDay.names());
        req.setAttribute("timetable", timetable);
        req.getRequestDispatcher("timetable.jsp").forward(req, resp);

    }
}
