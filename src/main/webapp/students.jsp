<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>List of Students</title>
</head>
<body>
<p><a href="${pageContext.request.contextPath}/index.html">Home page</a></p>

<h1>List of students</h1>
<table border="2px" cellpadding="1" cellspacing="1">
    <thead>
    <tr>
        <th width="10%">Id</th>
        <th width="30%">Firstname</th>
        <th width="30%">Lastname</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="student" items="${students}">
        <tr align="center">
            <td>${student.id}</td>
            <td>${student.firstName}</td>
            <td>${student.lastName}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>