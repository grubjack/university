<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>List of Classrooms</title>
</head>
<body>
<p><a href="${pageContext.request.contextPath}/index.html">Home page</a></p>

<h1>List of classrooms</h1>
<table border="2px" cellpadding="1" cellspacing="1">
    <thead>
    <tr>
        <th width="10%">Id</th>
        <th width="10%">Number</th>
        <th width="10%">Location</th>
        <th width="10%">Capacity</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="classroom" items="${classrooms}">
        <tr align="center">
            <td>${classroom.id}</td>
            <td>${classroom.number}</td>
            <td>${classroom.location}</td>
            <td>${classroom.capacity}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>