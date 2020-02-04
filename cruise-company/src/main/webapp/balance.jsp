<%--
  Created by IntelliJ IDEA.
  User: Tima
  Date: 28.01.2020
  Time: 13:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<c:if test ="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}"  scope="session" />
</c:if>

<fmt:setBundle basename="message" />
<html  lang="${param.lang}">
<head>
    <title>Balance</title>
</head>
<body>
<ul>
    <li><a href="?lang=en"><fmt:message key="label.lang.en" /></a></li>
    <li><a href="?lang=ru"><fmt:message key="label.lang.ru" /></a></li>
</ul>
<form method="post" action="${pageContext.request.contextPath}/balance">

    <input type="number" required name="balance"><br/>
    <input class="button" type="submit" value="Replenish">

</form>
</body>
</html>
