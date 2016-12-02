<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>

<c:choose>
    <c:when test="${teacher.id == 0}">
        <c:set value="teacher/add?did=${departmentId}" var="action"/>
    </c:when>
    <c:otherwise>
        <c:set value="teacher/edit?id=${teacher.id}&did=${departmentId}" var="action"/>
    </c:otherwise>
</c:choose>

<form action="${action}" method="post">
    <ul class="form-style-1">
        <li>
            <h2>${title}</h2>
        </li>
        <li>
            <label>Lastname</label>
            <input type="text" name="lastName" value="${fn:escapeXml(teacher.lastName)}" class="field-long" required/>
        </li>
        <li>
            <label>Firstname</label>
            <input type="text" name="firstName" value="${fn:escapeXml(teacher.firstName)}" class="field-long" required/>
        </li>
        <li>
            <label>Salary</label>
            <input type="number" min="10" name="salary" value="${fn:escapeXml(teacher.salary)}" class="field-long"
                   required/>
        </li>
        <li>
            <c:choose>
                <c:when test="${teacher.id == 0}">
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
