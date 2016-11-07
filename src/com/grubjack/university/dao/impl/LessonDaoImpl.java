package com.grubjack.university.dao.impl;

import com.grubjack.university.dao.*;
import com.grubjack.university.domain.*;

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
    private FacultyDao facultyDao;

    public LessonDaoImpl() {
        this.classroomDao = daoFactory.getClassroomDao();
        this.teacherDao = daoFactory.getTeacherDao();
        this.groupDao = daoFactory.getGroupDao();
        this.facultyDao = daoFactory.getFacultyDao();
    }

    @Override
    public void create(Lesson lesson) {
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             "INSERT INTO lessons (subject,week_day,room_id,teacher_id,group_id,faculty_id) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, lesson.getSubject());
            statement.setString(2, lesson.getDayOfWeek().toString());
            statement.setInt(3, lesson.getClassroom().getId());
            statement.setInt(4, lesson.getTeacher().getId());
            statement.setInt(5, lesson.getGroup().getId());
            statement.setInt(6, lesson.getFaculty().getId());
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                lesson.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void update(Lesson lesson) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE lessons SET subject=?,week_day=?,room_id=?,teacher_id=?,group_id=?,faculty_id=? WHERE id=?")) {
            statement.setString(1, lesson.getSubject());
            statement.setString(2, lesson.getDayOfWeek().toString());
            statement.setInt(3, lesson.getClassroom().getId());
            statement.setInt(4, lesson.getTeacher().getId());
            statement.setInt(5, lesson.getGroup().getId());
            statement.setInt(6, lesson.getFaculty().getId());
            statement.setInt(7, lesson.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM lessons WHERE id=?")) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Lesson find(int id) {
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT subject,week_day,room_id,teacher_id,group_id,faculty_id name FROM lessons WHERE id=?")) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            Lesson lesson = null;
            if (resultSet.next()) {
                lesson = new Lesson();
                lesson.setId(id);
                lesson.setSubject(resultSet.getString("subject"));
                lesson.setDayOfWeek(DayOfWeek.valueOf(resultSet.getString("week_day")));
                Classroom classroom = classroomDao.find(resultSet.getInt("room_id"));
                lesson.setClassroom(classroom);
                Teacher teacher = teacherDao.find(resultSet.getInt("teacher_id"));
                lesson.setTeacher(teacher);
                Group group = groupDao.find(resultSet.getInt("group_id"));
                lesson.setGroup(group);
                Faculty faculty = facultyDao.find(resultSet.getInt("faculty_id"));
                lesson.setFaculty(faculty);
            }
            return lesson;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public List<Lesson> findAll() {
        List<Lesson> result = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM lessons");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Lesson lesson = new Lesson();
                lesson.setId(resultSet.getInt("id"));
                lesson.setSubject(resultSet.getString("subject"));
                lesson.setDayOfWeek(DayOfWeek.valueOf(resultSet.getString("week_day")));
                Classroom classroom = classroomDao.find(resultSet.getInt("room_id"));
                lesson.setClassroom(classroom);
                Teacher teacher = teacherDao.find(resultSet.getInt("teacher_id"));
                lesson.setTeacher(teacher);
                Group group = groupDao.find(resultSet.getInt("group_id"));
                lesson.setGroup(group);
                Faculty faculty = facultyDao.find(resultSet.getInt("faculty_id"));
                lesson.setFaculty(faculty);
                result.add(lesson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Lesson> findAllByDayForGroup(int groupId, DayOfWeek dayOfWeek) {
        List<Lesson> result = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM lessons WHERE group_id=? AND UPPER(week_day) LIKE UPPER(?)")) {
            statement.setInt(1, groupId);
            statement.setString(2, dayOfWeek.toString());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Lesson lesson = new Lesson();
                lesson.setId(resultSet.getInt("id"));
                lesson.setSubject(resultSet.getString("subject"));
                lesson.setDayOfWeek(dayOfWeek);
                Classroom classroom = classroomDao.find(resultSet.getInt("room_id"));
                lesson.setClassroom(classroom);
                Teacher teacher = teacherDao.find(resultSet.getInt("teacher_id"));
                lesson.setTeacher(teacher);
                Group group = groupDao.find(groupId);
                lesson.setGroup(group);
                Faculty faculty = facultyDao.find(resultSet.getInt("faculty_id"));
                lesson.setFaculty(faculty);
                result.add(lesson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public List<Lesson> findAllByDayForTeacher(int teacherId, DayOfWeek dayOfWeek) {
        List<Lesson> result = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM lessons WHERE teacher_id=? AND UPPER(week_day) LIKE UPPER(?)")) {
            statement.setInt(1, teacherId);
            statement.setString(2, dayOfWeek.toString());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Lesson lesson = new Lesson();
                lesson.setId(resultSet.getInt("id"));
                lesson.setSubject(resultSet.getString("subject"));
                lesson.setDayOfWeek(dayOfWeek);
                Classroom classroom = classroomDao.find(resultSet.getInt("room_id"));
                lesson.setClassroom(classroom);
                Teacher teacher = teacherDao.find(teacherId);
                lesson.setTeacher(teacher);
                Group group = groupDao.find(resultSet.getInt("group_id"));
                lesson.setGroup(group);
                Faculty faculty = facultyDao.find(resultSet.getInt("faculty_id"));
                lesson.setFaculty(faculty);
                result.add(lesson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public List<Lesson> findAllByDayForFacultyGroup(int facultyId, int groupId, DayOfWeek dayOfWeek) {
        List<Lesson> result = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM lessons WHERE faculty_id=? AND  group_id=? AND UPPER(week_day) LIKE UPPER(?)")) {
            statement.setInt(1, facultyId);
            statement.setInt(2, groupId);
            statement.setString(3, dayOfWeek.toString());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Lesson lesson = new Lesson();
                lesson.setId(resultSet.getInt("id"));
                lesson.setSubject(resultSet.getString("subject"));
                lesson.setDayOfWeek(dayOfWeek);
                Classroom classroom = classroomDao.find(resultSet.getInt("room_id"));
                lesson.setClassroom(classroom);
                Teacher teacher = teacherDao.find(resultSet.getInt("teacher_id"));
                lesson.setTeacher(teacher);
                Group group = groupDao.find(groupId);
                lesson.setGroup(group);
                Faculty faculty = facultyDao.find(resultSet.getInt("faculty_id"));
                lesson.setFaculty(faculty);
                result.add(lesson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public List<Lesson> findAllByDayForFacultyTeacher(int facultyId, int teacherId, DayOfWeek dayOfWeek) {
        List<Lesson> result = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM lessons WHERE faculty_id=? AND teacher_id=? AND UPPER(week_day) LIKE UPPER(?)")) {
            statement.setInt(1, facultyId);
            statement.setInt(2, teacherId);
            statement.setString(3, dayOfWeek.toString());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Lesson lesson = new Lesson();
                lesson.setId(resultSet.getInt("id"));
                lesson.setSubject(resultSet.getString("subject"));
                lesson.setDayOfWeek(dayOfWeek);
                Classroom classroom = classroomDao.find(resultSet.getInt("room_id"));
                lesson.setClassroom(classroom);
                Teacher teacher = teacherDao.find(teacherId);
                lesson.setTeacher(teacher);
                Group group = groupDao.find(resultSet.getInt("group_id"));
                lesson.setGroup(group);
                Faculty faculty = facultyDao.find(resultSet.getInt("faculty_id"));
                lesson.setFaculty(faculty);
                result.add(lesson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
