<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false"%>





<c:if test ="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}"  scope="session" />
</c:if>

<fmt:setBundle basename="message" />
<html lang="${param.lang}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Admin page</title>
</head>
<body>
<ul>
    <li><a href="?lang=en"><fmt:message key="label.lang.en" /></a></li>
    <li><a href="?lang=ru"><fmt:message key="label.lang.ru" /></a></li>
</ul>
<h1 ><fmt:message key="label.welcome"/>  </h1>
<h1> Admin </h1>
<a href="${pageContext.request.contextPath}/logout" <fmt:message key="button.logout"/> ></a>
</body>
</html>