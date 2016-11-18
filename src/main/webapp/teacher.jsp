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
</head>
<body>

<c:choose>
    <c:when test="${departmentId !=null}">
        <c:set value="teachers?did=${departmentId}" var="action"/>
    </c:when>
    <c:otherwise>
        <c:set value="teachers" var="action"/>
    </c:otherwise>
</c:choose>

<form action="${action}" method="post">
    <ul class="form-style-1">
        <input type="hidden" name="did" value="${fn:escapeXml(departmentId)}"/>
        <input type="hidden" name="id" value="${fn:escapeXml(teacher.id)}"/>
        <li>
            <h2>${title}</h2>
        </li>
        <li>
            <label>Lastname</label>
            <input type="text" name="lastname" value="${fn:escapeXml(teacher.lastName)}" class="field-long" required/>
        </li>
        <li>
            <label>Firstname</label>
            <input type="text" name="firstname" value="${fn:escapeXml(teacher.firstName)}" class="field-long" required/>
        </li>
        <li>
            <label>Salary</label>
            <input type="number" min="10" name="salary" value="${fn:escapeXml(teacher.salary)}" class="field-long"
                   required/>
        </li>
        <li>
            <c:choose>
                <c:when test="${teacher.id == null}">
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
