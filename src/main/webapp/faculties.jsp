<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>List of Faculties</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<p><a href="${pageContext.request.contextPath}/index.html" class="button">Home page</a></p>

<h1>List of faculties</h1>
<p>
    <a href="faculties?action=create" class="button">Add Faculty</a>
</p>
<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Groups</th>
        <th>Departments</th>
        <th>Timetable</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="faculty" items="${faculties}">
        <tr>
            <td>${faculty.name}</td>
            <td><a href="groups?fid=${faculty.id}">show</a></td>
            <td><a href="departments?fid=${faculty.id}">show</a></td>
            <td><a href="timetable?fid=${faculty.id}">show</a></td>
            <td>
                <a href="faculties?action=edit&id=${faculty.id}">Edit</a><br/>
                <a href="faculties?action=delete&id=${faculty.id}">Delete</a><br/>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>