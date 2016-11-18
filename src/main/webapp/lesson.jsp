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
    <c:when test="${sid !=null}">
        <c:set value="lessons?sid=${sid}" var="action"/>
    </c:when>
    <c:when test="${gid !=null}">
        <c:set value="lessons?gid=${gid}" var="action"/>
    </c:when>
    <c:when test="${tid !=null}">
        <c:set value="lessons?tid=${tid}" var="action"/>
    </c:when>
    <c:when test="${fid !=null}">
        <c:set value="lessons?fid=${fid}" var="action"/>
    </c:when>
    <c:otherwise>
        <c:set value="lessons" var="action"/>
    </c:otherwise>
</c:choose>


<form action="${action}" method="post">
    <ul class="form-style-1">
        <input type="hidden" name="sid" value="${fn:escapeXml(sid)}"/>
        <input type="hidden" name="gid" value="${fn:escapeXml(gid)}"/>
        <input type="hidden" name="tid" value="${fn:escapeXml(tid)}"/>
        <input type="hidden" name="fid" value="${fn:escapeXml(fid)}"/>
        <input type="hidden" name="id" value="${fn:escapeXml(lesson.id)}"/>
        <input type="hidden" name="day" value="${fn:escapeXml(day)}"/>
        <input type="hidden" name="time" value="${fn:escapeXml(time)}"/>
        <li>
            <h2>${title}</h2>
        </li>
        <li>
            <label>Subject </label>
            <input type="text" name="subject" value="${fn:escapeXml(lesson.subject)}" class="field-long" required/>
        </li>
        <c:if test="${roomNotification !=null}">
            <div class="required_notification">
                *${roomNotification}
            </div>
        </c:if>
        <li>
            <label>Classroom </label>
            <select name="classroom" class="field-select" required>
                <option value="${lesson.classroom.id}" selected>${lesson.classroom.number}</option>
                <c:forEach var="room" items="${rooms}">
                    <c:if test="${room.id != lesson.classroom.id}">
                        <option value="${room.id}">${room.number}</option>
                    </c:if>
                </c:forEach>
            </select>
        </li>
        <c:if test="${groupNotification !=null}">
            <div class="required_notification">
                *${groupNotification}
            </div>
        </c:if>
        <li>
            <label>Group </label>
            <select name="group" class="field-select" required>
                <c:choose>
                    <c:when test="${selectedGroup!=null}">
                        <option value="${selectedGroup.id}" selected>${selectedGroup.name}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${lesson.group.id}" selected>${lesson.group.name}</option>
                        <c:forEach var="group" items="${groups}">
                            <c:if test="${group.id != lesson.group.id}">
                                <option value="${group.id}">${group.name}</option>
                            </c:if>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </select>
        </li>
        <c:if test="${teacherNotification !=null}">
            <div class="required_notification">
                *${teacherNotification}
            </div>
        </c:if>
        <li>
            <label>Teacher </label>
            <select name="teacher" class="field-select" required>
                <c:choose>
                    <c:when test="${selectedTeacher != null}">
                        <option value="${selectedTeacher.id}" selected>${selectedTeacher.name}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${lesson.teacher.id}" selected>${lesson.teacher.name}</option>
                        <c:forEach var="teacher" items="${teachers}">
                            <c:if test="${teacher.id != lesson.teacher.id}">
                                <option value="${teacher.id}">${teacher.name}</option>
                            </c:if>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </select>
        </li>
        <li>
            <c:choose>
                <c:when test="${lesson.id == null}">
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
