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
<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Timetable</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="group" items="${groups}">
        <tr>
            <td><a href="students?id=${group.getId()}">${group.name}</a></td>
            <td><a href="timetable?gid=${group.getId()}">show</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>