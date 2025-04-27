package com.leoduarte.servlet;

import com.leoduarte.dto.TaskRequestDTO;
import com.leoduarte.dto.TaskResponseDTO;
import com.leoduarte.dto.TaskUpdateDTO;
import com.leoduarte.factory.TaskServiceFactory;
import com.leoduarte.service.TaskService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class TaskServlet extends HttpServlet {

    private TaskService taskService;

    private static final Logger log = LoggerFactory.getLogger(TaskServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            taskService = TaskServiceFactory.getInstance();
        } catch (Exception e) {
            throw new ServletException("Erro ao iniciar TaskService", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getRequestURI().substring(req.getContextPath().length());

        if (path.equals("/task")) {
            handleSave(req, resp);
        } else {
            badRequest(resp, path);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getRequestURI().substring(req.getContextPath().length());

        if (path.equals("/task")) {
            handleGetAll(req, resp);
        } else if (path.matches("^/task/\\d+$")) {
            Integer id = Integer.parseInt(path.substring("/task/".length()));
            handleGetById(req, resp, id);
        } else {
            badRequest(resp, path);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getRequestURI().substring(req.getContextPath().length());

        if (path.equals("/task")) {
            handleDone(req, resp);
        } else if (path.equals("/task/update")) {
            handleUpdate(req, resp);
        } else {
            badRequest(resp, path);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getRequestURI().substring(req.getContextPath().length());

        if (path.matches("^/task/\\d+$")) {
            handleDelete(req, resp);
        } else {
            badRequest(resp, path);
        }
    }

    private void handleSave(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        TaskRequestDTO taskRequest = new TaskRequestDTO(title, description);

        TaskResponseDTO taskSaved = taskService.save(taskRequest);
        req.setAttribute("taskSaved", taskSaved);

        handleGetAll(req, resp);
    }

    private void handleGetAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<TaskResponseDTO> tasks = taskService.getAll();
        req.setAttribute("tasks", tasks);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/tasks.jsp");
        dispatcher.forward(req, resp);
    }

    private void handleGetById(HttpServletRequest req, HttpServletResponse resp, Integer id) throws ServletException, IOException {
        TaskResponseDTO taskResponse = taskService.getById(id);
        req.setAttribute("taskById", taskResponse);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/taskById.jsp");
        dispatcher.forward(req, resp);
    }

    private void handleDone(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        String doneParam = req.getParameter("done");

        if (idParam != null & doneParam != null) {
            Integer id = Integer.parseInt(idParam);
            boolean done = Boolean.parseBoolean(doneParam);
            taskService.done(id, done);

            resp.setStatus(204);
        } else {
            resp.sendError(400, "Invalid parameters!");
        }
    }

    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        TaskUpdateDTO taskUpdate = new TaskUpdateDTO(id, title, description);

        TaskResponseDTO taskResponse = taskService.update(taskUpdate);
        req.setAttribute("taskUpdated", taskResponse);

        // RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        // dispatcher.forward(req, resp);
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        taskService.delete(id);

        handleGetAll(req, resp);
    }

    private void badRequest(HttpServletResponse resp, String path) throws IOException {
        log.warn("Bad Request. Invalid Path: {}", path);
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Action");
    }
}
