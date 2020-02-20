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
<a id='ru'
   href="&lang=ru">ru
</a>
<a id='en'
   href="&lang=en">en
</a>

<script>
    var x = window.location.href;
    var count = (x.split('&').length - 1);
    if (count === 0) {
        x = window.location.href + "&";
    }
    var page = x.substring(0, x.lastIndexOf('&'));
    var link = document.getElementById("ru"); // store the element
    var curHref = link.getAttribute('href'); // get its current href value
    link.setAttribute('href', page + curHref);


    var x = window.location.href;
    var count = (x.split('&').length - 1);
    if (count === 0) {
        x = window.location.href + "&";
    }
    var page = x.substring(0, x.lastIndexOf('&'));
    var link = document.getElementById("en"); // store the element
    var curHref = link.getAttribute('href'); // get its current href value
    link.setAttribute('href', page + curHref);


</script>
<ul>
    <li><a href="?lang=en"><fmt:message key="label.lang.en"/></a></li>
    <li><a href="?lang=ru"><fmt:message key="label.lang.ru"/></a></li>
</ul>
<%--<form id="buy" action="${pageContext.request.contextPath}/buy" method="post" >--%>
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
            <th scope="col"> first name</th>
            <th scope="col"> Second name</th>
            <th scope="col"> Ticket</th>
            <th scope="col"> Price</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="ticket" items="${sessionScope.cruise.tickets}">
            <form action="${pageContext.request.contextPath}/buy" method="post">
                <tr>
                    <td><input type="text" name="firstName"></td>
                    <td><input type="text" name="secondName"></td>
                    <td>${ticket.ticketName}</td>
                    <td>${ticket.priceWithDiscount}</td>
                    <td>
                        <input hidden name="ticket" value="${ticket.id}">
                            <%--                        <input hidden name="price" value="${ticket.priceWithDiscount}">--%>
                            <%--                        <input hidden name="cruiseId" value="${cruiseId}">--%>
                        <p><input type="submit" value="Submit"/></p>
                    </td>
                        <%--            <td>--%>
                        <%--                <form action="user" method="post">--%>
                        <%--                    <input type="hidden" name="command" value="processExposition"/>--%>
                        <%--                    <input type="hidden" name="expositionId" value="${exposition.getId()}"/>--%>
                        <%--                    <button type="submit" class="btn btn-success"><fmt:message key="user.showExpositions.order"/></button>--%>
                        <%--                </form>--%>
                        <%--            </td>--%>
                </tr>

            </form>

        </c:forEach>
        </tbody>
    </table>
</div>

<%--        <select name = "ticket">--%>
<%--            <c:forEach var="ticket" items="${tickets}">--%>
<%--            <option  value="${ticket.id}">--%>
<%--                    ${ticket.ticketName} + ' ' + ${ticket.priceWithDiscount}--%>
<%--            </option>--%>
<%--            </c:forEach>--%>
<%--        </select>--%>


<%--</form>--%>
</body>
</html>
