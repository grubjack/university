package com.grubjack.university.dao.jdbc;

import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.DepartmentDao;
import com.grubjack.university.dao.FacultyDao;
import com.grubjack.university.domain.Department;
import com.grubjack.university.domain.Faculty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.grubjack.university.dao.DaoFactory.getConnection;

/**
 * Created by grubjack on 03.11.2016.
 */
public class DepartmentDaoPlainJdbcImpl implements DepartmentDao {

    private DaoFactory daoFactory = DaoFactory.getInstance();
    private FacultyDao facultyDao;


    public DepartmentDaoPlainJdbcImpl() {
        this.facultyDao = daoFactory.getFacultyDao();
    }


    @Override
    public void create(Department department) {
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO departments (name, faculty_id) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, department.getName());
            statement.setInt(2, department.getFaculty().getId());
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                department.setId(resultSet.getInt(1));
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
    public void update(Department department) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE departments SET name=?,faculty_id=? WHERE id=?")) {
            statement.setString(1, department.getName());
            statement.setInt(2, department.getFaculty().getId());
            statement.setInt(3, department.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM departments WHERE id=?")) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Department find(int id) {
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT name, faculty_id FROM departments WHERE id=?")) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            Department department = null;
            if (resultSet.next()) {
                department = new Department();
                department.setId(id);
                department.setName(resultSet.getString("name"));
                Faculty faculty = facultyDao.find(resultSet.getInt("faculty_id"));
                department.setFaculty(faculty);
            }
            return department;
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
    public List<Department> findAll() {
        List<Department> result = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM departments");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Department department = new Department();
                department.setId(resultSet.getInt("id"));
                department.setName(resultSet.getString("name"));
                Faculty faculty = facultyDao.find(resultSet.getInt("faculty_id"));
                department.setFaculty(faculty);
                result.add(department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Department findByName(String name) {
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT * FROM departments WHERE UPPER(name) LIKE UPPER(?)")) {
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            Department department = null;
            if (resultSet.next()) {
                department = new Department();
                department.setId(resultSet.getInt("id"));
                department.setName(resultSet.getString("name"));
                Faculty faculty = facultyDao.find(resultSet.getInt("faculty_id"));
                department.setFaculty(faculty);
            }
            return department;
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
    public List<Department> findAll(int facultyId) {
        List<Department> result = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM departments WHERE faculty_id=?")) {
            statement.setInt(1, facultyId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Department department = new Department();
                department.setId(resultSet.getInt("id"));
                department.setName(resultSet.getString("name"));
                Faculty faculty = facultyDao.find(facultyId);
                department.setFaculty(faculty);
                result.add(department);
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
