package com.leoduarte;

import com.leoduarte.model.Task;

public class TasksApplication {

    public static void main(String[] args) {

        Task task = new Task.Builder()
                .id(1)
                .title("Estudar Java")
                .description("Estudar")
                .done(false)
                .build();
    }
}
