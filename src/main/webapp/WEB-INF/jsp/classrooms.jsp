<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<h1>${title}</h1>
<p>
    <a href="classroom/add" class="button">Add Classroom</a>
</p>
<table>
    <thead>
    <tr>
        <th>Number</th>
        <th>Location</th>
        <th>Capacity</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="classroom" items="${classrooms}">
        <tr>
            <td>${classroom.number}</td>
            <td>${classroom.location}</td>
            <td>${classroom.capacity}</td>
            <td>
                <a href="classroom/edit?id=${classroom.id}">Edit</a><br/>
                <a href="classroom/delete?id=${classroom.id}">Delete</a><br/>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>