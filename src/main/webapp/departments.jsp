<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${title}</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<p><a href="${pageContext.request.contextPath}/index.html">Home page</a></p>
<p><a href="${pageContext.request.contextPath}/faculties">Faculties</a></p>

<h1>${title}</h1>
<c:if test="${facultyId != null}">
    <p>
        <a href="departments?action=create&fid=${facultyId}">Add Department</a>
    </p>
</c:if>
<table>
    <thead>
    <tr>
        <th>Name</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="department" items="${departments}">
        <tr>
            <td><a href="teachers?did=${department.id}">${department.name}</a></td>
            <c:if test="${facultyId != null}">
                <td>
                    <a href="departments?action=edit&fid=${facultyId}&id=${department.id}">Edit</a><br/>
                    <a href="departments?action=delete&fid=${facultyId}&id=${department.id}">Delete</a><br/>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>