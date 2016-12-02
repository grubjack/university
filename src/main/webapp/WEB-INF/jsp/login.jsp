<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
    <c:set var="url">${pageContext.request.requestURL}</c:set>
    <base href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/"/>
    <link rel="stylesheet" href="resources/css/style.css">
    <link rel="shortcut icon" href="resources/images/icon.png">
</head>
<body>

<form action="spring_security_check" method="post">
    <ul class="form-style-1">
        <li>
            <h2>Welcome to the university</h2>
        </li>
        <li>
            <label>Username</label>
            <input type="text" name="username" class="field-long" placeholder="admin" required/>
        </li>
        <li>
            <label>Password</label>
            <input type="password" name="password" class="field-long" placeholder="admin" required/>
        </li>
        <li>
            <input type="submit" value="Login"/>&nbsp;
            <input type="reset" value="Reset"/>
        </li>
    </ul>
</form>
</body>
</html>
