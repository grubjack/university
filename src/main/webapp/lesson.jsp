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
    <label>Group</label>
    <c:choose>
        <c:when test="${selectedGroup!=null}">
            <select name="group">
                <option value="${selectedGroup.id}" selected>${selectedGroup.name}</option>
            </select>
        </c:when>
        <c:when test="${facultyGroups!=null}">
            <select name="group">
                <c:forEach var="group" items="${facultyGroups}">
                    <option value="${group.id}">${group.name}</option>
                </c:forEach>
            </select>
        </c:when>
        <c:otherwise>
            <select name="group">
                <option value="${lesson.group.id}" selected>${lesson.group.name}</option>
                <c:forEach var="group" items="${groups}">
                    <c:if test="${group.id != lesson.group.id}">
                        <option value="${group.id}">${group.name}</option>
                    </c:if>
                </c:forEach>
            </select>
        </c:otherwise>
    </c:choose>

    <label>Teacher</label>
    <c:choose>
        <c:when test="${selectedTeacher != null}">
            <select name="teacher">
                <option value="${selectedTeacher.id}" selected>${selectedTeacher.name}</option>
            </select>
        </c:when>
        <c:otherwise>
            <select name="teacher">
                <option value="${lesson.teacher.id}" selected>${lesson.teacher.name}</option>
                <c:forEach var="teacher" items="${teachers}">
                    <c:if test="${teacher.id != lesson.teacher.id}">
                        <option value="${teacher.id}">${teacher.name}</option>
                    </c:if>
                </c:forEach>
            </select>
        </c:otherwise>
    </c:choose>
    </select>

    <input type="submit" value="Submit"/>
    <input type="reset" value="Reset"/>
    </div>
    </fieldset>
</form>
</body>
</html>
