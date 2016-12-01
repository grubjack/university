<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="fragments/head.jsp"/>
<body>
<p><a href="${pageContext.request.contextPath}/" class="button">Home page</a></p>
<p><a href="${pageContext.request.contextPath}/faculty/list" class="button">Faculties</a></p>

<h1>${title}</h1>

<c:choose>
    <c:when test="${facultyId != null}">
        <c:set value="department/search?fid=${facultyId}" var="action"/>
    </c:when>
    <c:otherwise>
        <c:set value="department/search" var="action"/>
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

<c:if test="${facultyId != null}">
    <p>
        <a href="department/add?fid=${facultyId}" class="button">Add Department</a>
    </p>
</c:if>
<table>
    <thead>
    <tr>
        <th>Name</th>
        <c:if test="${facultyId != null}">
            <th>Actions</th>
        </c:if>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="department" items="${departments}">
        <tr>
            <td><a href="teacher/list?did=${department.id}">${department.name}</a></td>
            <c:if test="${facultyId != null}">
                <td>
                    <a href="department/edit?id=${department.id}&fid=${facultyId}">Edit</a><br/>
                    <a href="department/delete?id=${department.id}&fid=${facultyId}">Delete</a><br/>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>