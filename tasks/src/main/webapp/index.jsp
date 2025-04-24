<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="com.leoduarte.dto.TaskResponseDTO" %>

<html>
    <head>
        <title>Home</title>
    </head>
    <body>
        <% response.sendRedirect(request.getContextPath() + "/task"); %>
    </body>
</html>
