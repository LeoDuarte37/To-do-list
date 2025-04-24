<!DOCTYPE html>
<%@ page import="java.util.List" %>
<%@ page import="com.leoduarte.dto.TaskResponseDTO" %>

<html>
    <head>
        <title>Tasks</title>
        <style>
            body {
                width: 100%;
                height: 100vh;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                margin: 0;
                background-color: #f0f0f0;
            }

            #main {
                display: flex;
                gap: 2rem;
            }

            #container {
                width: 30rem;
                display: flex;
                flex-direction: row;
                align-items: center;
                justify-content: center;
                gap: 2rem;
                background-color: #f0e8e6;
                padding: 2rem;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            form {
                width: 100%;
            }

            input[type="text"], input[type="submit"] {
                width: 100%;
                padding: 0.8rem;
                margin-bottom: 1rem;
                border: 1px solid #ccc;
                border-radius: 5px;
            }

            button[type="submit"] {
                width: 100%;
                padding: 0.8rem;
                background-color: #4CAF50;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 1rem;
            }

            button[type="submit"]:hover {
                background-color: #45a049;
            }

            tr, th, td {
                padding: 10px;
            }
        </style>
    </head>
    <body>
        <%@ include file="../includes/header.jsp" %>

        <h1>To Do List</h1>

        <div id="main">
            <div id="container">
                <table border="1">
                    <tr>
                        <th>Id</th>
                        <th>Title</th>
                        <th>Description</th>
                        <th>Done</th>
                    </tr>

                    <%
                        List<TaskResponseDTO> tasks = (List<TaskResponseDTO>) request.getAttribute("tasks");
                        if (tasks != null) {
                            for (TaskResponseDTO task : tasks) {
                    %>
                            <tr>
                                <td><%= task.id() %></td>
                                <td><%= task.title() %></td>
                                <td><%= task.description() %></td>
                                <td><%= task.done() %></td>
                            </tr>
                    <%
                            }
                        }
                    %>
                </table>
            </div>
            <div>
                <%@ include file="formNewTask.jsp" %>
            </div>
        </div>

        <%@ include file="../includes/footer.jsp" %>
    </body>
</html>