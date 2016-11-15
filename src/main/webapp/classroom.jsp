<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 14.11.2016
  Time: 12:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Create new classroom</title>
</head>
<body>
<form action="classrooms" method="post">
    <input type="hidden" name="id" value="${fn:escapeXml(classroom.id)}"/>
    <label>Number</label>
    <input type="text" name="number" value="${fn:escapeXml(classroom.number)}"/>
    <label>Location</label>
    <input type="text" name="location" value="${fn:escapeXml(classroom.location)}"/>
    <label>Capacity</label>
    <input type="text" name="capacity" value="${fn:escapeXml(classroom.capacity)}"/>
    <input type="submit" value="Submit"/>
    <input type="reset" value="Reset"/>
    </div>
    </fieldset>
</form>
</body>
</html>
