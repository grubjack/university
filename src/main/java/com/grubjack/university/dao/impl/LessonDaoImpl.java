package com.grubjack.university.dao.impl;

import com.grubjack.university.dao.*;
import com.grubjack.university.domain.*;
import com.grubjack.university.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.grubjack.university.dao.DaoFactory.getConnection;

/**
 * Created by grubjack on 03.11.2016.
 */
public class LessonDaoImpl implements LessonDao {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private ClassroomDao classroomDao;
    private PersonDao<Teacher> teacherDao;
    private GroupDao groupDao;

    private static Logger log = LoggerFactory.getLogger(LessonDaoImpl.class);

    public LessonDaoImpl() {
        this.classroomDao = daoFactory.getClassroomDao();
        this.teacherDao = daoFactory.getTeacherDao();
        this.groupDao = daoFactory.getGroupDao();
    }

    @Override
    public void create(Lesson lesson) throws DaoException {
        log.info("Creating new lesson");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("INSERT INTO lessons (subject,week_day,day_time,room_id,teacher_id,group_id) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, lesson.getSubject());
            statement.setString(2, lesson.getDayOfWeek().toString());
            statement.setString(3, lesson.getTimeOfDay().toString());
            statement.setInt(4, lesson.getClassroom().getId());
            statement.setInt(5, lesson.getTeacher().getId());
            statement.setInt(6, lesson.getGroup().getId());
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                lesson.setId(resultSet.getInt(1));
                log.info("Lesson is created with id = " + lesson.getId());
            }
        } catch (SQLException e) {
            log.error("Can't create lesson", e);
            throw new DaoException("Can't create lesson", e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    log.error("Can't close result set", e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error("Can't close statement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("Can't close connection", e);
                }
            }
        }
    }

    @Override
    public void update(Lesson lesson) throws DaoException {
        log.info("Updating lesson with id " + lesson.getId());
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("UPDATE lessons SET subject=?,week_day=?,day_time=?,room_id=?,teacher_id=?,group_id=? WHERE id=?");
            statement.setString(1, lesson.getSubject());
            statement.setString(2, lesson.getDayOfWeek().toString());
            statement.setString(3, lesson.getTimeOfDay().toString());
            statement.setInt(4, lesson.getClassroom().getId());
            statement.setInt(5, lesson.getTeacher().getId());
            statement.setInt(6, lesson.getGroup().getId());
            statement.setInt(7, lesson.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Can't update lesson", e);
            throw new DaoException("Can't update lesson", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error("Can't close statement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("Can't close connection", e);
                }
            }
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        log.info("Deleting lesson with id " + id);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("DELETE FROM lessons WHERE id=?");
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.error("Can't delete lesson", e);
            throw new DaoException("Can't delete lesson", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error("Can't close statement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("Can't close connection", e);
                }
            }
        }
    }

