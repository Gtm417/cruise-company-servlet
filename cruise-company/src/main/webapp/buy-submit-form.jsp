<%--
  Created by IntelliJ IDEA.
  User: Tima
  Date: 29.01.2020
  Time: 22:58
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<c:if test ="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}"  scope="session" />
</c:if>

<fmt:setBundle basename="message" />
<html  lang="${param.lang}">
<head>
    <title>Submit Buy Form</title>
</head>
<body>
<ul>
    <li><a href="?lang=en"><fmt:message key="label.lang.en" /></a></li>
    <li><a href="?lang=ru"><fmt:message key="label.lang.ru" /></a></li>
</ul>
<c:if test="${sessionScope.exception == true}">
    <label class="alert alert-info" > <fmt:message key="alert.not.enough.money"/></label>
</c:if>
<c:remove var="exception" scope="session"/>
<h1>Receipt</h1>

<table cellspacing="0">
    <tr>
        <td>Cruise</td>
        <td>${sessionScope.cruise.cruiseName}</td>
    </tr>
    <tr>
        <td>Ticket</td>
        <td>${sessionScope.order.ticketId}</td>
    </tr>
    <tr>
        <td>Price</td>
        <td>${sessionScope.order.price}</td>
        <td>${resultPrice}</td>
    </tr>
</table>

<table>
    <thead class="thead-dark">
    <tr>
        <th scope="col"> port</th>
        <th scope="col"> Name</th>
        <th scope="col"> duration</th>
        <th scope="col"> price</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="excursion" items="${excursions}">
        <tr>
            <td>${excursion.port.portName}</td>
            <td>${excursion.excursionName}</td>
            <td>${excursion.duration}</td>
            <td>${excursion.price}</td>
            <td>
                <form action="${pageContext.request.contextPath}/add-excursion" method="post">
                    <input hidden name="excursionName" value = "${excursion.excursionName}"/>
                    <input hidden name="id" value = "${excursion.id}"/>
                    <input hidden name="duration" value = "${excursion.duration}"/>
                    <input hidden name="price" value = "${excursion.price}"/>
                    <input hidden name="portId" value = "${excursion.port.id}"/>
                    <input hidden name="portName" value = "${excursion.port.portName}"/>
                    <button class="btn btn-success">Add</button>
                </form>
            </td>
        </tr>


    </c:forEach>
    </tbody>
</table>
<table>
    <thead class="thead-dark">
    <tr>
        <th scope="col"> port</th>
        <th scope="col"> Name</th>
        <th scope="col"> price</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="selectedExcursion" items="${sessionScope.selectedExcursions}">
        <tr>
            <td>${selectedExcursion.port.portName}</td>
            <td>${selectedExcursion.excursionName}</td>
            <td>${selectedExcursion.price}</td>
            <td>
                <form action="${pageContext.request.contextPath}/remove-excursion" method="post">
                    <input hidden name="excursionName" value = "${selectedExcursion.excursionName}"/>
                    <input hidden name="id" value = "${selectedExcursion.id}"/>
                    <input hidden name="duration" value = "${selectedExcursion.duration}"/>
                    <input hidden name="price" value = "${selectedExcursion.price}"/>
                    <input hidden name="portId" value = "${selectedExcursion.port.id}"/>
                    <input hidden name="portName" value = "${selectedExcursion.port.portName}"/>
                    <button class="btn btn-danger">Remove</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<form action="${pageContext.request.contextPath}/buy-submit">
    <button class="btn btn-success" value="Submit">Submit</button>
    <input hidden name="resultPrice" value="${resultPrice}">
</form>

</body>
</html>
