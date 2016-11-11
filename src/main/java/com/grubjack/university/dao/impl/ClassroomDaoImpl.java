package com.grubjack.university.dao.impl;

import com.grubjack.university.exception.DaoException;
import com.grubjack.university.dao.ClassroomDao;
import com.grubjack.university.domain.Classroom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.grubjack.university.dao.DaoFactory.getConnection;

/**
 * Created by grubjack on 03.11.2016.
 */
public class ClassroomDaoImpl implements ClassroomDao {
    private static Logger log = LoggerFactory.getLogger(ClassroomDaoImpl.class);

    @Override
    public void create(Classroom classroom) throws DaoException {
        log.info("Creating new classroom " + classroom.getNumber());
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("INSERT INTO classrooms (number,location,capacity) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, classroom.getNumber());
            statement.setString(2, classroom.getLocation());
            statement.setInt(3, classroom.getCapacity());
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                classroom.setId(resultSet.getInt(1));
                log.info("Classroom is created with id = " + classroom.getId());
            }
        } catch (SQLException e) {
            log.error("Can't create classroom", e);
            throw new DaoException("Can't create classroom", e);
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
    public void update(Classroom classroom) throws DaoException {
        log.info("Updating classroom with id " + classroom.getId());
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("UPDATE classrooms SET number=?,location=?,capacity=? WHERE id=?");
            statement.setString(1, classroom.getNumber());
            statement.setString(2, classroom.getLocation());
            statement.setInt(3, classroom.getCapacity());
            statement.setInt(4, classroom.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Can't update classroom", e);
            throw new DaoException("Can't update classroom", e);
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
        log.info("Deleting classroom with id " + id);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("DELETE FROM classrooms WHERE id=?");
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.error("Can't delete classroom", e);
            throw new DaoException("Can't delete classroom", e);
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
    public Classroom find(int id) throws DaoException {
        log.info("Finding classroom with id " + id);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Classroom classroom = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM classrooms WHERE id=?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                classroom = new Classroom();
                classroom.setId(id);
                classroom.setNumber(resultSet.getString("number"));
                classroom.setLocation(resultSet.getString("location"));
                classroom.setCapacity(resultSet.getInt("capacity"));
            }
        } catch (SQLException e) {
            log.error("Can't find classroom", e);
            throw new DaoException("Can't find classroom", e);
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
        return classroom;
    }

    @Override
    public List<Classroom> findAll() throws DaoException {
        log.info("Finding all classrooms");
        List<Classroom> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM classrooms");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Classroom classroom = new Classroom();
                classroom.setId(resultSet.getInt("id"));
                classroom.setNumber(resultSet.getString("number"));
                classroom.setLocation(resultSet.getString("location"));
                classroom.setCapacity(resultSet.getInt("capacity"));
                result.add(classroom);
            }
        } catch (SQLException e) {
            log.error("Can't find classrooms", e);
            throw new DaoException("Can't find classrooms", e);
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
    public Classroom findByNumber(String number) throws DaoException {
        log.info("Finding classroom with number " + number);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Classroom classroom = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM classrooms WHERE UPPER(number) LIKE UPPER(?)");
            statement.setString(1, number);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                classroom = new Classroom();
                classroom.setId(resultSet.getInt("id"));
                classroom.setNumber(resultSet.getString("number"));
                classroom.setLocation(resultSet.getString("location"));
                classroom.setCapacity(resultSet.getInt("capacity"));
            }
        } catch (SQLException e) {
            log.error("Can't find classroom by number", e);
            throw new DaoException("Can't find classroom by number", e);
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
        return classroom;
    }
}
