<%--
  Created by IntelliJ IDEA.
  User: Tima
  Date: 31.01.2020
  Time: 15:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Description</title>
</head>
<body>
<c:if test="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}" scope="session"/>
</c:if>

<fmt:setBundle basename="message"/>

<ul>
    <li><a href="?lang=en"><fmt:message key="label.lang.en"/></a></li>
    <li><a href="?lang=ru"><fmt:message key="label.lang.ru"/></a></li>
</ul>
<c:if test="${errors}">
    <p>Not Valid</p>
</c:if>
<form action="${pageContext.request.contextPath}/admin/edit-description" method="post">
    <input type="text" placeholder="descriptionEng" name="descriptionEng"><br/>
    <input type="text" placeholder="descriptionRu" name="descriptionRu"><br/><br/>
    <input class="button" type="submit" value="<fmt:message key="button.submit"/>">
</form>
<a aria-pressed="true" class="btn button" role="button" href="${pageContext.request.contextPath}/main"> <fmt:message
        key="button.main"/></a
</body>
</html>
