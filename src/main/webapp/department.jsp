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
    <title>Create new department</title>
</head>
<body>
<form action="departments" method="post">
    <input type="hidden" name="fid" value="${fn:escapeXml(facultyId)}"/>
    <input type="hidden" name="id" value="${fn:escapeXml(department.id)}"/>
    <label>Name</label>
    <input type="text" name="name" value="${fn:escapeXml(department.name)}"/>
    <input type="submit" value="Submit"/>
    <input type="reset" value="Reset"/>
    </div>
    </fieldset>
</form>
</body>
</html>
