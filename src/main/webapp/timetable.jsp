<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Timetable</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<p><a href="${pageContext.request.contextPath}/index.html">Home page</a></p>

<h1>Timetable</h1>
<table>
    <tr>
        <td/>
        <c:forEach var="day" items="${days}">
            <th>${day}</th>
        </c:forEach>
    </tr>

    <c:forEach var="time" items="${times}">
        <tr>
            <th>${time}</th>
            <c:forEach var="day" items="${days}">
                <td>
                    <c:set value="${timetable.get(day).get(time)}" var="lesson"/>
                        ${lesson.subject}<br>
                        ${lesson.classroom.number}<br>
                        ${lesson.teacher.name}
                </td>
            </c:forEach>
        </tr>
    </c:forEach>
</table>
</body>
</html>