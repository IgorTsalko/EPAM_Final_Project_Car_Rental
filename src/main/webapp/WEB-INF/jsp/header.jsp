<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="img/favicon.png" rel="icon">
    <link rel="stylesheet" media="all" type="text/css" href="css/core.css">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap" rel="stylesheet">
    <script type="text/javascript" src="js/common.js"></script>

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
    <fmt:message key="work_schedule" var="work_schedule"/>

    <fmt:message key="contacts" var="contacts_title"/>
    <fmt:message key="cars_title" var="cars_title"/>
    <fmt:message key="stocks" var="stocks_title"/>
    <fmt:message key="rules" var="rules_title"/>
    <fmt:message key="news" var="news_title"/>

    <fmt:message key="warning.action_not_approved" var="action_not_approved"/>

    <c:set var="command" value="${pageContext.request.getParameter(\"command\")}"/>
    <c:set var="message_security" value="${pageContext.request.getParameter(\"message_security\")}"/>
</head>
<body>
<c:if test="${message_security eq 'unverified_action'}">
<div id="warning">
    <div id="warning_content">
        <span id="close_warning" onclick="closeWarning()">&times;</span>
        <p>${action_not_approved}</p>
    </div>
</div>
</c:if>
<!-- START HEADER -->
<header class="boxShadow1">
    <div class="top">
        <div class="container">
            <div class="header-contacts rowing-left">
                <p><strong>+375(33)357-76-60</strong></p>
            </div>
            <div class="header-contacts rowing-left">
                <p>${work_schedule}</p>
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
                        <button type="submit" name="local" value="ru">${button_ru}</button>
                        <button type="submit" name="local" value="en">${button_en}</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="menu">
            <a class="logo rowing-left" href="${pageContext.request.contextPath}">
                <img src="img/logo.png" alt="Logo">
            </a>
            <div id="main-menu" class="rowing-right">
                <div class="header-menu rowing-right <c:if test="${command eq 'go_to_contact_page'}">selected-tab</c:if>">
                    <a href="mainController?command=go_to_contact_page">${contacts_title}</a>
                </div>
                <div class="header-menu rowing-right <c:if test="${command eq 'go_to_our_cars'}">selected-tab</c:if>">
                    <a href="mainController?command=go_to_our_cars">${cars_title}</a>
                </div>
                <div class="header-menu rowing-right <c:if test="${command eq 'go_to_stocks'}">selected-tab</c:if>">
                    <a href="mainController?command=go_to_stocks">${stocks_title}</a>
                </div>
                <div class="header-menu rowing-right <c:if test="${command eq 'go_to_rules'}">selected-tab</c:if>">
                    <a href="mainController?command=go_to_rules">${rules_title}</a>
                </div>
                <div class="header-menu rowing-right <c:if test="${command eq 'go_to_news'}">selected-tab</c:if>">
                    <a href="mainController?command=go_to_news">${news_title}</a>
                </div>
            </div>
        </div>
    </div>
</header>
<%--END HEADER--%>