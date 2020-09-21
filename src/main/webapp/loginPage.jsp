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

        <div class="login-block">
            <h1>Войти</h1>
            <div class="login-form">
                <form action="mainController" method="post">
                    <input type="hidden" name="command" value="authorization">
                    <input type="text" name="login" placeholder="логин">
                    <input type="password" name="password" placeholder="пароль">
                    <input type="submit" value="войти">
                </form>
            </div>
            <div class="info-login-block">
                <p>Вы можете войти с использованием логина и пароля, или воспользоваться <a href="#">страницей регистрации</a></p>
            </div>
        </div>

    </div>
</div>
<%--END MAIN-CONTENT--%>
<%--START FOOTER--%>
<%@include file="footer.jsp"%>
<%--END FOOTER--%>
</body>
</html>
