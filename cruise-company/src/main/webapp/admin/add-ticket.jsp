<%--
  Created by IntelliJ IDEA.
  User: Tima
  Date: 31.01.2020
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <title>Add Ticket</title>
</head>
<body>
<c:if test="${sessionScope.exception == true}">
    <label class="alert alert-info" > <fmt:message key="alert.wrong.password.or.login"/></label>
</c:if>
<c:remove var="exception" scope="session"/>
<c:if test="${sessionScope.success == true}">
    <label class="alert alert-info" > <fmt:message key="alert.success.sing.up"/></label>
</c:if>
<c:remove var="success" scope="session"/>
<form action="${pageContext.request.contextPath}/admin/add-ticket" method="post">
    <div class="form-group">
        <label> name</label>
        <input type="text"  class="form-control" required name="ticketName">
    </div>

    <div class="form-group">
        <label>price</label>
        <input type="number" min="0" step="10" placeholder="0" required class="form-control" name="price">
    </div>

    <div class="form-group">
        <label>discount</label>
        <input type="number" min="0" max="100" step="10" placeholder="0" required class="form-control" name="discount">
    </div>

    <button type="submit" class="btn btn-default">Submit</button>
</form>
</body>
</html>
