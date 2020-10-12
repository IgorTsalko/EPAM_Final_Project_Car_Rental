<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<%@include file="../header.jsp"%>

<fmt:message key="user.role" var="role"/>
<fmt:message key="user.rating" var="rating"/>
<fmt:message key="user.discount" var="discount"/>
<fmt:message key="user.orders.title" var="orders_title"/>
<fmt:message key="user.passport_data.title" var="passport_data_title"/>
<fmt:message key="user.cards.title" var="cards_title"/>
<fmt:message key="user.all_orders_title" var="all_orders_title"/>
<c:set var="command" value="${pageContext.request.getParameter(\"command\")}"/>

<%--START MAIN-CONTENT--%>
<div id="content" class="clear">
    <div class="container main-content">

        <c:if test="${empty sessionScope.user}">
            <c:redirect url="mainController?command=go_to_main_page"/>
        </c:if>

        <c:if test="${not empty sessionScope.user}">

                    <div id="user-logo" class="rowing-left">
                        <p>${sessionScope.user.login.charAt(0)}</p>
                    </div>
                    <h3>${hello} ${sessionScope.user.login}</h3>
                    <p>${role}: ${sessionScope.user.role} • ${rating}: ${sessionScope.user.rating} • ${discount}: ${sessionScope.user.discount}%</p>

                <div class="user-data">
                    <div id="user-menu" class="clear">
                        <div class="user-menu-tab rowing-left <c:if test="${command eq 'go_to_user_page_orders'}">selected-user-tab</c:if>">
                            <a href="mainController?command=go_to_user_page_orders">${orders_title}</a>
                        </div>
                        <div class="user-menu-tab rowing-left <c:if test="${command eq 'go_to_user_page_passport'}">selected-user-tab</c:if>">
                            <a href="mainController?command=go_to_user_page_passport">${passport_data_title}</a>
                        </div>
                        <div class="user-menu-tab rowing-left <c:if test="${command eq 'go_to_user_page_cards'}">selected-user-tab</c:if>">
                            <a href="mainController?command=go_to_user_page_cards">${cards_title}</a>
                        </div>
                        <c:if test="${sessionScope.user.role eq 'admin'}">
                            <div class="user-menu-tab admin-tab rowing-left <c:if test="${command eq 'go_to_user_page_all_orders'}">selected-user-tab</c:if>">
                                <a href="mainController?command=go_to_user_page_all_orders">${all_orders_title}</a>
                            </div>
                        </c:if>
                    </div>
                    <div id="user-menu-content">
                        <c:choose>
                            <c:when test="${command eq 'go_to_user_page_orders'}">
                                <%@include file="orders.jsp"%>
                            </c:when>
                            <c:when test="${command eq 'go_to_user_page_passport'}">
                                <%@include file="passport.jsp"%>
                            </c:when>
                            <c:when test="${command eq 'go_to_user_page_cards'}">
                                <%@include file="cards.jsp"%>
                            </c:when>
                            <c:when test="${command eq 'go_to_user_page_all_orders'}">
                                <%@include file="allOrders.jsp"%>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
        </c:if>

    </div>
</div>
<%--END MAIN-CONTENT--%>

<%@include file="../footer.jsp"%>