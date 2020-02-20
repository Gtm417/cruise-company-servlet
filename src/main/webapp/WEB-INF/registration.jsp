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
    <title>Registration</title>
</head>
<body>
<ul>
    <li><a href="?lang=en"><fmt:message key="label.lang.en"/></a></li>
    <li><a href="?lang=ru"><fmt:message key="label.lang.ru"/></a></li>
</ul>
<h1><fmt:message key="button.registration"/></h1><br/>

<c:if test="${sessionScope.exception == true}">
    <label class="alert alert-info"> <fmt:message key="alert.user.already.exist"/></label>
</c:if>
<c:remove var="exception" scope="session"/>

<c:if test="${errors}">
    <p>Not Valid</p>
</c:if>
<form method="post" action="${pageContext.request.contextPath}/registration">
    <label for="name"> <fmt:message key="label.login"/></label>
    <input id="name" type="text" required pattern="^[A-Za-z0-9_-]{3,16}$" name="name"><br/>
    <label for="password"> <fmt:message key="label.password"/></label>
    <input id="password" type="password" required pattern="^[A-Za-z0-9_-]{5,18}$" name="pass"><br/><br/>
    <input class="button" type="submit" value="<fmt:message key="button.registration"/>">

</form>

</body>
</html>
