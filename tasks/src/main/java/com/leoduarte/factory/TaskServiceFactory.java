package com.leoduarte.factory;

import com.leoduarte.config.ConnectionPool;
import com.leoduarte.dao.TaskDAO;
import com.leoduarte.service.TaskService;
import com.leoduarte.service.impl.TaskServiceImpl;

import java.sql.Connection;

public class TaskServiceFactory {

    private static TaskService taskService;

    public static synchronized TaskService getInstance() {
        if (taskService == null) {
            Connection conn = ConnectionPool.getInstance().getConnection();
            TaskDAO taskDAO = new TaskDAO(conn);
            taskService = new TaskServiceImpl(taskDAO);
        }

        return taskService;
    }
}
