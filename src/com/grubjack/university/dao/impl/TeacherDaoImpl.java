package com.grubjack.university.dao.impl;

import com.grubjack.university.exception.DaoException;
import com.grubjack.university.dao.PersonDao;
import com.grubjack.university.domain.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.grubjack.university.dao.DaoFactory.getConnection;


/**
 * Created by grubjack on 03.11.2016.
 */
public class TeacherDaoImpl implements PersonDao<Teacher> {

    private static Logger log = LoggerFactory.getLogger(TeacherDaoImpl.class);

    public TeacherDaoImpl() {
    }

    @Override
    public void create(Teacher teacher, int departmentId) throws DaoException {
        log.info("Creating new teacher");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("INSERT INTO teachers (firstname, lastname,salary, department_id) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, teacher.getFirstName());
            statement.setString(2, teacher.getLastName());
            statement.setInt(3, teacher.getSalary());
            statement.setInt(4, departmentId);
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                teacher.setId(resultSet.getInt(1));
                log.info("Teacher is created with id = " + teacher.getId());
            }
        } catch (SQLException e) {
            log.error("Can't create teacher", e);
            throw new DaoException("Can't create teacher", e);
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
    public void update(Teacher teacher, int departmentId) throws DaoException {
        log.info("Updating teacher with id " + teacher.getId());
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("UPDATE teachers SET firstname=?,lastname=?,salary=?,department_id=? WHERE id=?");
            statement.setString(1, teacher.getFirstName());
            statement.setString(2, teacher.getLastName());
            statement.setInt(3, teacher.getSalary());
            statement.setInt(4, departmentId);
            statement.setInt(5, teacher.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Can't update teacher", e);
            throw new DaoException("Can't update teacher", e);
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
        log.info("Deleting teacher with id " + id);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("DELETE FROM teachers WHERE id=?");
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.error("Can't delete teacher", e);
            throw new DaoException("Can't delete teacher", e);
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
    public Teacher find(int id) throws DaoException {
        log.info("Finding teacher with id " + id);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Teacher teacher = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM teachers WHERE id=?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                teacher = new Teacher();
                teacher.setId(id);
                teacher.setFirstName(resultSet.getString("firstname"));
                teacher.setLastName(resultSet.getString("lastname"));
                teacher.setSalary(resultSet.getInt("salary"));
            }
        } catch (SQLException e) {
            log.error("Can't find teacher", e);
            throw new DaoException("Can't find teacher", e);
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
        return teacher;
    }

    @Override
    public List<Teacher> findAll() throws DaoException {
        log.info("Finding all teachers");
        List<Teacher> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM teachers");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getInt("id"));
                teacher.setFirstName(resultSet.getString("firstname"));
                teacher.setLastName(resultSet.getString("lastname"));
                teacher.setSalary(resultSet.getInt("salary"));
                result.add(teacher);
            }
        } catch (SQLException e) {
            log.error("Can't find teachers", e);
            throw new DaoException("Can't find teachers", e);
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
    public List<Teacher> findByFirstName(String firstName) throws DaoException {
        log.info("Finding teachers with firstname " + firstName);
        List<Teacher> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM teachers WHERE UPPER(firstname) LIKE UPPER(?)");
            statement.setString(1, firstName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getInt("id"));
                teacher.setFirstName(resultSet.getString("firstname"));
                teacher.setLastName(resultSet.getString("lastname"));
                teacher.setSalary(resultSet.getInt("salary"));
                result.add(teacher);
            }
        } catch (SQLException e) {
            log.error("Can't find teachers by firstname", e);
            throw new DaoException("Can't find teachers by firstname", e);
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
    public List<Teacher> findByFirstName(int departmentId, String firstName) throws DaoException {
        log.info("Finding teachers with firstname " + firstName + " from department with id " + departmentId);
        List<Teacher> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM teachers WHERE department_id=? AND UPPER(firstname) LIKE UPPER(?)");
            statement.setInt(1, departmentId);
            statement.setString(2, firstName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getInt("id"));
                teacher.setFirstName(resultSet.getString("firstname"));
                teacher.setLastName(resultSet.getString("lastname"));
                teacher.setSalary(resultSet.getInt("salary"));
                result.add(teacher);
            }
        } catch (SQLException e) {
            log.error("Can't find teachers by firstname and department", e);
            throw new DaoException("Can't find teachers by firstname and department", e);
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
    public List<Teacher> findByLastName(String lastName) throws DaoException {
        log.info("Finding teachers with lastname " + lastName);
        List<Teacher> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM teachers WHERE UPPER(lastname) LIKE UPPER(?)");
            statement.setString(1, lastName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getInt("id"));
                teacher.setFirstName(resultSet.getString("firstname"));
                teacher.setLastName(resultSet.getString("lastname"));
                teacher.setSalary(resultSet.getInt("salary"));
                result.add(teacher);
            }
        } catch (SQLException e) {
            log.error("Can't find teachers by lastname", e);
            throw new DaoException("Can't find teachers by lastname", e);
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
    public List<Teacher> findByLastName(int departmentId, String lastName) throws DaoException {
        log.info("Finding teachers with lastname " + lastName + " from department with id " + departmentId);
        List<Teacher> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM teachers WHERE department_id=? AND UPPER(lastname) LIKE UPPER(?)");
            statement.setInt(1, departmentId);
            statement.setString(2, lastName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getInt("id"));
                teacher.setFirstName(resultSet.getString("firstname"));
                teacher.setLastName(resultSet.getString("lastname"));
                teacher.setSalary(resultSet.getInt("salary"));
                result.add(teacher);
            }
        } catch (SQLException e) {
            log.error("Can't find teachers by lastname and department", e);
            throw new DaoException("Can't find teachers by lastname and department", e);
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
    public List<Teacher> findByName(String firstName, String lastName) throws DaoException {
        log.info("Finding teachers with firstname " + lastName + " and lastname " + lastName);
        List<Teacher> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM teachers WHERE UPPER(firstname) LIKE UPPER(?) AND UPPER(lastname) LIKE UPPER(?)");
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getInt("id"));
                teacher.setFirstName(resultSet.getString("firstname"));
                teacher.setLastName(resultSet.getString("lastname"));
                teacher.setSalary(resultSet.getInt("salary"));
                result.add(teacher);
            }
        } catch (SQLException e) {
            log.error("Can't find teachers by name", e);
            throw new DaoException("Can't find teachers by name", e);
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
    public List<Teacher> findByName(int departmentId, String firstName, String lastName) throws DaoException {
        log.info("Finding teachers with firstname " + lastName + " and lastname " + lastName + " from department with id " + departmentId);
        List<Teacher> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM teachers WHERE department_id=? AND UPPER(firstname) LIKE UPPER(?) AND UPPER(lastname) LIKE UPPER(?)");
            statement.setInt(1, departmentId);
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getInt("id"));
                teacher.setFirstName(resultSet.getString("firstname"));
                teacher.setLastName(resultSet.getString("lastname"));
                teacher.setSalary(resultSet.getInt("salary"));
                result.add(teacher);
            }
        } catch (SQLException e) {
            log.error("Can't find teachers by name and department", e);
            throw new DaoException("Can't find teachers by name and department", e);
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
    public List<Teacher> findAll(int departmentId) throws DaoException {
        log.info("Finding all teachers from department with id " + departmentId);
        List<Teacher> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM teachers WHERE department_id=?");
            statement.setInt(1, departmentId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getInt("id"));
                teacher.setFirstName(resultSet.getString("firstname"));
                teacher.setLastName(resultSet.getString("lastname"));
                teacher.setSalary(resultSet.getInt("salary"));
                result.add(teacher);
            }
        } catch (SQLException e) {
            log.error("Can't find teachers from department", e);
            throw new DaoException("Can't find teachers from department", e);
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
}
