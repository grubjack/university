package com.grubjack.university.dao.jdbc;

import com.grubjack.university.dao.ClassroomDao;
import com.grubjack.university.domain.Classroom;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.grubjack.university.dao.DaoFactory.getConnection;

/**
 * Created by grubjack on 03.11.2016.
 */
public class ClassroomDaoPlainJdbcImpl implements ClassroomDao {

    @Override
    public void create(Classroom classroom) {
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO classrooms (number,location,size) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, classroom.getNumber());
            statement.setString(2, classroom.getLocation());
            statement.setInt(3, classroom.getSize());
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                classroom.setId(resultSet.getInt(1));
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
    public void update(Classroom classroom) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE classrooms SET number=?,location=?,size=? WHERE id=?")) {
            statement.setString(1, classroom.getNumber());
            statement.setString(2, classroom.getLocation());
            statement.setInt(3, classroom.getSize());
            statement.setInt(4, classroom.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM classrooms WHERE id=?")) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Classroom find(int id) {
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT number, location, size FROM classrooms WHERE id=?")) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            Classroom classroom = null;
            if (resultSet.next()) {
                classroom = new Classroom();
                classroom.setId(id);
                classroom.setNumber(resultSet.getString("number"));
                classroom.setLocation(resultSet.getString("location"));
                classroom.setSize(resultSet.getInt("size"));
            }
            return classroom;
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
    public List<Classroom> findAll() {
        List<Classroom> result = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM classrooms");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Classroom classroom = new Classroom();
                classroom.setId(resultSet.getInt("id"));
                classroom.setNumber(resultSet.getString("number"));
                classroom.setLocation(resultSet.getString("location"));
                classroom.setSize(resultSet.getInt("size"));
                result.add(classroom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Classroom findByNumber(String number) {
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT * FROM classrooms WHERE UPPER(number) LIKE UPPER(?)")) {
            statement.setString(1, number);
            resultSet = statement.executeQuery();
            Classroom classroom = null;
            if (resultSet.next()) {
                classroom = new Classroom();
                classroom.setId(resultSet.getInt("id"));
                classroom.setNumber(resultSet.getString("number"));
                classroom.setLocation(resultSet.getString("location"));
                classroom.setSize(resultSet.getInt("size"));
            }
            return classroom;
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