    @Override
    public Lesson find(int id) throws DaoException {
        log.info("Finding lesson with id " + id);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Lesson lesson = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM lessons WHERE id=?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                lesson = new Lesson();
                lesson.setId(id);
                lesson.setSubject(resultSet.getString("subject"));
                lesson.setDayOfWeek(DayOfWeek.valueOf(resultSet.getString("week_day")));
                lesson.setTimeOfDay(TimeOfDay.convert(resultSet.getString("day_time")));
                Classroom classroom = classroomDao.find(resultSet.getInt("room_id"));
                lesson.setClassroom(classroom);
                Teacher teacher = teacherDao.find(resultSet.getInt("teacher_id"));
                lesson.setTeacher(teacher);
                Group group = groupDao.find(resultSet.getInt("group_id"));
                lesson.setGroup(group);
            }
        } catch (SQLException e) {
            log.error("Can't find lesson", e);
            throw new DaoException("Can't find lesson", e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    log.error("Can't close result set", e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error("Can't close statement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("Can't close connection", e);
                }
            }
        }
        return lesson;
    }

    @Override
    public List<Lesson> findAll() throws DaoException {
        log.info("Finding all lessons");
        List<Lesson> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM lessons");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Lesson lesson = new Lesson();
                lesson.setId(resultSet.getInt("id"));
                lesson.setSubject(resultSet.getString("subject"));
                lesson.setDayOfWeek(DayOfWeek.valueOf(resultSet.getString("week_day")));
                lesson.setTimeOfDay(TimeOfDay.convert(resultSet.getString("day_time")));
                Classroom classroom = classroomDao.find(resultSet.getInt("room_id"));
                lesson.setClassroom(classroom);
                Teacher teacher = teacherDao.find(resultSet.getInt("teacher_id"));
                lesson.setTeacher(teacher);
                Group group = groupDao.find(resultSet.getInt("group_id"));
                lesson.setGroup(group);
                result.add(lesson);
            }
        } catch (SQLException e) {
            log.error("Can't find lessons", e);
            throw new DaoException("Can't find lessons", e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    log.error("Can't close result set", e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error("Can't close statement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("Can't close connection", e);
                }
            }
        }
        return result;
    }

    @Override
    public List<Lesson> findGroupLessons(int groupId, DayOfWeek dayOfWeek) throws DaoException {
        log.info("Finding lessons for groupId " + groupId);
        List<Lesson> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM lessons WHERE group_id=? AND UPPER(week_day) LIKE UPPER(?)");
            statement.setInt(1, groupId);
            statement.setString(2, dayOfWeek.toString());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Lesson lesson = new Lesson();
                lesson.setId(resultSet.getInt("id"));
                lesson.setSubject(resultSet.getString("subject"));
                lesson.setDayOfWeek(dayOfWeek);
                lesson.setTimeOfDay(TimeOfDay.convert(resultSet.getString("day_time")));
                Classroom classroom = classroomDao.find(resultSet.getInt("room_id"));
                lesson.setClassroom(classroom);
                Teacher teacher = teacherDao.find(resultSet.getInt("teacher_id"));
                lesson.setTeacher(teacher);
                Group group = groupDao.find(groupId);
                lesson.setGroup(group);
                result.add(lesson);
            }
        } catch (SQLException e) {
            log.error("Can't find group lessons", e);
            throw new DaoException("Can't find group lessons", e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    log.error("Can't close result set", e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error("Can't close statement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("Can't close connection", e);
                }
            }
        }
        return result;
    }

    @Override
    public List<Lesson> findTeacherLessons(int teacherId, DayOfWeek dayOfWeek) throws DaoException {
        log.info("Finding lessons for teacherId " + teacherId);
        List<Lesson> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM lessons WHERE teacher_id=? AND UPPER(week_day) LIKE UPPER(?)");
            statement.setInt(1, teacherId);
            statement.setString(2, dayOfWeek.toString());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Lesson lesson = new Lesson();
                lesson.setId(resultSet.getInt("id"));
                lesson.setSubject(resultSet.getString("subject"));
                lesson.setDayOfWeek(dayOfWeek);
                lesson.setTimeOfDay(TimeOfDay.convert(resultSet.getString("day_time")));
                Classroom classroom = classroomDao.find(resultSet.getInt("room_id"));
                lesson.setClassroom(classroom);
                Teacher teacher = teacherDao.find(teacherId);
                lesson.setTeacher(teacher);
                Group group = groupDao.find(resultSet.getInt("group_id"));
                lesson.setGroup(group);
                result.add(lesson);
            }
        } catch (SQLException e) {
            log.error("Can't find teacher lessons", e);
            throw new DaoException("Can't find teacher lessons", e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    log.error("Can't close result set", e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error("Can't close statement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("Can't close connection", e);
                }
            }
        }
        return result;
    }

    @Override
    public Lesson findGroupLesson(int groupId, DayOfWeek dayOfWeek, TimeOfDay timeOfDay) throws DaoException {
        log.info("Finding lesson for groupId " + groupId);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Lesson lesson = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT DISTINCT * FROM lessons WHERE group_id=? AND UPPER(week_day) LIKE UPPER(?) AND day_time=?");
            statement.setInt(1, groupId);
            statement.setString(2, dayOfWeek.toString());
            statement.setString(3, timeOfDay.toString());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                lesson = new Lesson();
                lesson.setId(resultSet.getInt("id"));
                lesson.setSubject(resultSet.getString("subject"));
                lesson.setDayOfWeek(dayOfWeek);
                lesson.setTimeOfDay(TimeOfDay.convert(resultSet.getString("day_time")));
                Classroom classroom = classroomDao.find(resultSet.getInt("room_id"));
                lesson.setClassroom(classroom);
                Teacher teacher = teacherDao.find(resultSet.getInt("teacher_id"));
                lesson.setTeacher(teacher);
                Group group = groupDao.find(groupId);
                lesson.setGroup(group);
            }
        } catch (SQLException e) {
            log.error("Can't find group lessons", e);
            throw new DaoException("Can't find group lessons", e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    log.error("Can't close result set", e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error("Can't close statement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("Can't close connection", e);
                }
            }
        }
        return lesson;
    }

    @Override
    public Lesson findTeacherLesson(int teacherId, DayOfWeek dayOfWeek, TimeOfDay timeOfDay) throws DaoException {
        log.info("Finding lessons for teacherId " + teacherId);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Lesson lesson = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT DISTINCT * FROM lessons WHERE teacher_id=? AND UPPER(week_day) LIKE UPPER(?) AND day_time=?");
            statement.setInt(1, teacherId);
            statement.setString(2, dayOfWeek.toString());
            statement.setString(3, timeOfDay.toString());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                lesson = new Lesson();
                lesson.setId(resultSet.getInt("id"));
                lesson.setSubject(resultSet.getString("subject"));
                lesson.setDayOfWeek(dayOfWeek);
                lesson.setTimeOfDay(TimeOfDay.convert(resultSet.getString("day_time")));
                Classroom classroom = classroomDao.find(resultSet.getInt("room_id"));
                lesson.setClassroom(classroom);
                Teacher teacher = teacherDao.find(teacherId);
                lesson.setTeacher(teacher);
                Group group = groupDao.find(resultSet.getInt("group_id"));
                lesson.setGroup(group);
            }
        } catch (SQLException e) {
            log.error("Can't find teacher lessons", e);
            throw new DaoException("Can't find teacher lessons", e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    log.error("Can't close result set", e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error("Can't close statement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("Can't close connection", e);
                }
            }
        }
        return lesson;
    }
}