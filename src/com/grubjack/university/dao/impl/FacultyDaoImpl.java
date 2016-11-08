package com.grubjack.university.dao.impl;

import com.grubjack.university.DaoException;
import com.grubjack.university.dao.FacultyDao;
import com.grubjack.university.domain.Faculty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.grubjack.university.dao.DaoFactory.getConnection;

/**
 * Created by grubjack on 03.11.2016.
 */
public class FacultyDaoImpl implements FacultyDao {

    private static Logger log = LoggerFactory.getLogger(FacultyDaoImpl.class);

    @Override
    public void create(Faculty faculty) throws DaoException {
        log.info("Creating new faculty " + faculty.getName());
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("INSERT INTO faculties (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, faculty.getName());
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                faculty.setId(resultSet.getInt(1));
                log.info("Faculty is created with id = " + faculty.getId());
            }
        } catch (SQLException e) {
            log.error("Can't create faculty", e);
            throw new DaoException("Can't create faculty", e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    log.warn("Can't close result set", e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.warn("Can't close statement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.warn("Can't close connection", e);
                }
            }
        }
    }

    @Override
    public void update(Faculty faculty) throws DaoException {
        log.info("Updating faculty with id " + faculty.getId());
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("UPDATE faculties SET name=? WHERE id=?");
            statement.setString(1, faculty.getName());
            statement.setInt(2, faculty.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Can't update faculty", e);
            throw new DaoException("Can't update faculty", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.warn("Can't close statement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.warn("Can't close connection", e);
                }
            }
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        log.info("Deleting faculty with id " + id);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("DELETE FROM faculties WHERE id=?");
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.error("Can't delete faculty", e);
            throw new DaoException("Can't delete faculty", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.warn("Can't close statement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.warn("Can't close connection", e);
                }
            }
        }
    }

    @Override
    public Faculty find(int id) throws DaoException {
        log.info("Finding faculty with id " + id);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Faculty faculty = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM faculties WHERE id=?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                faculty = new Faculty();
                faculty.setId(id);
                faculty.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            log.error("Can't find faculty", e);
            throw new DaoException("Can't find faculty", e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    log.warn("Can't close result set", e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.warn("Can't close statement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.warn("Can't close connection", e);
                }
            }
        }
        return faculty;
    }

    @Override
    public List<Faculty> findAll() throws DaoException {
        log.info("Finding all faculties");
        List<Faculty> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM faculties");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Faculty faculty = new Faculty();
                faculty.setId(resultSet.getInt("id"));
                faculty.setName(resultSet.getString("name"));
                result.add(faculty);
            }
        } catch (SQLException e) {
            log.error("Can't find faculties", e);
            throw new DaoException("Can't find faculties", e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    log.warn("Can't close result set", e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.warn("Can't close statement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.warn("Can't close connection", e);
                }
            }
        }
        return result;
    }

    @Override
    public Faculty findByName(String name) throws DaoException {
        log.info("Finding faculty with name " + name);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Faculty faculty = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT DISTINCT * FROM faculties WHERE UPPER(name) LIKE UPPER(?)");
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                faculty = new Faculty();
                faculty.setId(resultSet.getInt("id"));
                faculty.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            log.error("Can't find faculty by name", e);
            throw new DaoException("Can't find faculty by name", e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    log.warn("Can't close result set", e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.warn("Can't close statement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.warn("Can't close connection", e);
                }
            }
        }
        return faculty;
    }

}
