<!DOCTYPE html>
<%@ page import="java.util.List" %>
<%@ page import="com.leoduarte.dto.TaskResponseDTO" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
    <head>
        <title>Tasks</title>
        <link rel="stylesheet" href="assets/css/style.css">
        <script src="assets/js/script.js"></script>
    </head>
    <body>
        <%@ include file="../includes/header.jsp" %>

        <div id="container">
            <h1>To Do List</h1>

            <%@ include file="../includes/formNewTask.jsp" %>

            <c:if test="${not empty tasks}">
                <ul>
                    <c:forEach var="task" items="${tasks}">
                        <li>
                            <div id="content">
                                <input
                                    type="checkbox"
                                    onclick="toggleDone(${task.id}, ${task.done})"
                                    <c:if test="${task.done}">
                                        checked
                                    </c:if>
                                />
                                <span>${task.title}</span>
                            </div>
                            <div onclick="deleteTask(${task.id})">
                                X
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
        </div>

        <%@ include file="../includes/footer.jsp" %>
    </body>
</html>