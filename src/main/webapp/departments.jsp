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
<p><a href="${pageContext.request.contextPath}/faculties" class="button">Faculties</a></p>

<h1>${title}</h1>

<form action="search" method="post">
    <c:if test="${facultyId !=null}">
        <input type="hidden" name="fid" value="${facultyId}"/>
    </c:if>
    <input type="hidden" name="entity" value="department"/>
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
        <a href="departments?action=create&fid=${facultyId}" class="button">Add Department</a>
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
            <td><a href="teachers?did=${department.id}">${department.name}</a></td>
            <c:if test="${facultyId != null}">
                <td>
                    <a href="departments?fid=${facultyId}&action=edit&id=${department.id}">Edit</a><br/>
                    <a href="departments?fid=${facultyId}&action=delete&id=${department.id}">Delete</a><br/>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>