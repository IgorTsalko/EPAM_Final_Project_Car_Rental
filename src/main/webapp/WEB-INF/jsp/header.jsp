<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Car_rental Igor Tsalko</title>
    <link rel="stylesheet" media="all" type="text/css" href="${pageContext.request.contextPath}/css/core.css">
    <script type="text/javascript" async="" src="${pageContext.request.contextPath}/js/common.js"></script>
    <link href="${pageContext.request.contextPath}/img/favicon.png" rel="icon">

    <c:if test="${not empty sessionScope.local}">
        <fmt:setLocale value="${sessionScope.local}"/>
    </c:if>

    <fmt:setBundle basename="appLocalization.app"/>

    <fmt:message key="header.button.ru" var="button_ru"/>
    <fmt:message key="header.button.en" var="button_en"/>

    <fmt:message key="hello" var="hello"/>
    <fmt:message key="log_in" var="log_in"/>
    <fmt:message key="log_out" var="log_out"/>
    <fmt:message key="registration" var="registration"/>

    <fmt:message key="contacts" var="contacts_title"/>
    <fmt:message key="catalog" var="catalog_title"/>
    <fmt:message key="stocks" var="stocks_title"/>
    <fmt:message key="rules" var="rules_title"/>
    <fmt:message key="news" var="news_title"/>
</head>
<body>
<!-- START HEADER -->
<header class="boxShadow1">
    <div class="top">
        <div class="container">
            <div class="header-contacts rowing-left">
                <p><strong>+375(33)357-76-60</strong></p>
            </div>
            <div class="header-contacts rowing-left">
                <p>с 9:00 до 21:00</p>
            </div>
            <div class="user-info">
                <c:if test="${sessionScope.user != null}">
                    ${hello} <a href="mainController?command=go_to_personal_page"><strong>${sessionScope.user.login}</strong></a>
                    <a href="mainController?command=logout">${log_out}</a>
                </c:if>

                <c:if test="${sessionScope.user == null}">
                    <a href="mainController?command=go_to_registration_page">${registration}</a>
                    <a href="mainController?command=go_to_login_page">${log_in}</a>
                </c:if>

                <div id="localization">
                    <form action="mainController?command=app_localization" method="post">
                        <input type="hidden" name="local" value="ru"/>
                        <button type="submit">${button_ru}</button>
                    </form>
                    <form action="mainController?command=app_localization" method="post">
                        <input type="hidden" name="local" value="en"/>
                        <button type="submit">${button_en}</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="menu clear">
            <a class="logo rowing-left" href="${pageContext.request.contextPath}">
                <img src="${pageContext.request.contextPath}/img/logo.png" alt="Logo">
            </a>
            <a class="header-menu rowing-right" href="mainController?command=go_to_contact_page">${contacts_title}</a>
            <a class="header-menu rowing-right" href="mainController?command=go_to_catalog">${catalog_title}</a>
            <a class="header-menu rowing-right" href="mainController?command=go_to_stocks">${stocks_title}</a>
            <a class="header-menu rowing-right" href="mainController?command=go_to_rules">${rules_title}</a>
            <a class="header-menu rowing-right" href="mainController?command=go_to_news">${news_title}</a>
        </div>
    </div>
</header>
<%--END HEADER--%>