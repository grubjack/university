package com.grubjack.university.dao.impl;

import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.DepartmentDao;
import com.grubjack.university.dao.PersonDao;
import com.grubjack.university.domain.Department;
import com.grubjack.university.domain.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.grubjack.university.dao.DaoFactory.getConnection;


/**
 * Created by grubjack on 03.11.2016.
 */
public class TeacherDaoImpl implements PersonDao<Teacher> {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private DepartmentDao departmentDao;


    public TeacherDaoImpl() {
        this.departmentDao = daoFactory.getDepartmentDao();
    }

    @Override
    public void create(Teacher teacher) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("INSERT INTO teachers (firstname, lastname,salary, department_id) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, teacher.getFirstName());
            statement.setString(2, teacher.getLastName());
            statement.setInt(3, teacher.getSalary());
            statement.setInt(4, teacher.getDepartment().getId());
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                teacher.setId(resultSet.getInt(1));
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
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
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
    public void update(Teacher teacher) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("UPDATE teachers SET firstname=?,lastname=?,salary=?,department_id=? WHERE id=?");
            statement.setString(1, teacher.getFirstName());
            statement.setString(2, teacher.getLastName());
            statement.setInt(3, teacher.getSalary());
            statement.setInt(4, teacher.getDepartment().getId());
            statement.setInt(5, teacher.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
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
    public void delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("DELETE FROM teachers WHERE id=?");
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
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
    public Teacher find(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT firstname, lastname,salary, department_id FROM teachers WHERE id=?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            Teacher teacher = null;
            if (resultSet.next()) {
                teacher = new Teacher();
                teacher.setId(id);
                teacher.setFirstName(resultSet.getString("firstname"));
                teacher.setLastName(resultSet.getString("lastname"));
                teacher.setSalary(resultSet.getInt("salary"));
                Department department = departmentDao.find(resultSet.getInt("department_id"));
                teacher.setDepartment(department);
            }
            return teacher;
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
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
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
        return null;
    }

    @Override
    public List<Teacher> findAll() {
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
                Department department = departmentDao.find(resultSet.getInt("department_id"));
                teacher.setDepartment(department);
                result.add(teacher);
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
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
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
        return result;
    }

    @Override
    public List<Teacher> findByFirstName(String firstName) {
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
                Department department = departmentDao.find(resultSet.getInt("department_id"));
                teacher.setDepartment(department);
                result.add(teacher);
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
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
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
        return result;
    }


    @Override
    public List<Teacher> findByFirstName(int userId, String firstName) {
        List<Teacher> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM teachers WHERE department_id=? AND UPPER(firstname) LIKE UPPER(?)");
            statement.setInt(1, userId);
            statement.setString(2, firstName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getInt("id"));
                teacher.setFirstName(resultSet.getString("firstname"));
                teacher.setLastName(resultSet.getString("lastname"));
                teacher.setSalary(resultSet.getInt("salary"));
                Department department = departmentDao.find(resultSet.getInt("department_id"));
                teacher.setDepartment(department);
                result.add(teacher);
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
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
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
        return result;
    }

    @Override
    public List<Teacher> findByLastName(String lastName) {
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
                Department department = departmentDao.find(resultSet.getInt("department_id"));
                teacher.setDepartment(department);
                result.add(teacher);
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
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
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
        return result;
    }

    @Override
    public List<Teacher> findByLastName(int unitId, String lastName) {
        List<Teacher> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM teachers WHERE department_id=? AND UPPER(lastname) LIKE UPPER(?)");
            statement.setInt(1, unitId);
            statement.setString(2, lastName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getInt("id"));
                teacher.setFirstName(resultSet.getString("firstname"));
                teacher.setLastName(resultSet.getString("lastname"));
                teacher.setSalary(resultSet.getInt("salary"));
                Department department = departmentDao.find(resultSet.getInt("department_id"));
                teacher.setDepartment(department);
                result.add(teacher);
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
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
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
        return result;
    }

    @Override
    public List<Teacher> findByName(String firstName, String lastName) {
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
                Department department = departmentDao.find(resultSet.getInt("department_id"));
                teacher.setDepartment(department);
                result.add(teacher);
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
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
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
        return result;
    }

    @Override
    public List<Teacher> findByName(int userId, String firstName, String lastName) {
        List<Teacher> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM teachers WHERE department_id=? AND UPPER(firstname) LIKE UPPER(?) AND UPPER(lastname) LIKE UPPER(?)");
            statement.setInt(1, userId);
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getInt("id"));
                teacher.setFirstName(resultSet.getString("firstname"));
                teacher.setLastName(resultSet.getString("lastname"));
                teacher.setSalary(resultSet.getInt("salary"));
                Department department = departmentDao.find(resultSet.getInt("department_id"));
                teacher.setDepartment(department);
                result.add(teacher);
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
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
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
        return result;
    }

    @Override
    public List<Teacher> findAll(int unitId) {
        List<Teacher> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM teachers WHERE department_id=?");
            statement.setInt(1, unitId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getInt("id"));
                teacher.setFirstName(resultSet.getString("firstname"));
                teacher.setLastName(resultSet.getString("lastname"));
                teacher.setSalary(resultSet.getInt("salary"));
                Department department = departmentDao.find(resultSet.getInt("department_id"));
                teacher.setDepartment(department);
                result.add(teacher);
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
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
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
        return result;
    }
}
