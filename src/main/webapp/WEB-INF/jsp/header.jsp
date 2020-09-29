<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Car_rental Igor Tsalko</title>
    <link rel="stylesheet" media="all" type="text/css" href="${pageContext.request.contextPath}/css/core.css">

    <c:if test="${not empty sessionScope.local}">
        <fmt:setLocale value="${sessionScope.local}"/>
    </c:if>

    <fmt:setBundle basename="appLocalization.app"/>

    <fmt:message key="header.button.ru" var="button_ru"/>
    <fmt:message key="header.button.en" var="button_en"/>

    <fmt:message key="hello" var="hello"/>
    <fmt:message key="log_in_title" var="log_in"/>
    <fmt:message key="log_out_title" var="log_out"/>
    <fmt:message key="registration_title" var="registration"/>

    <fmt:message key="contacts_title" var="menu_contacts"/>
    <fmt:message key="catalog_title" var="menu_catalog"/>
    <fmt:message key="stocks_title" var="menu_stocks"/>
    <fmt:message key="rules_title" var="menu_rules"/>
    <fmt:message key="news_title" var="menu_news"/>
</head>
<body>
<!-- START HEADER -->
<header class="boxShadow1">
    <div class="top">
        <div class="container">
            <div class="user-info">
                <c:if test="${sessionScope.user != null}">
                    ${hello}<a href="mainController?command=go_to_user_page"><strong>${sessionScope.user.login}</strong></a>
                    <a href="mainController?command=logout">${log_out}</a>
                </c:if>

                <c:if test="${sessionScope.user == null}">
                    <a href="mainController?command=go_to_registration_page">${registration}</a>
                    <a href="mainController?command=go_to_login_page">${log_in}</a>
                </c:if>

                <div id="localization">
                    <form action="mainController?command=app_localization" method="post">
                        <input type="hidden" name="local" value="ru"/>
                        <input type="hidden" name="previous_command" value="${pageContext.request.getParameter("command")}">
                        <input type="submit" value="${button_ru}"/>
                    </form>
                    <form action="mainController?command=app_localization" method="post">
                        <input type="hidden" name="local" value="en"/>
                        <input type="hidden" name="previous_command" value="${pageContext.request.getParameter("command")}">
                        <input type="submit" value="${button_en}"/>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="menu clear">
            <a class="logo rowing-left" href="<c:url value="/"/>">
                <img src="${pageContext.request.contextPath}/img/logo.png" alt="Logo">
            </a>
            <a class="header-menu rowing-right" href="mainController?command=go_to_contact_page">${menu_contacts}</a>
            <a class="header-menu rowing-right" href="mainController?command=go_to_catalog">${menu_catalog}</a>
            <a class="header-menu rowing-right" href="mainController?command=go_to_stocks">${menu_stocks}</a>
            <a class="header-menu rowing-right" href="mainController?command=go_to_rules">${menu_rules}</a>
            <a class="header-menu rowing-right" href="mainController?command=go_to_news">${menu_news}</a>
        </div>
    </div>
</header>
<%--END HEADER--%>