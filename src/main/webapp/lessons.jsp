<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>List of Lessons</title>
</head>
<body>
<p><a href="${pageContext.request.contextPath}/index.html">Home page</a></p>

<h1>List of lessons</h1>
<table border="2px" cellpadding="1" cellspacing="1">
    <thead>
    <tr>
        <th width="10%">Id</th>
        <th width="30%">Subject</th>
        <th width="30%">Day</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="lesson" items="${lessons}">
        <tr align="center">
            <td>${lesson.id}</td>
            <td>${lesson.subject}</td>
            <td>${lesson.dayOfWeek}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>