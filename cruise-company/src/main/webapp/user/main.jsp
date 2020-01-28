<%--
  Created by IntelliJ IDEA.
  User: Tima
  Date: 27.01.2020
  Time: 21:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false"%>

<html>
<head>
    <title>Main</title>
</head>
<body>
<tr>
    <th scope="col"> Name</th>
    <th scope="col"> Description</th>
    <th scope="col"> DescriptionRu</th>

</tr>
</thead>

<tbody>
<c:forEach var="cruise" items="${cruises}">
    <tr>
    <td>${cruise.getCruiseName}</td>
    <td>${cruise.getDescriptionEng}</td>
    <td>${cruise.getDescriptionRu}</td>
    <%--            <td>--%>
    <%--                <form action="user" method="post">--%>
    <%--                    <input type="hidden" name="command" value="processExposition"/>--%>
    <%--                    <input type="hidden" name="expositionId" value="${exposition.getId()}"/>--%>
    <%--                    <button type="submit" class="btn btn-success"><fmt:message key="user.showExpositions.order"/></button>--%>
    <%--                </form>--%>
    <%--            </td>--%>
    </tr>
</c:forEach>
</tbody>
</body>
</html>
