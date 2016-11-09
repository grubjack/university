<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>List of Groups</title>
</head>
<body>
<p><a href="${pageContext.request.contextPath}/index.html">Home page</a></p>

<h1>List of groups</h1>
<table border="2px" cellpadding="1" cellspacing="1">
    <thead>
    <tr>
        <th width="30%">Id</th>
        <th width="30%">Name</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="group" items="${groups}">
        <tr align="center">
            <td>${group.id}</td>
            <td>${group.name}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>