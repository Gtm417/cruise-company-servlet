<%--
  Created by IntelliJ IDEA.
  User: Tima
  Date: 31.01.2020
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit Cruise</title>
</head>
<body>
<c:if test="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}" scope="session"/>
</c:if>

<fmt:setBundle basename="message"/>
<ul>
    <li><a href="?lang=en"><fmt:message key="label.lang.en"/></a></li>
    <li><a href="?lang=ru"><fmt:message key="label.lang.ru"/></a></li>
</ul>
<a href="${pageContext.request.contextPath}/admin/add-ticket"><fmt:message key="button.add.ticket"/></a>
<a href="${pageContext.request.contextPath}/admin/edit-description"><fmt:message key="button.description"/></a>
<a href="${pageContext.request.contextPath}/admin/all-passengers"><fmt:message key="button.all.passengers"/></a>
<a aria-pressed="true" class="btn button" role="button" href="${pageContext.request.contextPath}/main"> <fmt:message
        key="button.main"/></a
</body>
</html>
