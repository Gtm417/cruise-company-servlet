<%--
  Created by IntelliJ IDEA.
  User: Tima
  Date: 09.02.2020
  Time: 16:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Not Enough Money</title>
</head>
<body>
<h1>Not enough money</h1>
<a href="${pageContext.request.contextPath}/balance"><fmt:message key="button.replenish.balance"/></a>
<a aria-pressed="true" class="btn button" role="button" href="${pageContext.request.contextPath}/main"> <fmt:message
        key="button.main"/></a
</body>
</html>
