package com.grubjack.university.dao.jdbc;

import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.FacultyDao;
import com.grubjack.university.dao.GroupDao;
import com.grubjack.university.domain.Faculty;
import com.grubjack.university.domain.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.grubjack.university.dao.DaoFactory.getConnection;

/**
 * Created by grubjack on 03.11.2016.
 */
public class GroupDaoPlainJdbcImpl implements GroupDao {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private FacultyDao facultyDao;


    public GroupDaoPlainJdbcImpl() {
        this.facultyDao = daoFactory.getFacultyDao();
    }

    @Override
    public void create(Group group) {
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO groups (name, faculty_id) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, group.getName());
            statement.setInt(2, group.getFaculty().getId());
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                group.setId(resultSet.getInt(1));
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
    public void update(Group group) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE groups SET name=?,faculty_id=? WHERE id=?")) {
            statement.setString(1, group.getName());
            statement.setInt(2, group.getFaculty().getId());
            statement.setInt(3, group.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM groups WHERE id=?")) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Group find(int id) {
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT name, faculty_id FROM groups WHERE id=?")) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            Group group = null;
            if (resultSet.next()) {
                group = new Group();
                group.setId(id);
                group.setName(resultSet.getString("name"));
                Faculty faculty = facultyDao.find(resultSet.getInt("faculty_id"));
                group.setFaculty(faculty);
            }
            return group;
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
    public List<Group> findAll() {
        List<Group> result = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM groups");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Group group = new Group();
                group.setId(resultSet.getInt("id"));
                group.setName(resultSet.getString("name"));
                Faculty faculty = facultyDao.find(resultSet.getInt("faculty_id"));
                group.setFaculty(faculty);
                result.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Group findByName(String name) {
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT * FROM groups WHERE UPPER(name) LIKE UPPER(?)")) {
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            Group group = null;
            if (resultSet.next()) {
                group = new Group();
                group.setId(resultSet.getInt("id"));
                group.setName(resultSet.getString("name"));
                Faculty faculty = facultyDao.find(resultSet.getInt("faculty_id"));
                group.setFaculty(faculty);
            }
            return group;
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
    public List<Group> findAll(int facultyId) {
        List<Group> result = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM groups WHERE faculty_id=?")) {
            statement.setInt(1, facultyId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Group group = new Group();
                group.setId(resultSet.getInt("id"));
                group.setName(resultSet.getString("name"));
                Faculty faculty = facultyDao.find(facultyId);
                group.setFaculty(faculty);
                result.add(group);
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
