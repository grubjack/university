<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<a href="${pageContext.request.contextPath}/" class="button">Home page</a>&nbsp;
<sec:authorize access="isAuthenticated()">
    <a href="${pageContext.request.contextPath}/logout" class="button">Logout</a><p/>
</sec:authorize>