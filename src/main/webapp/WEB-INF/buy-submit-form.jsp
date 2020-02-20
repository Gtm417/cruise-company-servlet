<%--
  Created by IntelliJ IDEA.
  User: Tima
  Date: 29.01.2020
  Time: 22:58
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<c:if test="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}" scope="session"/>
</c:if>

<fmt:setBundle basename="message"/>
<html lang="${param.lang}">
<head>
    <title>Submit Buy Form</title>
</head>
<body>
<ul>
    <li><a href="?lang=en"><fmt:message key="label.lang.en"/></a></li>
    <li><a href="?lang=ru"><fmt:message key="label.lang.ru"/></a></li>

</ul>
<c:if test="${sessionScope.exception == true}">
    <label class="alert alert-info"> <fmt:message key="alert.excursion.not.found"/></label>
</c:if>
<c:remove var="exception" scope="session"/>
<h1>Receipt</h1>

<table>
    <tr>
        <td><fmt:message key="table.order.first.name"/></td>
        <td>${sessionScope.order.firstName}</td>
    </tr>
    <tr>
        <td><fmt:message key="table.order.second.name"/></td>
        <td>${sessionScope.order.secondName}</td>
    </tr>
    <tr>
        <td><fmt:message key="table.order.cruise"/></td>
        <td>${sessionScope.cruise.cruiseName}</td>
    </tr>
    <tr>
        <td><fmt:message key="table.order.ticket"/></td>
        <td>${sessionScope.order.ticket.ticketName}</td>
    </tr>
    <tr>
        <td><fmt:message key="table.order.price"/></td>
        <td>${sessionScope.order.ticket.price} <fmt:message key="currency.info"/></td>
        <td></td>
    </tr>
    <tr>
        <td><fmt:message key="label.ticket.discount"/></td>
        <td>${sessionScope.order.ticket.discount}%</td>
    </tr>
    <tr>
        <td>Total</td>
        <td>${sessionScope.order.orderPrice} <fmt:message key="currency.info"/></td>
        <%--        <td>${resultPrice} <fmt:message key="currency.info"/></td>--%>
    </tr>
</table>

<table>
    <thead class="thead-dark">
    <tr>
        <th scope="col"><fmt:message key="table.excursion.port"/></th>
        <th scope="col"><fmt:message key="table.excursion.name"/></th>
        <th scope="col"><fmt:message key="table.excursion.duration"/></th>
        <th scope="col"><fmt:message key="table.excursion.price"/></th>
    </tr>
    </thead>
    <tbody>
    <c:choose>
        <c:when test="${excursions.isEmpty()}">
            <h2><fmt:message key="alert.excursion.list.not.found"/></h2>
        </c:when>
        <c:otherwise>
            <c:forEach var="excursion" items="${excursions}">
                <tr>
                    <td>${excursion.port.portName}</td>
                    <td>${excursion.excursionName}</td>
                    <td>${excursion.duration}</td>
                    <td>${excursion.price}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/add-excursion" method="post">
                            <input hidden name="id" value="${excursion.id}"/>
                            <button class="btn btn-success"><fmt:message key="button.add"/></button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>

    </tbody>
</table>
<table>
    <thead class="thead-dark">
    <tr>
        <th scope="col"><fmt:message key="table.excursion.port"/></th>
        <th scope="col"><fmt:message key="table.excursion.name"/></th>
        <th scope="col"><fmt:message key="table.excursion.price"/></th>
    </tr>
    </thead>
    <tbody>
    <c:choose>
        <c:when test="${sessionScope.order.excursionList.isEmpty()}">
            <p><fmt:message key="alert.selected.excursions.not.found"/></p>
        </c:when>
        <c:otherwise>
            <c:forEach var="selectedExcursion" items="${sessionScope.order.excursionList}">
                <tr>
                    <td>${selectedExcursion.port.portName}</td>
                    <td>${selectedExcursion.excursionName}</td>
                    <td>${selectedExcursion.price}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/remove-excursion" method="post">
                            <input hidden name="id" value="${selectedExcursion.id}"/>
                            <button class="btn btn-danger"><fmt:message key="button.remove"/></button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>

    </tbody>
</table>
<form action="${pageContext.request.contextPath}/buy-submit" method="post">
    <input class="button" type="submit" value="<fmt:message key="button.submit"/>">
</form>

<a aria-pressed="true" class="btn" role="button" href="${pageContext.request.contextPath}/main"> <fmt:message
        key="button.main"/></a
</body>
</html>
