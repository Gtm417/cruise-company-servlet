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
    <title>Admin Main</title>
</head>
<body>
<ul>
    <li><a href="?lang=en"><fmt:message key="label.lang.en" /></a></li>
    <li><a href="?lang=ru"><fmt:message key="label.lang.ru" /></a></li>
</ul>
<a href="${pageContext.request.contextPath}/balance"> Replenish</a>
<button></button>

<c:if test="${sessionScope.notFoundCruise}">
    <label class="alert alert-info" > <fmt:message key="alert.wrong.input.data"/></label>
</c:if>
<c:remove var="notFoundCruise" scope="session"/>
<c:if test="${sessionScope.notAllData}">
    <label class="alert alert-info" > <fmt:message key="alert.not.all.data"/></label>
</c:if>
<c:remove var="notAllData" scope="session"/>
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
            <td>
                <form action ="${pageContext.request.contextPath}/admin/edit-cruise" method="post">
                    <input hidden name="cruiseId" value="${cruise.id}">
                    <input class="button" type="submit" value="Edit">
                </form>
            </td>
<%--            <td>--%>
<%--                <form action="user" method="post">--%>
<%--                    <input type="hidden" name="command" value="processExposition"/>--%>
<%--                    <input type="hidden" name="expositionId" value="${exposition.getId()}"/>--%>
<%--                    <button type="submit" class="btn btn-success"><fmt:message key="user.showExpositions.order"/></button>--%>
<%--                </form>--%>
<%--            </td>--%>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href="${pageContext.request.contextPath}/balance"> </a>
</body>
</html>
