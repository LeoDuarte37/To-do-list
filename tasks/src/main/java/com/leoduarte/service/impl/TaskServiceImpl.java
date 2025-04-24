package com.leoduarte.service.impl;

import com.leoduarte.dao.TaskDAO;
import com.leoduarte.dto.TaskRequestDTO;
import com.leoduarte.dto.TaskResponseDTO;
import com.leoduarte.dto.TaskUpdateDTO;
import com.leoduarte.mapper.TaskMapper;
import com.leoduarte.model.Task;
import com.leoduarte.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService {

    private final TaskDAO taskDAO;

    private static final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    public TaskServiceImpl(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @Override
    public TaskResponseDTO save(TaskRequestDTO dto) {
        Task task = TaskMapper.toEntity(dto);
        Task saved = taskDAO.save(task);
        return TaskMapper.toResponseDTO(saved);
    }

    @Override
    public List<TaskResponseDTO> getAll() {
        List<Task> tasks = taskDAO.getAll();
        return tasks.stream()
                .map(TaskMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponseDTO update(TaskUpdateDTO dto) {

        if (!taskDAO.existsById(dto.id())) {
            String msg = "Unable to update task. Not found by id: " + dto.id();
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }

        Task task = new Task.Builder()
                .id(dto.id())
                .title(dto.title())
                .description(dto.description())
                .build();

        task = taskDAO.update(task);
        return TaskMapper.toResponseDTO(task);
    }

    @Override
    public TaskResponseDTO getById(Integer id) {
        return taskDAO.getById(id)
                .map(TaskMapper::toResponseDTO)
                .orElseThrow(() -> {
                    String msg = "Unable to get task. Not found by id: " + id;
                    log.error(msg);
                    return new IllegalArgumentException(msg);
                });
    }

    @Override
    public void delete(Integer id) {
        if (!taskDAO.existsById(id)) {
            String msg = "Unable to delete task. Not found by id: " + id;
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }

        taskDAO.delete(id);
    }

    @Override
    public void done(Integer id, boolean done) {
        if (!taskDAO.existsById(id)) {
            String msg = "Unable to mark task as completed. Not found by id: " + id;
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }

        taskDAO.done(id, done);
    }
}
