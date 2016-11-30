<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="fragments/head.jsp"/>
</head>
<body>
<p><a href="${pageContext.request.contextPath}/index.html" class="button">Home page</a></p>
<p><a href="${pageContext.request.contextPath}/group/list" class="button">Groups</a></p>

<h1>${title}</h1>

<c:choose>
    <c:when test="${groupId != null}">
        <c:set value="student/search?gid=${groupId}" var="action"/>
    </c:when>
    <c:otherwise>
        <c:set value="student/search" var="action"/>
    </c:otherwise>
</c:choose>


<form action="${action}" method="post">
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
        <a href="student/add?gid=${groupId}" class="button">Add Student</a>
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
            <td><a href="lesson/list?sid=${student.id}">show</a></td>
            <c:if test="${groupId != null}">
                <td>
                    <a href="student/edit?id=${student.id}&gid=${groupId}">Edit</a><br/>
                    <a href="student/delete?id=${student.id}&gid=${groupId}">Delete</a><br/>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>