<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>Exception: ${requestScope.exception}</h3>
<p>
<c:forEach items="${requestScope.exception.stackTrace}" var="trace">
    ${trace}<br>
</c:forEach>
</p>
</body>
</html>
