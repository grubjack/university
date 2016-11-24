<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 14.11.2016
  Time: 12:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${title}</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="shortcut icon" href="images/icon.png">
</head>
<body>
<form action="classrooms" method="post">
    <ul class="form-style-1">
        <input type="hidden" name="id" value="${fn:escapeXml(classroom.id)}"/>
        <li>
            <h2>${title}</h2>
        </li>
        <li>
            <label>Number</label>
            <input type="text" name="number" value="${fn:escapeXml(classroom.number)}" class="field-long" required/>
        </li>
        <li>
            <label>Location</label>
            <input type="text" name="location" value="${fn:escapeXml(classroom.location)}" class="field-long" required/>
        </li>
        <li>
            <label>Capacity</label>
            <input type="number" min="1" name="capacity" value="${fn:escapeXml(classroom.capacity)}"
                   class="field-long"
                   required/>
        </li>
        <li>
            <c:choose>
                <c:when test="${classroom.id == null}">
                    <input type="submit" value="Create"/>&nbsp;
                </c:when>
                <c:otherwise>
                    <input type="submit" value="Edit"/>&nbsp;
                </c:otherwise>
            </c:choose>
            <input type="reset" value="Reset"/>
        </li>
    </ul>
</form>
</body>
</html>
