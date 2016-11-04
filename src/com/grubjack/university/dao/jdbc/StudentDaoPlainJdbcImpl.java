package com.grubjack.university.dao.jdbc;

import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.GroupDao;
import com.grubjack.university.dao.PersonDao;
import com.grubjack.university.domain.Group;
import com.grubjack.university.domain.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.grubjack.university.dao.DaoFactory.getConnection;

/**
 * Created by grubjack on 03.11.2016.
 */
public class StudentDaoPlainJdbcImpl implements PersonDao<Student> {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private GroupDao groupDao;

    public StudentDaoPlainJdbcImpl() {
        this.groupDao = daoFactory.getGroupDao();
    }

    @Override
    public void create(Student student) {
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO students (firstname, lastname, group_id) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setInt(3, student.getGroup().getId());
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                student.setId(resultSet.getInt(1));
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
    public void update(Student student) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE students SET firstname=?,lastname=?,group_id=? WHERE id=?")) {
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setInt(3, student.getGroup().getId());
            statement.setInt(4, student.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM students WHERE id=?")) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Student find(int id) {
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT firstname, lastname, group_id FROM students WHERE id=?")) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            Student student = null;
            if (resultSet.next()) {
                student = new Student();
                student.setId(id);
                student.setFirstName(resultSet.getString("firstname"));
                student.setLastName(resultSet.getString("lastname"));
                Group group = groupDao.find(resultSet.getInt("group_id"));
                student.setGroup(group);
            }
            return student;
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
    public List<Student> findAll() {
        List<Student> result = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM students");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setFirstName(resultSet.getString("firstname"));
                student.setLastName(resultSet.getString("lastname"));
                Group group = groupDao.find(resultSet.getInt("group_id"));
                student.setGroup(group);
                result.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Student> findByFirstName(String firstName) {
        List<Student> result = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM students WHERE UPPER(firstname) LIKE UPPER(?)")) {
            statement.setString(1, firstName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setFirstName(resultSet.getString("firstname"));
                student.setLastName(resultSet.getString("lastname"));
                Group group = groupDao.find(resultSet.getInt("group_id"));
                student.setGroup(group);
                result.add(student);
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
    public List<Student> findByLastName(String lastName) {
        List<Student> result = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM students WHERE UPPER(lastname) LIKE UPPER(?)")) {
            statement.setString(1, lastName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setFirstName(resultSet.getString("firstname"));
                student.setLastName(resultSet.getString("lastname"));
                Group group = groupDao.find(resultSet.getInt("group_id"));
                student.setGroup(group);
                result.add(student);
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
    public List<Student> findByName(String firstName, String lastName) {
        List<Student> result = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM students WHERE UPPER(firstname) LIKE UPPER(?) AND UPPER(lastname) LIKE UPPER(?)")) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setFirstName(resultSet.getString("firstname"));
                student.setLastName(resultSet.getString("lastname"));
                Group group = groupDao.find(resultSet.getInt("group_id"));
                student.setGroup(group);
                result.add(student);
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
    public List<Student> findAll(int unitId) {
        List<Student> result = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM students WHERE group_id=?")) {
            statement.setInt(1, unitId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setFirstName(resultSet.getString("firstname"));
                student.setLastName(resultSet.getString("lastname"));
                Group group = groupDao.find(unitId);
                student.setGroup(group);
                result.add(student);
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
