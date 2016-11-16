<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${title}</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<p><a href="${pageContext.request.contextPath}/${home}">${homeTitle}</a></p>

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
                    <c:set value="${timetable.findUnit(day).findLesson(time)}" var="lesson"/>
                    <td>
                        <c:choose>
                            <c:when test="${lesson != null}">
                                ${lesson.subject}<br>
                                ${lesson.classroom.number}<br>
                                <c:choose>
                                    <c:when test="${tid != null}">
                                        ${lesson.group.name}
                                    </c:when>
                                    <c:otherwise>
                                        ${lesson.teacher.name}
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${tid != null}">
                                        <br><br>
                                        <a href="lessons?action=edit&id=${lesson.id}&day=${day}&time=${time}&tid=${tid}">edit</a>
                                        <a href="lessons?action=delete&id=${lesson.id}">delete</a>
                                    </c:when>
                                    <c:when test="${gid != null}">
                                        <br><br>
                                        <a href="lessons?action=edit&id=${lesson.id}&day=${day}&time=${time}&gid=${gid}">edit</a>
                                        <a href="lessons?action=delete&id=${lesson.id}">delete</a>
                                    </c:when>
                                    <c:when test="${sid != null}">
                                        <br><br>
                                        <a href="lessons?action=edit&id=${lesson.id}&day=${day}&time=${time}&sid=${sid}">edit</a>
                                        <a href="lessons?action=delete&id=${lesson.id}">delete</a>
                                    </c:when>
                                    <c:when test="${fid != null}">
                                        <br><br>
                                        <a href="lessons?action=edit&id=${lesson.id}&day=${day}&time=${time}&fid=${fid}">edit</a>
                                        <a href="lessons?action=delete&id=${lesson.id}">delete</a>
                                    </c:when>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${sid != null}">
                                        <a href="lessons?action=create&day=${day}&time=${time}&sid=${sid}">add</a>
                                    </c:when>
                                    <c:when test="${gid != null}">
                                        <a href="lessons?action=create&day=${day}&time=${time}&gid=${gid}">add</a>
                                    </c:when>
                                    <c:when test="${tid != null}">
                                        <a href="lessons?action=create&day=${day}&time=${time}&tid=${tid}">add</a>
                                    </c:when>
                                    <c:when test="${fid != null}">
                                        <a href="lessons?action=create&day=${day}&time=${time}&fid=${fid}">add</a>
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