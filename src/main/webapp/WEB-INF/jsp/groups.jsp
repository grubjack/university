<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<p><a href="${pageContext.request.contextPath}/faculty/list" class="button">Faculties</a></p>

<h1>${title}</h1>

<c:choose>
    <c:when test="${facultyId != null}">
        <c:set value="group/search?fid=${facultyId}" var="action"/>
    </c:when>
    <c:otherwise>
        <c:set value="group/search" var="action"/>
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
        <a href="group/add?fid=${facultyId}" class="button">Add Group</a>
    </p>
</c:if>
<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Timetable</th>
        <c:if test="${facultyId != null}">
            <th>Actions</th>
        </c:if>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="group" items="${groups}">
        <tr>
            <td><a href="student/list?gid=${group.id}">${group.name}</a></td>
            <td><a href="lesson/list?gid=${group.id}">show</a></td>
            <c:if test="${facultyId != null }">
                <td>
                    <a href="group/edit?id=${group.id}&fid=${facultyId}">Edit</a><br/>
                    <a href="group/delete?id=${group.id}&fid=${facultyId}">Delete</a><br/>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>