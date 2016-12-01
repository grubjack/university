<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="fragments/head.jsp"/>
<body>
<p><a href="${pageContext.request.contextPath}/" class="button">Home page</a></p>

<h1>${title}</h1>

<form action="faculty/search" method="post">
    <ul class="search">
        <li>
            <input type="text" name="name" placeholder="Name"/>
        </li>
        <li>
            <input type="submit" value="Search"/>
        </li>
    </ul>
</form>

<p>
    <a href="faculty/add" class="button">Add Faculty</a>
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
            <td><a href="group/list?fid=${faculty.id}">show</a></td>
            <td><a href="department/list?fid=${faculty.id}">show</a></td>
            <td><a href="lesson/list?fid=${faculty.id}">show</a></td>
            <td>
                <a href="faculty/edit?id=${faculty.id}">Edit</a><br/>
                <a href="faculty/delete?id=${faculty.id}">Delete</a><br/>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>