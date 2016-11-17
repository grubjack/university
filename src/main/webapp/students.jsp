<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${title}</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<p><a href="${pageContext.request.contextPath}/index.html" class="button">Home page</a></p>
<p><a href="${pageContext.request.contextPath}/groups" class="button">Groups</a></p>

<h1>${title}</h1>
<c:if test="${groupId != null}">
    <p>
        <a href="students?action=create&gid=${groupId}" class="button">Add Student</a>
    </p>
</c:if>
<table>
    <thead>
    <tr>
        <th>Lastname</th>
        <th>Firstname</th>
        <th>Timetable</th>
        <c:if test="${groupId != null}">
            <th>Actions</th>
        </c:if>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="student" items="${students}">
        <tr>
            <td>${student.lastName}</td>
            <td>${student.firstName}</td>
            <td><a href="timetable?sid=${student.id}">show</a></td>
            <c:if test="${groupId != null}">
                <td>
                    <a href="students?action=edit&gid=${groupId}&id=${student.id}">Edit</a><br/>
                    <a href="students?action=delete&gid=${groupId}&id=${student.id}">Delete</a><br/>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>