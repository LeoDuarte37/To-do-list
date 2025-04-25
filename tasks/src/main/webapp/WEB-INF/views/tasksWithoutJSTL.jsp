<!DOCTYPE html>
<%@ page import="java.util.List" %>
<%@ page import="com.leoduarte.dto.TaskResponseDTO" %>

<html>
    <head>
        <title>Tasks</title>
        <link rel="stylesheet" href="assets/css/style.css">
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