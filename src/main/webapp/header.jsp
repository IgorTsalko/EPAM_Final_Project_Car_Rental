<!-- HEADER -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header class="boxShadow1">
    <div class="container">

        <div class="authorization">
            <c:if test="${requestScope.user != null}">
                Hello ${requestScope.user.email}
            </c:if>

            <c:if test="${requestScope.user == null}">
                <a href="registrationPage.jsp">Регистрация</a>
                <a href="loginPage.jsp">Войти</a>
            </c:if>
        </div>

        <div class="menu clear">
            <a class="logo rowing-left" href="<c:url value="/"/>"><img src="img/logo.png" alt="Logo"></a>
            <a class="header-menu rowing-right" href="someLink" class="header-button">Наши контакты</a>
            <a class="header-menu rowing-right" href="someLink" class="header-button">Акции</a>
            <a class="header-menu rowing-right" href="someLink" class="header-button">Правила аренды</a>
        </div>
    </div>
</header>