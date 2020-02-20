<%--
  Created by IntelliJ IDEA.
  User: Tima
  Date: 31.01.2020
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setBundle basename="message"/>
<html>
<head>
    <title>Success buy</title>
</head>
<body>
<h1>Success buy</h1>
<a aria-pressed="true" class="btn button" role="button" href="${pageContext.request.contextPath}/main"> <fmt:message
        key="button.main"/></a
</body>
</html>
