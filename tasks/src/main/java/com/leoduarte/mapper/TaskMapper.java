package com.leoduarte.mapper;

import com.leoduarte.dto.TaskRequestDTO;
import com.leoduarte.dto.TaskResponseDTO;
import com.leoduarte.dto.TaskUpdateDTO;
import com.leoduarte.model.Task;

public class TaskMapper {

    public static Task toEntity(TaskRequestDTO dto) {
        return new Task.Builder()
                .title(dto.title())
                .description(dto.description())
                .done(false)
                .build();
    }

    public static Task toEntity(TaskUpdateDTO dto) {
        return new Task.Builder()
                .title(dto.title())
                .description(dto.description())
                .build();
    }

    public static TaskResponseDTO toResponseDTO(Task entity) {
        return new TaskResponseDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.isDone()
        );
    }
}
