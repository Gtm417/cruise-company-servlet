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
    <title>Login in system</title>
</head>
<body>
    <ul>
        <li><a href="?lang=en"><fmt:message key="label.lang.en" /></a></li>
        <li><a href="?lang=ru"><fmt:message key="label.lang.ru" /></a></li>
    </ul>

        <h1><fmt:message key="button.login"/></h1><br/>
    <c:if test="${sessionScope.exception == true}">
        <label class="alert alert-info" > <fmt:message key="alert.wrong.password.or.login"/></label>
    </c:if>
    <c:remove var="exception" scope="session"/>
        <form method="post" action="${pageContext.request.contextPath}/login">

            <input type="text" name="name"><br/>
            <input type="password" name="pass"><br/><br/>
            <input class="button" type="submit" value="Войти">

        </form>
        <br/>
        <a href="${pageContext.request.contextPath}/logout"><fmt:message key="button.logout"/> </a>

</body>
</html>