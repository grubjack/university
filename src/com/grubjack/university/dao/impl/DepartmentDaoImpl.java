package com.grubjack.university.dao.impl;

import com.grubjack.university.DaoException;
import com.grubjack.university.dao.DepartmentDao;
import com.grubjack.university.domain.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.grubjack.university.dao.DaoFactory.getConnection;

/**
 * Created by grubjack on 03.11.2016.
 */
public class DepartmentDaoImpl implements DepartmentDao {

    private static Logger log = LoggerFactory.getLogger(DepartmentDaoImpl.class);

    public DepartmentDaoImpl() {
    }


    @Override
    public void create(Department department, int facultyId) throws DaoException {
        log.info("Creating new department " + department.getName());
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("INSERT INTO departments (name, faculty_id) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, department.getName());
            statement.setInt(2, facultyId);
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                department.setId(resultSet.getInt(1));
                log.info("Department is created with id = " + department.getId());
            }
        } catch (SQLException e) {
            log.error("Can't create department", e);
            throw new DaoException("Can't create department", e);
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
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void update(Department department, int facultyId) throws DaoException {
        log.info("Updating department with id " + department.getId());
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("UPDATE departments SET name=?,faculty_id=? WHERE id=?");
            statement.setString(1, department.getName());
            statement.setInt(2, facultyId);
            statement.setInt(3, department.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Can't update department", e);
            throw new DaoException("Can't update department", e);
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
        log.info("Deleting department with id " + id);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("DELETE FROM departments WHERE id=?");
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.error("Can't delete department", e);
            throw new DaoException("Can't delete department", e);
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
    public Department find(int id) throws DaoException {
        log.info("Finding department with id " + id);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Department department = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM departments WHERE id=?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                department = new Department();
                department.setId(id);
                department.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            log.error("Can't find department", e);
            throw new DaoException("Can't find department", e);
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
        return department;
    }

    @Override
    public List<Department> findAll() throws DaoException {
        log.info("Finding all departments");
        List<Department> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM departments");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Department department = new Department();
                department.setId(resultSet.getInt("id"));
                department.setName(resultSet.getString("name"));
                result.add(department);
            }
        } catch (SQLException e) {
            log.error("Can't find departments", e);
            throw new DaoException("Can't find departments", e);
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
    public Department findByName(String name) throws DaoException {
        log.info("Finding department with name " + name);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Department department = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT DISTINCT * FROM departments WHERE UPPER(name) LIKE UPPER(?)");
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                department = new Department();
                department.setId(resultSet.getInt("id"));
                department.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            log.error("Can't find department by name", e);
            throw new DaoException("Can't find department by name", e);
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
        return department;
    }

    @Override
    public List<Department> findAll(int facultyId) throws DaoException {
        log.info("Finding all departments with facultyId " + facultyId);
        List<Department> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM departments WHERE faculty_id=?");
            statement.setInt(1, facultyId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Department department = new Department();
                department.setId(resultSet.getInt("id"));
                department.setName(resultSet.getString("name"));
                result.add(department);
            }
        } catch (SQLException e) {
            log.error("Can't find faculty departments", e);
            throw new DaoException("Can't find faculty departments", e);
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
