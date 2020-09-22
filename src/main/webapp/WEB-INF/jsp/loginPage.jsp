<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="header.jsp"%>

<%--START MAIN-CONTENT--%>
<div id="content">
    <div class="container main-content">

        <div class="login-block">
            <h1>Войти</h1>

            <c:if test="${requestScope.message == \"incorrect data\"}">
                <p>Неверный логин или пароль!</p>
            </c:if>

            <div class="login-form">
                <form action="mainController" method="post">
                    <input type="hidden" name="command" value="authorization">
                    <input type="text" name="login" placeholder="логин">
                    <input type="password" name="password" placeholder="пароль">
                    <input type="submit" value="войти">
                </form>
            </div>
            <div class="info-login-block">
                <p>Вы можете войти с использованием логина и пароля, или воспользоваться
                    <a href="mainController?command=go_to_registration_page">страницей регистрации</a>
                </p>
            </div>
        </div>

    </div>
</div>
<%--END MAIN-CONTENT--%>

<%@include file="footer.jsp"%>