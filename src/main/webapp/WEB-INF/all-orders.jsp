<%--
  Created by IntelliJ IDEA.
  User: Tima
  Date: 01.02.2020
  Time: 11:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<html lang="${param.lang}">

<head>
    <title>Orders</title>
</head>
<body>
<c:if test="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}" scope="session"/>
</c:if>

<fmt:setBundle basename="message"/>
<table class="table table-striped table-responsive-md btn-table">
    <thead class="thead-dark">
    <tr>
        <th scope="col"><fmt:message key="table.order.cruise"/></th>
        <th scope="col"><fmt:message key="table.order.first.name"/></th>
        <th scope="col"><fmt:message key="table.order.second.name"/></th>
        <th scope="col"><fmt:message key="table.order.ticket"/></th>
        <th scope="col"><fmt:message key="table.order.price"/></th>
    </tr>
    </thead>

    <tbody>
    <c:choose>
        <c:when test="${orders.isEmpty()}">
            <h2><fmt:message key="alert.orders.list.is.empty"/></h2>
        </c:when>
        <c:otherwise>
            <c:forEach var="pass" items="${orders}">
                <tr>
                    <td>${pass.cruise.cruiseName}</td>
                    <td>${pass.firstName}</td>
                    <td>${pass.secondName}</td>
                    <td>${pass.ticket.ticketName}</td>
                    <td>${pass.orderPrice}</td>
                </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>
    </tbody>

</table>
<c:import url="pagination.jsp"/>
</body>
</html>

