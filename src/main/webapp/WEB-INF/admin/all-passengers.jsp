<%--
  Created by IntelliJ IDEA.
  User: Tima
  Date: 31.01.2020
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>


<html>
<head>
    <title>Passengers</title>
</head>
<body>
<c:if test="${sessionScope.exception == true}">
    <label class="alert alert-info"> <fmt:message key="alert.passenger.list.is.empty"/></label>
</c:if>
<c:remove var="exception" scope="session"/>
<table class="table table-striped table-responsive-md btn-table">
    <thead class="thead-dark">
    <tr>
        <th scope="col"> Cruise name</th>
        <th scope="col"> First name</th>
        <th scope="col"> Second name</th>
        <th scope="col"> Ticket name</th>
        <th scope="col"> Price</th>
    </tr>
    </thead>

    <tbody>
    <c:choose>
        <c:when test="${passengers.isEmpty()}">
            <h2><fmt:message key="alert.orders.list.is.empty"/></h2>
        </c:when>
        <c:otherwise>
            <c:forEach var="pass" items="${passengers}">
                <tr>
                    <td>${sessionScope.cruise.cruiseName}</td>
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
</body>
</html>
