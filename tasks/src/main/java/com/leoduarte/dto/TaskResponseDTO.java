package com.leoduarte.dto;

public record TaskResponseDTO (

        Integer id,
        String title,
        String description,
        boolean done
    ) {
}
