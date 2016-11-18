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
<p><a href="${pageContext.request.contextPath}/faculties" class="button">Faculties</a></p>

<h1>${title}</h1>

<form action="search" method="post">
    <input type="hidden" name="entity" value="group"/>
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
        <a href="groups?action=create&fid=${facultyId}" class="button">Add Group</a>
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
            <td><a href="students?gid=${group.id}">${group.name}</a></td>
            <td><a href="lessons?gid=${group.id}">show</a></td>
            <c:if test="${facultyId != null}">
                <td>
                    <a href="groups?fid=${facultyId}&action=edit&id=${group.id}">Edit</a><br/>
                    <a href="groups?fid=${facultyId}&action=delete&id=${group.id}">Delete</a><br/>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>