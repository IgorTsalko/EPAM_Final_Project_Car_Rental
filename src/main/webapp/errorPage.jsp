<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Error page</title>
</head>
<body>
<p>${errorMessage}</p>
<p>
    <c:forEach items="${stackTrace}" var="s">
        ${s}
    </c:forEach>
</p>
</body>
</html>
