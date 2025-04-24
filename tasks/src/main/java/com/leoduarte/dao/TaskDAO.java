package com.leoduarte.dao;

import com.leoduarte.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskDAO {

    private final Connection conn;

    private static final Logger log = LoggerFactory.getLogger(TaskDAO.class);

    public TaskDAO(Connection conn) {
        this.conn = conn;
    }

    public Task save(Task task) {
        String sql = "INSERT INTO task (title, description, done) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(
                sql,
                PreparedStatement.RETURN_GENERATED_KEYS);) {

            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setBoolean(3, task.isDone());
            stmt.executeUpdate();

            try (ResultSet resultSet = stmt.getGeneratedKeys()) {
                return taskWithGeneratedId(task, resultSet);
            }

        } catch (SQLException e) {
            String msg = "Error saving a task";
            log.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    public List<Task> getAll() {
        String sql = "SELECT * FROM task";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {

            try(ResultSet resultSet = stmt.executeQuery()) {
                return mapTasksFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            String msg = "Error getting all tasks";
            log.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    public Task update(Task task) {
        String sql = "UPDATE title, description FROM task VALUES (?, ?) WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setInt(3, task.getId());
            stmt.executeUpdate();

            try (ResultSet resultSet = stmt.getResultSet()) {
                return buildTaskFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            String msg = "Error updating task by id: " + task.getId();
            log.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    public Optional<Task> getById(Integer id) {
        String sql = "SELECT * FROM task WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeQuery();

            try (ResultSet resultSet = stmt.getResultSet()) {
                Task task = buildTaskFromResultSet(resultSet);
                return Optional.of(task);
            }

        } catch (SQLException e) {
            String msg = "Error getting task by id: " + id;
            log.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM task WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            String msg = "Error deleting task by id: " + id;
            log.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    public void done(Integer id, boolean done) {
        String sql = "UPDATE task SET done = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, done);
            stmt.setInt(2, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            String msg = "Error updating task done by id: " + id;
            log.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    public boolean existsById(Integer id) {
        String sql = "SELECT 1 FROM task WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet resultSet = stmt.executeQuery()) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            String msg = "Error checking if task exists by id: " + id;
            log.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    private List<Task> mapTasksFromResultSet(ResultSet resultSet)
            throws SQLException {

        List<Task> tasks = new ArrayList<>();

        while (resultSet.next()) {
            Task task = buildTaskFromResultSet(resultSet);
            tasks.add(task);
        }

        return tasks;
    }


    private Task buildTaskFromResultSet(ResultSet resultSet) throws SQLException {
        return new Task.Builder()
                .id(resultSet.getInt("id"))
                .title(resultSet.getString("title"))
                .description(resultSet.getString("description"))
                .done(resultSet.getBoolean("done"))
                .build();
    }

    private Task taskWithGeneratedId(Task task, ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            Integer id = resultSet.getInt(1);

            task = new Task.Builder()
                    .id(id)
                    .title(task.getTitle())
                    .description(task.getDescription())
                    .done(task.isDone())
                    .build();
        }

        return task;
    }
}
