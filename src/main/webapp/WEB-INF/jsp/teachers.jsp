<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="fragments/head.jsp"/>
<body>
<p><a href="${pageContext.request.contextPath}/" class="button">Home page</a></p>
<p><a href="${pageContext.request.contextPath}/department/list" class="button">Departments</a></p>

<h1>${title}</h1>

<c:choose>
    <c:when test="${departmentId != null}">
        <c:set value="teacher/search?did=${departmentId}" var="action"/>
    </c:when>
    <c:otherwise>
        <c:set value="teacher/search" var="action"/>
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

<c:if test="${departmentId != null}">
    <p>
        <a href="teacher/add?did=${departmentId}" class="button">Add Teacher</a>
    </p>
</c:if>
<table>
    <thead>
    <tr>
        <th>Lastname</th>
        <th>Firstname</th>
        <th>Salary</th>
        <th>Timetable</th>
        <c:if test="${departmentId != null}">
            <th>Actions</th>
        </c:if>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="teacher" items="${teachers}">
        <tr>
            <td>${teacher.lastName}</td>
            <td>${teacher.firstName}</td>
            <td>${teacher.salary}</td>
            <td><a href="lesson/list?tid=${teacher.id}">show</a></td>
            <c:if test="${departmentId != null}">
                <td>
                    <a href="teacher/edit?id=${teacher.id}&did=${departmentId}">Edit</a><br/>
                    <a href="teacher/delete?id=${teacher.id}&did=${departmentId}">Delete</a><br/>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>