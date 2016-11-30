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
<jsp:include page="fragments/head.jsp"/>
<body>


<c:choose>
    <c:when test="${group.id == 0}">
        <c:set value="group/add?fid=${facultyId}" var="action"/>
    </c:when>
    <c:otherwise>
        <c:set value="group/edit?id=${group.id}&fid=${facultyId}" var="action"/>
    </c:otherwise>
</c:choose>


<form action="${action}" method="post">
    <ul class="form-style-1">
        <li>
            <h2>${title}</h2>
        </li>
        <li>
            <label>Name</label>
            <input type="text" name="name" value="${fn:escapeXml(group.name)}" class="field-long" required/>
        </li>
        <li>
            <c:choose>
                <c:when test="${group.id == 0}">
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
