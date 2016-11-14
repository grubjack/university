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
            <th>${timetable.getName()}</th>
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
                    <td><a href="lessons?id=${lesson.getId()}">
                            ${lesson.subject}<br>
                            ${lesson.classroom.number}<br>
                            ${lesson.teacher.name}
                    </a></td>
                </c:forEach>
            </tr>
        </c:forEach>
        </tr>
    </c:forEach>
</table>
</body>
</html>