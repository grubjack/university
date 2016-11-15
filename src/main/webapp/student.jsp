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
    <title>Create new student</title>
</head>
<body>
<form action="students" method="post">
    <input type="hidden" name="gid" value="${fn:escapeXml(groupId)}"/>
    <input type="hidden" name="id" value="${fn:escapeXml(student.id)}"/>
    <label>Lastname</label>
    <input type="text" name="lastname" value="${fn:escapeXml(student.lastName)}"/>
    <label>Firstname</label>
    <input type="text" name="firstname" value="${fn:escapeXml(student.firstName)}"/>
    <input type="submit" value="Submit"/>
    <input type="reset" value="Reset"/>
    </div>
    </fieldset>
</form>
</body>
</html>
