<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>

<c:choose>
    <c:when test="${lesson.id == 0}">
        <c:choose>
            <c:when test="${studentId != null}">
                <c:set value="lesson/add?sid=${studentId}" var="action"/>
            </c:when>
            <c:when test="${groupId != null}">
                <c:set value="lesson/add?gid=${groupId}" var="action"/>
            </c:when>
            <c:when test="${teacherId != null}">
                <c:set value="lesson/add?tid=${teacherId}" var="action"/>
            </c:when>
            <c:when test="${facultyId != null}">
                <c:set value="lesson/add?fid=${facultyId}" var="action"/>
            </c:when>
        </c:choose>
    </c:when>
    <c:otherwise>
        <c:choose>
            <c:when test="${studentId != null}">
                <c:set value="lesson/edit?id=${lesson.id}&sid=${studentId}" var="action"/>
            </c:when>
            <c:when test="${groupId != null}">
                <c:set value="lesson/edit?id=${lesson.id}&gid=${groupId}" var="action"/>
            </c:when>
            <c:when test="${teacherId != null}">
                <c:set value="lesson/edit?id=${lesson.id}&tid=${teacherId}" var="action"/>
            </c:when>
            <c:when test="${facultyId != null}">
                <c:set value="lesson/edit?id=${lesson.id}&fid=${facultyId}" var="action"/>
            </c:when>
        </c:choose>
    </c:otherwise>
</c:choose>

<form action="${action}" method="post">
    <ul class="form-style-1">
        <li>
            <h2>${title}</h2>
        </li>
        <li>
            <label>Subject </label>
            <input type="text" name="subject" value="${fn:escapeXml(lesson.subject)}" class="field-long" required/>
        </li>
        <li>
            <label>Day</label>
            <select name="dayOfWeek" class="field-select disabled">
                <option value="${lesson.dayOfWeek}">${lesson.dayOfWeek.toString()}</option>
            </select>
        </li>
        <li>
            <label>Time</label>
            <select name="timeOfDay" class="field-select disabled">
                <option value="${lesson.timeOfDay.toString()}">${lesson.timeOfDay.toString()}</option>
            </select>
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
            <c:choose>
                <c:when test="${teacherId != null}">
                    <select name="group" class="field-select" required>
                        <option value="${lesson.group.id}" selected>${lesson.group.name}</option>
                        <c:forEach var="group" items="${groups}">
                            <c:if test="${group.id != lesson.group.id}">
                                <option value="${group.id}">${group.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </c:when>
                <c:otherwise>
                    <select name="group" class="field-select disabled">
                        <option value="${lesson.group.id}">${lesson.group.name}</option>
                    </select>
                </c:otherwise>
            </c:choose>
        </li>
        <c:if test="${teacherNotification !=null}">
            <div class="required_notification">
                *${teacherNotification}
            </div>
        </c:if>
        <li>
            <label>Teacher </label>
            <c:choose>
                <c:when test="${teacherId != null}">
                    <select name="teacher" class="field-select disabled">
                        <option value="${lesson.teacher.id}">${lesson.teacher.name}</option>
                    </select>
                </c:when>
                <c:otherwise>
                    <select name="teacher" class="field-select" required>
                        <option value="${lesson.teacher.id}" selected>${lesson.teacher.name}</option>
                        <c:forEach var="teacher" items="${teachers}">
                            <c:if test="${teacher.id != lesson.teacher.id}">
                                <option value="${teacher.id}">${teacher.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </c:otherwise>
            </c:choose>
        </li>
        <li>
            <c:choose>
                <c:when test="${lesson.id == 0}">
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
