package com.grubjack.university.dao.impl;

import com.grubjack.university.domain.DayOfWeek;
import com.grubjack.university.domain.TimeOfDay;
import com.grubjack.university.exception.DaoException;
import com.grubjack.university.dao.PersonDao;
import com.grubjack.university.domain.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.grubjack.university.dao.DaoFactory.getConnection;

/**
 * Created by grubjack on 03.11.2016.
 */
public class StudentDaoImpl implements PersonDao<Student> {

    private static Logger log = LoggerFactory.getLogger(StudentDaoImpl.class);

    public StudentDaoImpl() {
    }

    @Override
    public void create(Student student, int groupId) throws DaoException {
        log.info("Creating new student");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("INSERT INTO students (firstname, lastname, group_id) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setInt(3, groupId);
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                student.setId(resultSet.getInt(1));
                log.info("Student is created with id = " + student.getId());
            }
        } catch (SQLException e) {
            log.error("Can't create student", e);
            throw new DaoException("Can't create student", e);
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
    public void update(Student student, int groupId) throws DaoException {
        log.info("Updating student with id " + student.getId());
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("UPDATE students SET firstname=?,lastname=?,group_id=? WHERE id=?");
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setInt(3, groupId);
            statement.setInt(4, student.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Can't update student", e);
            throw new DaoException("Can't update student", e);
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
        log.info("Deleting student with id " + id);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("DELETE FROM students WHERE id=?");
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.error("Can't delete student", e);
            throw new DaoException("Can't delete student", e);
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
    public Student find(int id) throws DaoException {
        log.info("Finding student with id " + id);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Student student = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM students WHERE id=?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                student = new Student();
                student.setId(id);
                student.setFirstName(resultSet.getString("firstname"));
                student.setLastName(resultSet.getString("lastname"));
            }
        } catch (SQLException e) {
            log.error("Can't find student", e);
            throw new DaoException("Can't find student", e);
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
        return student;
    }

    @Override
    public List<Student> findAll() throws DaoException {
        log.info("Finding all students");
        List<Student> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM students");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setFirstName(resultSet.getString("firstname"));
                student.setLastName(resultSet.getString("lastname"));
                result.add(student);
            }
        } catch (SQLException e) {
            log.error("Can't find students", e);
            throw new DaoException("Can't find students", e);
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
    public List<Student> findByFirstName(String firstName) throws DaoException {
        log.info("Finding student with firstname " + firstName);
        List<Student> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM students WHERE UPPER(firstname) LIKE UPPER(?)");
            statement.setString(1, firstName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setFirstName(resultSet.getString("firstname"));
                student.setLastName(resultSet.getString("lastname"));
                result.add(student);
            }
        } catch (SQLException e) {
            log.error("Can't find student by firstname", e);
            throw new DaoException("Can't find student by firstname", e);
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
    public List<Student> findByFirstName(int groupId, String firstName) throws DaoException {
        log.info("Finding student with firstname " + firstName + " from group with id " + groupId);
        List<Student> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM students WHERE group_id=? AND UPPER(firstname) LIKE UPPER(?)");
            statement.setInt(1, groupId);
            statement.setString(2, firstName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setFirstName(resultSet.getString("firstname"));
                student.setLastName(resultSet.getString("lastname"));
                result.add(student);
            }
        } catch (SQLException e) {
            log.error("Can't find student by firstname and group", e);
            throw new DaoException("Can't find student by firstname and group", e);
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
    public List<Student> findByLastName(String lastName) throws DaoException {
        log.info("Finding students with lastname " + lastName);
        List<Student> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM students WHERE UPPER(lastname) LIKE UPPER(?)");
            statement.setString(1, lastName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setFirstName(resultSet.getString("firstname"));
                student.setLastName(resultSet.getString("lastname"));
                result.add(student);
            }
        } catch (SQLException e) {
            log.error("Can't find students by lastname", e);
            throw new DaoException("Can't find students by lastname", e);
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
    public List<Student> findByLastName(int groupId, String lastName) throws DaoException {
        log.info("Finding students with lastname " + lastName + " from group with id " + groupId);
        List<Student> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM students WHERE group_id=? AND UPPER(lastname) LIKE UPPER(?)");
            statement.setInt(1, groupId);
            statement.setString(2, lastName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setFirstName(resultSet.getString("firstname"));
                student.setLastName(resultSet.getString("lastname"));
                result.add(student);
            }
        } catch (SQLException e) {
            log.error("Can't find students by lastname and group", e);
            throw new DaoException("Can't find students by lastname and group", e);
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
    public List<Student> findByName(String firstName, String lastName) throws DaoException {
        log.info("Finding students with firstname " + lastName + " and lastname " + lastName);
        List<Student> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM students WHERE UPPER(firstname) LIKE UPPER(?) AND UPPER(lastname) LIKE UPPER(?)");
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setFirstName(resultSet.getString("firstname"));
                student.setLastName(resultSet.getString("lastname"));
                result.add(student);
            }
        } catch (SQLException e) {
            log.error("Can't find students by name", e);
            throw new DaoException("Can't find students by name", e);
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
    public List<Student> findByName(int groupId, String firstName, String lastName) throws DaoException {
        log.info("Finding students with firstname " + lastName + " and lastname " + lastName + " from group with id " + groupId);
        List<Student> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM students WHERE group_id=? AND UPPER(firstname) LIKE UPPER(?) AND UPPER(lastname) LIKE UPPER(?)");
            statement.setInt(1, groupId);
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setFirstName(resultSet.getString("firstname"));
                student.setLastName(resultSet.getString("lastname"));
                result.add(student);
            }
        } catch (SQLException e) {
            log.error("Can't find students by name and group", e);
            throw new DaoException("Can't find students by name and group", e);
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
    public List<Student> findAll(int groupId) throws DaoException {
        log.info("Finding all students from group with id " + groupId);
        List<Student> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM students WHERE group_id=?");
            statement.setInt(1, groupId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setFirstName(resultSet.getString("firstname"));
                student.setLastName(resultSet.getString("lastname"));
                result.add(student);
            }
        } catch (SQLException e) {
            log.error("Can't find students from group", e);
            throw new DaoException("Can't find students from group", e);
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
    public List<Student> findAvailable(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) throws DaoException {
        return null;
    }
}
