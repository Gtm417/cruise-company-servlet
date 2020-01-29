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
<html>
<head>
    <title>Buy_cruise</title>
</head>
<body>
<%--<form id="buy" action="${pageContext.request.contextPath}/buy" method="post" >--%>

<div class="form-row">
    <div class="col">
        <input type="text" class="form-control" placeholder="Имя">
    </div>
    <div class="col">
        <input type="text" class="form-control" placeholder="Фамилия">
    </div>
</div>
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
        <c:forEach var="ticket" items="${tickets}">
            <form action="${pageContext.request.contextPath}/buy">
                <tr>
                    <td><input type="text" required name="firstName"></td>
                    <td><input type="text" required name="secondName"></td>
                    <td>${ticket.ticketName}</td>
                    <td>${ticket.priceWithDiscount}</td>
                    <td>
                        <input hidden name="ticket" value="${ticket.id}">
                        <input hidden name="price" value="${ticket.priceWithDiscount}">
                        <input hidden name="cruiseId" value="${cruiseId}">
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
