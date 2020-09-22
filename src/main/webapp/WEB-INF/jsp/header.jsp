<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Car_rental Igor Tsalko</title>
    <link rel="stylesheet" media="all" type="text/css" href="${pageContext.request.contextPath}/css/core.css">
</head>
<body>
<!-- START HEADER -->
<header class="boxShadow1">
    <div class="top">
        <div class="container">
            <div class="user-info">
                <c:if test="${sessionScope.user != null}">
                    Привет <strong>${sessionScope.user.login};</strong>
                    ID: ${sessionScope.user.id}, Роль: ${sessionScope.user.role}, Рейтинг: ${sessionScope.user.rating}
                    <a href="mainController?command=logout">Выйти</a>
                </c:if>

                <c:if test="${sessionScope.user == null}">
                    <a href="mainController?command=go_to_registration_page">Регистрация</a>
                    <a href="mainController?command=go_to_login_page">Войти</a>
                </c:if>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="menu clear">
            <a class="logo rowing-left" href="<c:url value="/"/>"><img src="${pageContext.request.contextPath}/img/logo.png" alt="Logo"></a>
            <a class="header-menu rowing-right" href="mainController?command=go_to_contact_page" class="header-button">Контакты</a>
            <a class="header-menu rowing-right" href="mainController?command=go_to_catalog" class="header-button">Каталог</a>
            <a class="header-menu rowing-right" href="mainController?command=go_to_stocks" class="header-button">Акции</a>
            <a class="header-menu rowing-right" href="mainController?command=go_to_regulations" class="header-button">Правила аренды</a>
            <a class="header-menu rowing-right" href="mainController?command=go_to_news" class="header-button">Новости</a>
        </div>
    </div>
</header>
<%--END HEADER--%>