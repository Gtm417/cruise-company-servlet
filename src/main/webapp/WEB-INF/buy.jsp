<%--
  Created by IntelliJ IDEA.
  User: Tima
  Date: 28.01.2020
  Time: 23:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>


<c:if test="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}" scope="session"/>
</c:if>

<fmt:setBundle basename="message"/>
<html lang="${param.lang}">
<head>
    <title>Buy_cruise</title>
</head>
<body>
<ul>
    <li><a href="?lang=en"><fmt:message key="label.lang.en"/></a></li>
    <li><a href="?lang=ru"><fmt:message key="label.lang.ru"/></a></li>
</ul>
<c:if test="${sessionScope.exception == true}">
    <label class="alert alert-info"> <fmt:message key="alert.cruise.not.found"/></label>
</c:if>
<c:remove var="exception" scope="session"/>
<c:if test="${noPlaces == true}">
    <label class="alert alert-info"> <fmt:message key="alert.no.place"/></label>
</c:if>
<c:if test="${errors}">
    <p>Not Valid</p>
</c:if>
<div class="form-group">
    <table>
        <thead class="thead-dark">
        <tr>
            <th scope="col"><fmt:message key="table.order.first.name"/></th>
            <th scope="col"><fmt:message key="table.order.second.name"/></th>
            <th scope="col"><fmt:message key="table.order.ticket"/></th>
            <th scope="col"><fmt:message key="table.order.price"/></th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${sessionScope.cruise.tickets.isEmpty()}">
                <h2><fmt:message key="alert.ticket.list.is.empty"/></h2>
            </c:when>
            <c:otherwise>
                <c:forEach var="ticket" items="${sessionScope.cruise.tickets}">
                    <form action="${pageContext.request.contextPath}/buy" method="post">
                        <tr>
                            <td><input type="text" name="firstName"></td>
                            <td><input type="text" name="secondName"></td>
                            <td>${ticket.ticketName}</td>
                            <td>${ticket.priceWithDiscount}</td>
                            <td>
                                <input hidden name="ticket" value="${ticket.id}">
                                <p><input type="submit" value="<fmt:message key="button.submit"/>"/></p>
                            </td>
                        </tr>
                    </form>
                </c:forEach>
            </c:otherwise>
        </c:choose>

        </tbody>
    </table>
    <a aria-pressed="true" class="btn button" role="button" href="${pageContext.request.contextPath}/main"> <fmt:message
            key="button.main"/></a
</div>
</body>
</html>
