<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${title}</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="shortcut icon" href="images/icon.png">
</head>
<body>
<p><a href="${pageContext.request.contextPath}/index.html" class="button">Home page</a></p>
<p><a href="${pageContext.request.contextPath}/departments" class="button">Departments</a></p>

<h1>${title}</h1>

<form action="search" method="post">
    <c:if test="${departmentId !=null}">
        <input type="hidden" name="did" value="${departmentId}"/>
    </c:if>
    <input type="hidden" name="entity" value="teacher"/>
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
        <a href="teachers?action=create&did=${departmentId}" class="button">Add Teacher</a>
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
            <td><a href="lessons?tid=${teacher.id}">show</a></td>
            <c:if test="${departmentId != null}">
                <td>
                    <a href="teachers?did=${departmentId}&action=edit&id=${teacher.id}">Edit</a><br/>
                    <a href="teachers?did=${departmentId}&action=delete&id=${teacher.id}">Delete</a><br/>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>