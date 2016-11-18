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

<form action="search" method="post">
    <c:if test="${groupId !=null}">
        <input type="hidden" name="gid" value="${groupId}"/>
    </c:if>
    <input type="hidden" name="entity" value="student"/>
    <ul class="search">
        <li>
            <input type="text" name="name" placeholder="Name"/>
        </li>
        <li>
            <input type="submit" value="Search"/>
        </li>
    </ul>
</form>

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
            <td><a href="lessons?sid=${student.id}">show</a></td>
            <c:if test="${groupId != null}">
                <td>
                    <a href="students?gid=${groupId}&action=edit&id=${student.id}">Edit</a><br/>
                    <a href="students?gid=${groupId}&action=delete&id=${student.id}">Delete</a><br/>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>