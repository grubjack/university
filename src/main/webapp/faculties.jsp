<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>List of Faculties</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<p><a href="${pageContext.request.contextPath}/index.html">Home page</a></p>

<h1>List of faculties</h1>
<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Groups</th>
        <th>Departments</th>
        <th>Timetable</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="faculty" items="${faculties}">
        <tr>
            <td>${faculty.name}</td>
            <td><a href="groups?id=${faculty.getId()}">show</a></td>
            <td><a href="departments?id=${faculty.getId()}">show</a></td>
            <td><a href="timetable?fid=${faculty.getId()}">show</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>