<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>List of Lessons</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<p><a href="${pageContext.request.contextPath}/index.html" class="button">Home page</a></p>

<h1>List of lessons</h1>
<table>
    <thead>
    <tr>
        <th>Day</th>
        <th>Time</th>
        <th>Subject</th>
        <th>Classroom</th>
        <th>Group</th>
        <th>Teacher</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="lesson" items="${lessons}">
        <tr>
            <td>${lesson.dayOfWeek}</td>
            <td>${lesson.timeOfDay.toString()}</td>
            <td>${lesson.subject}</td>
            <td>${lesson.classroom.number}</td>
            <td>${lesson.group.name}</td>
            <td>${lesson.teacher.name}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>