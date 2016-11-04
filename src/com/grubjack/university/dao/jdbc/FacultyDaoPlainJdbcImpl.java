package com.grubjack.university.dao.jdbc;

import com.grubjack.university.dao.FacultyDao;
import com.grubjack.university.domain.Faculty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.grubjack.university.dao.DaoFactory.getConnection;

/**
 * Created by grubjack on 03.11.2016.
 */
public class FacultyDaoPlainJdbcImpl implements FacultyDao {

    @Override
    public void create(Faculty faculty) {
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO faculties (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, faculty.getName());
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                faculty.setId(resultSet.getInt(1));
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
    public void update(Faculty faculty) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE faculties SET name=? WHERE id=?")) {
            statement.setString(1, faculty.getName());
            statement.setInt(2, faculty.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM faculties WHERE id=?")) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Faculty find(int id) {
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id, name FROM faculties WHERE id=?")) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            Faculty faculty = null;
            if (resultSet.next()) {
                faculty = new Faculty();
                faculty.setId(id);
                faculty.setName(resultSet.getString("name"));
            }
            return faculty;
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
    public List<Faculty> findAll() {
        List<Faculty> result = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM faculties");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Faculty faculty = new Faculty();
                faculty.setId(resultSet.getInt("id"));
                faculty.setName(resultSet.getString("name"));
                result.add(faculty);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Faculty findByName(String name) {
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT * FROM faculties WHERE UPPER(name) LIKE UPPER(?)")) {
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            Faculty faculty = null;
            if (resultSet.next()) {
                faculty = new Faculty();
                faculty.setId(resultSet.getInt("id"));
                faculty.setName(resultSet.getString("name"));
            }
            return faculty;
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

}
