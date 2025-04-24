package com.leoduarte.service;

import com.leoduarte.dto.TaskRequestDTO;
import com.leoduarte.dto.TaskResponseDTO;
import com.leoduarte.dto.TaskUpdateDTO;

import java.util.List;

public interface TaskService {

    TaskResponseDTO save(TaskRequestDTO dto);
    TaskResponseDTO update(TaskUpdateDTO dto);
    List<TaskResponseDTO> getAll();
    TaskResponseDTO getById(Integer id);
    void delete(Integer id);
    void done(Integer id, boolean done);
}
