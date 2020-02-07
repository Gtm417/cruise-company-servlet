<%--
  Created by IntelliJ IDEA.
  User: Tima
  Date: 31.01.2020
  Time: 15:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Description</title>
</head>
<body>
<c:if test="${sessionScope.exception == true}">
    <label class="alert alert-info" > <fmt:message key="alert.wrong.password.or.login"/></label>
</c:if>
<c:remove var="exception" scope="session"/>
<form action="${pageContext.request.contextPath}/admin/edit-description" method="post">
    <input type="text"  placeholder="descriptionEng" name="descriptionEng"><br/>
    <input type="text"  placeholder="descriptionRu" name="descriptionRu"><br/><br/>
    <input class="button" type="submit" value="Update">
</form>
</body>
</html>
