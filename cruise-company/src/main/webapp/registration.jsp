<<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>


<c:if test ="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}"  scope="session" />
</c:if>

<fmt:setBundle basename="message" />
<html  lang="${param.lang}">
<head>
    <title>Registration</title>
</head>
<body>
    <ul>
        <li><a href="?lang=en"><fmt:message key="label.lang.en"/></a></li>
        <li><a href="?lang=ru"><fmt:message key="label.lang.ru"/></a></li>
    </ul>
    <h1><fmt:message key="button.registration"/></h1><br/>
    <form method="post" action="${pageContext.request.contextPath}/registration">

        <input type="text" required name="name"><br/>
        <input type="password" required name="pass"><br/><br/>
        <input class="button" type="submit" value="Войти">

    </form>

</body>
</html>
