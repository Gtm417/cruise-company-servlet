<%--
  Created by IntelliJ IDEA.
  User: Tima
  Date: 27.01.2020
  Time: 21:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false"%>

<c:if test ="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}"  scope="session" />
</c:if>

<fmt:setBundle basename="message" />
<html  lang="${param.lang}">
<head>
    <ul>
        <li><a href="?lang=en"><fmt:message key="label.lang.en" /></a></li>
        <li><a href="?lang=ru"><fmt:message key="label.lang.ru" /></a></li>
    </ul>
    <title>Main</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/balance" class="button"> replenish</a>
<c:if test="${notFoundCruise}">
    <label class="alert alert-info" > <fmt:message key="alert.wrong.input.data"/></label>
</c:if>
<c:if test="${notAllData}">
    <label class="alert alert-info" > <fmt:message key="alert.not.all.data"/></label>
</c:if>
<table class="table table-striped table-responsive-md btn-table">
    <thead class="thead-dark">
    <tr>
        <th scope="col"> Name</th>
        <th scope="col"> Description</th>
        <th scope="col"> DescriptionRu</th>
    </tr>
    </thead>

    <tbody>
    <c:forEach var="cruise" items="${cruises}">
        <tr>
            <td>${cruise.cruiseName}</td>
            <td>${cruise.descriptionEng}</td>
            <td>${cruise.descriptionRu}</td>
            <td>
                <form action ="${pageContext.request.contextPath}/buy-form" method="post">
                    <input hidden name="cruiseId" value="${cruise.id}">
                    <input class="button" type="submit" value="Buy">
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
