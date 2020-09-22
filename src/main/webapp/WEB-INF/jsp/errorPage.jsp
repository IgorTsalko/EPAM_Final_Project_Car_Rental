<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Error page</title>
</head>
<body>
<h1>Извините, у нас ошибка!</h1>
<h3>Ниже описание ошибки, возможно оно поможет вам разобраться</h3>
<p>${requestScope.error.message}</p>
<p>${requestScope.error.stackTrace}</p>
</body>
</html>
