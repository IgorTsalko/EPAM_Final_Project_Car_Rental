<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Car_rental Igor Tsalko</title>
    <link rel="stylesheet" media="all" type="text/css" href="css/core.css">
</head>
<body>
<%--START HEADER--%>
<%@include file="header.jsp"%>
<%--END HEADER--%>
<%--START MAIN-CONTENT--%>
<div id="content">
    <div class="container">

        <c:if test="${requestScope.registration == null}">

            <div class="registration-block">
                <h1>Регистрация</h1>
                <div class="registration-form">
                    <form action="mainController" method="post">
                        <input type="hidden" name="command" value="registration">
                        <input type="email" name="email" placeholder="email">
                        <input type="tel" name="phone" placeholder="телефон">
                        <input type="text" name="login" required placeholder="логин">
                        <input type="password" name="password" required placeholder="пароль">
                        <input type="submit" value="Зарегистрироваться">
                    </form>
                </div>
                <div class="info-registration-block">
                    <p>Нажимая кнопку «Зарегистрироваться», вы даете <a href="#">согласие на обработку своих персональных данных</a></p>
                </div>
            </div>

        </c:if>

        <c:if test="${requestScope.registration != null}">

            <h2>Поздравляем, регистрация прошла успешно, теперь вы можете <a href="loginPage.jsp">войти</a> под своим логином и паролем</h2>

        </c:if>

    </div>
</div>
<%--END MAIN-CONTENT--%>
<%--START FOOTER--%>
<%@include file="footer.jsp"%>
<%--END FOOTER--%>
</body>
</html>
