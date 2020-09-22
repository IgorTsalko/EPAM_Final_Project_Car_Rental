<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="header.jsp"%>

<%--START MAIN-CONTENT--%>
<div id="content">
    <div class="container main-content">

        <c:if test="${requestScope.message == \"registration successful\"}">
            <h2>Вы успешно зарегистрированны!</h2>
            <p>Теперь вы можете <a href="mainController?command=go_to_login_page">войти</a> под своим логином и паролем</p>
        </c:if>

        <c:if test="${requestScope.message == null}">
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

    </div>
</div>
<%--END MAIN-CONTENT--%>

<%@include file="footer.jsp"%>
