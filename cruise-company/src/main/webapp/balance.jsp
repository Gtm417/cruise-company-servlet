<%--
  Created by IntelliJ IDEA.
  User: Tima
  Date: 28.01.2020
  Time: 13:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Balance</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/balance">

    <input type="number" required name="balance"><br/>
    <input class="button" type="submit" value="Replenish">

</form>
</body>
</html>
