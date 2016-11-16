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
    <title>Create new lesson</title>
</head>
<body>
<form action="lessons" method="post">
    <input type="hidden" name="sid" value="${fn:escapeXml(sid)}"/>
    <input type="hidden" name="gid" value="${fn:escapeXml(gid)}"/>
    <input type="hidden" name="tid" value="${fn:escapeXml(tid)}"/>
    <input type="hidden" name="fid" value="${fn:escapeXml(fid)}"/>
    <input type="hidden" name="id" value="${fn:escapeXml(lesson.id)}"/>
    <input type="hidden" name="day" value="${fn:escapeXml(day)}"/>
    <input type="hidden" name="time" value="${fn:escapeXml(time)}"/>
    <input type="hidden" name="groupName" value="${fn:escapeXml(groupName)}"/>
    <label>Subject</label>
    <input type="text" name="subject" value="${fn:escapeXml(lesson.subject)}"/>
    <label>Classroom</label>
    <select name="classroom">
        <option value="${lesson.classroom.id}" selected>${lesson.classroom.number}</option>
        <c:forEach var="room" items="${rooms}">
            <c:if test="${room.id != lesson.classroom.id}">
                <option value="${room.id}">${room.number}</option>
            </c:if>
        </c:forEach>
    </select>
    <label>Teacher</label>
    <select name="teacher">
        <option value="${lesson.teacher.id}" selected>${lesson.teacher.name}</option>
        <c:forEach var="teacher" items="${teachers}">
            <c:if test="${teacher.id != lesson.teacher.id}">
                <option value="${teacher.id}">${teacher.name}</option>
            </c:if>
        </c:forEach>
    </select>
    <input type="submit" value="Submit"/>
    <input type="reset" value="Reset"/>
    </div>
    </fieldset>
</form>
</body>
</html>