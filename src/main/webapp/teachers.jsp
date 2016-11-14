<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>List of Teachers</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<p><a href="${pageContext.request.contextPath}/index.html">Home page</a></p>
<p><a href="${pageContext.request.contextPath}/departments">Departments</a></p>

<h1>List of teachers</h1>
<table>
    <thead>
    <tr>
        <th>Lastname</th>
        <th>Firstname</th>
        <th>Salary</th>
        <th>Timetable</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="teacher" items="${teachers}">
        <tr>
            <td>${teacher.lastName}</td>
            <td>${teacher.firstName}</td>
            <td>${teacher.salary}</td>
            <td><a href="timetable?tid=${teacher.getId()}">show</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>