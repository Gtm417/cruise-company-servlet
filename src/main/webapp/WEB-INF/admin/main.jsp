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
    <title>Admin Main</title>
</head>
<body>
<ul>
    <li><a href="?lang=en"><fmt:message key="label.lang.en"/></a></li>
    <li><a href="?lang=ru"><fmt:message key="label.lang.ru"/></a></li>
</ul>
<button><a href="${pageContext.request.contextPath}/balance"><fmt:message key="button.replenish.balance"/> </a></button>
<button><a href="${pageContext.request.contextPath}/all-orders?page=1&size=5"> <fmt:message key="button.orders"/></a>
</button>

<c:if test="${sessionScope.exception == true}">
    <label class="alert alert-info"> <fmt:message key="alert.cruise.not.found"/></label>
</c:if>
<c:remove var="exception" scope="session"/>
<c:if test="${sessionScope.notAllData}">
    <label class="alert alert-info"> <fmt:message key="alert.not.all.data"/></label>
</c:if>
<c:remove var="notAllData" scope="session"/>

<p><fmt:message key="label.balance"/> ${sessionScope.user.balance}</p>
<table class="table table-striped table-responsive-md btn-table">
    <thead class="thead-dark">
    <tr>
        <th scope="col"><fmt:message key="table.cruise.col.name"/></th>
        <th scope="col"><fmt:message key="table.cruise.col.description"/></th>
        <th scope="col"><fmt:message key="table.cruise.col.arrival.date"/></th>
        <th scope="col"><fmt:message key="table.cruise.col.departure.date"/></th>
    </tr>
    </thead>

    <tbody>
    <c:forEach var="cruise" items="${cruises}">
        <tr>
            <td>${cruise.cruiseName}</td>
            <td>
                <c:choose>
                    <c:when test="${sessionScope.lang.equals('en') || sessionScope.lang == null}">
                        ${cruise.descriptionEng}
                    </c:when>
                    <c:otherwise>
                        ${cruise.descriptionRu}
                    </c:otherwise>
                </c:choose>
            </td>
            <td>${cruise.arrivalDate}</td>
            <td>${cruise.departureDate}</td>
            <td>
                <form action="${pageContext.request.contextPath}/buy" method="get">
                    <input hidden name="cruiseId" value="${cruise.id}">
                    <input class="button" type="submit" value="<fmt:message key="button.buy"/>">
                </form>
            </td>
            <td>
                <form action="${pageContext.request.contextPath}/admin/edit-cruise" method="get">
                    <input hidden name="cruiseId" value="${cruise.id}">
                    <input class="button" type="submit" value="<fmt:message key="button.edit"/>">
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
