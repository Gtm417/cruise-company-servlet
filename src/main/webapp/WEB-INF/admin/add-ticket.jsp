<%--
  Created by IntelliJ IDEA.
  User: Tima
  Date: 31.01.2020
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <title>Add Ticket</title>
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
<c:if test="${sessionScope.exception == true}">
    <label class="alert alert-info"> <fmt:message key="alert.ticket.already.exist"/></label>
</c:if>
<c:remove var="exception" scope="session"/>
<c:if test="${sessionScope.success == true}">
    <label class="alert alert-info"> <fmt:message key="alert.success.sing.up"/></label>
</c:if>
<c:remove var="success" scope="session"/>
<c:if test="${errors}">
    <p>Not Valid</p>
</c:if>
<form action="${pageContext.request.contextPath}/admin/add-ticket" method="post">
    <div class="form-group">
        <label> <fmt:message key="label.ticket.name"/></label>
        <input type="text" class="form-control" name="ticketName">
    </div>

    <div class="form-group">
        <label><fmt:message key="label.ticket.price"/></label>
        <input type="number" min="0" step="10" placeholder="0" required class="form-control" name="price">
    </div>

    <div class="form-group">
        <label><fmt:message key="label.ticket.discount"/></label>
        <input type="number" min="0" max="100" step="10" placeholder="0" required class="form-control" name="discount">
    </div>

    <button type="submit" class="btn btn-default"><fmt:message key="button.submit"/></button>
</form>
<a aria-pressed="true" class="btn button" role="button" href="${pageContext.request.contextPath}/main"> <fmt:message
        key="button.main"/></a
</body>
</html>
