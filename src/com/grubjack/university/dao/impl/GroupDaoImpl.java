package com.grubjack.university.dao.impl;

import com.grubjack.university.exception.DaoException;
import com.grubjack.university.dao.GroupDao;
import com.grubjack.university.domain.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.grubjack.university.dao.DaoFactory.getConnection;

/**
 * Created by grubjack on 03.11.2016.
 */
public class GroupDaoImpl implements GroupDao {
    private static Logger log = LoggerFactory.getLogger(GroupDaoImpl.class);

    public GroupDaoImpl() {
    }

    @Override
    public void create(Group group, int facultyId) throws DaoException {
        log.info("Creating new group " + group.getName());
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("INSERT INTO groups (name, faculty_id) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, group.getName());
            statement.setInt(2, facultyId);
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                group.setId(resultSet.getInt(1));
                log.info("Group is created with id = " + group.getId());
            }
        } catch (SQLException e) {
            log.error("Can't create group", e);
            throw new DaoException("Can't create group", e);
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
    public void update(Group group, int facultyId) throws DaoException {
        log.info("Updating group with id " + group.getId());
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("UPDATE groups SET name=?,faculty_id=? WHERE id=?");
            statement.setString(1, group.getName());
            statement.setInt(2, facultyId);
            statement.setInt(3, group.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Can't update group", e);
            throw new DaoException("Can't update group", e);
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
        log.info("Deleting group with id " + id);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("DELETE FROM groups WHERE id=?");
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.error("Can't delete group", e);
            throw new DaoException("Can't delete group", e);
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
    public Group find(int id) throws DaoException {
        log.info("Finding group with id " + id);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Group group = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM groups WHERE id=?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                group = new Group();
                group.setId(id);
                group.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            log.error("Can't find group", e);
            throw new DaoException("Can't find group", e);
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
        return group;
    }

    @Override
    public List<Group> findAll() throws DaoException {
        log.info("Finding all groups");
        List<Group> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM groups");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Group group = new Group();
                group.setId(resultSet.getInt("id"));
                group.setName(resultSet.getString("name"));
                result.add(group);
            }
        } catch (SQLException e) {
            log.error("Can't find groups", e);
            throw new DaoException("Can't find groups", e);
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
    public Group findByName(String name) throws DaoException {
        log.info("Finding group with name " + name);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Group group = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT DISTINCT * FROM groups WHERE UPPER(name) LIKE UPPER(?)");
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                group = new Group();
                group.setId(resultSet.getInt("id"));
                group.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            log.error("Can't find group by name", e);
            throw new DaoException("Can't find group by name", e);
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
        return group;
    }

    @Override
    public List<Group> findAll(int facultyId) throws DaoException {
        log.info("Finding all groups with facultyId " + facultyId);
        List<Group> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM groups WHERE faculty_id=?");
            statement.setInt(1, facultyId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Group group = new Group();
                group.setId(resultSet.getInt("id"));
                group.setName(resultSet.getString("name"));
                result.add(group);
            }
        } catch (SQLException e) {
            log.error("Can't find faculty groups", e);
            throw new DaoException("Can't find faculty groups", e);
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
}
