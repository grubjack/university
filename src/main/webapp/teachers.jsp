<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>List of Teachers</title>
</head>
<body>
<p><a href="${pageContext.request.contextPath}/index.html">Home page</a></p>

<h1>List of teachers</h1>
<table border="2px" cellpadding="1" cellspacing="1">
    <thead>
    <tr>
        <th width="10%">Id</th>
        <th width="30%">Firstname</th>
        <th width="30%">Lastname</th>
        <th width="10%">Salary</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="teacher" items="${teachers}">
        <tr align="center">
            <td>${teacher.id}</td>
            <td>${teacher.firstName}</td>
            <td>${teacher.lastName}</td>
            <td>${teacher.salary}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>