<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="fragments/head.jsp"/>
<body>
<p><a href="${pageContext.request.contextPath}/index.html" class="button">Home page</a></p>
<c:if test="${home != null }">
    <p><a href="${pageContext.request.contextPath}/${home}" class="button">${homeTitle}</a></p>
</c:if>

<h1>${title}</h1>
<table>
    <tr>
        <td/>
        <td/>
        <c:forEach var="timetable" items="${timetables}">
            <th>${timetable.name}</th>
        </c:forEach>
    </tr>
    <c:forEach var="day" items="${days}">
        <tr>
        <th rowspan="7">${day}</th>
        <c:forEach var="time" items="${times}">
            <tr>
                <th>${time}</th>
                <c:forEach var="timetable" items="${timetables}">
                    <c:set value="${timetable.findLesson(day,time)}" var="lesson"/>
                    <td>
                        <c:choose>
                            <c:when test="${lesson != null}">
                                ${lesson.subject}<br>
                                ${lesson.classroom.number}<br>
                                <c:choose>
                                    <c:when test="${teacherId != null}">
                                        ${lesson.group.name}
                                    </c:when>
                                    <c:otherwise>
                                        ${lesson.teacher.name}
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${teacherId != null}">
                                        <br><br>
                                        <a href="lesson/edit?id=${lesson.id}&tid=${teacherId}">edit</a>
                                        <a href="lesson/delete?id=${lesson.id}&tid=${teacherId}">delete</a>
                                    </c:when>
                                    <c:when test="${groupId != null}">
                                        <br><br>
                                        <a href="lesson/edit?id=${lesson.id}&gid=${groupId}">edit</a>
                                        <a href="lesson/delete?id=${lesson.id}&gid=${groupId}">delete</a>
                                    </c:when>
                                    <c:when test="${studentId != null}">
                                        <br><br>
                                        <a href="lesson/edit?id=${lesson.id}&sid=${studentId}">edit</a>
                                        <a href="lesson/delete?id=${lesson.id}&sid=${studentId}">delete</a>
                                    </c:when>
                                    <c:when test="${facultyId != null}">
                                        <br><br>
                                        <a href="lesson/edit?id=${lesson.id}&fid=${facultyId}">edit</a>
                                        <a href="lesson/delete?id=${lesson.id}&fid=${facultyId}">delete</a>
                                    </c:when>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${studentId != null}">
                                        <a href="lesson/add?sid=${studentId}&day=${day}&time=${time}">add</a>
                                    </c:when>
                                    <c:when test="${groupId != null}">
                                        <a href="lesson/add?gid=${groupId}&day=${day}&time=${time}">add</a>
                                    </c:when>
                                    <c:when test="${teacherId != null}">
                                        <a href="lesson/add?tid=${teacherId}&day=${day}&time=${time}">add</a>
                                    </c:when>
                                    <c:when test="${facultyId != null}">
                                        <a href="lesson/add?fid=${facultyId}&day=${day}&time=${time}&groupname=${timetable.name}">add</a>
                                    </c:when>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
        </tr>
    </c:forEach>
</table>
</body>
</html>