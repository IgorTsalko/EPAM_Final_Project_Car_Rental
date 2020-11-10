<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://localhost:8080/CarRentalFinalProjectJWD/mytag" prefix="mytag" %>

<%@include file="../header.jsp" %>
<title>${sessionScope.user.login}</title>

<fmt:message key="user.role" var="role"/>
<fmt:message key="user.rating" var="rating"/>
<fmt:message key="user.discount" var="discount"/>
<fmt:message key="user.registration_date" var="registration_date"/>
<fmt:message key="user.details" var="details_title"/>
<fmt:message key="user.orders" var="orders_title"/>
<fmt:message key="user.passport_data" var="passport_data"/>
<fmt:message key="user.bankcards_title" var="bankcards_title"/>
<fmt:message key="user.all_orders" var="all_orders"/>
<fmt:message key="user.all_users" var="all_users"/>
<fmt:message key="bankcard.adding_title" var="bankcard_adding_title"/>
<c:set var="command" value="${pageContext.request.getParameter(\"command\")}"/>

<%--START MAIN-CONTENT--%>
<div id="content" class="clear">
    <div class="container main-content">

        <c:choose>
            <c:when test="${empty sessionScope.user}">
                <c:redirect url="mainController?command=go_to_main_page"/>
            </c:when>
            <c:when test="${(sessionScope.user.role eq 'admin') && (command eq 'go_to_personal_page')}">
                <c:redirect url="mainController?command=go_to_personal_page_all_orders"/>
            </c:when>
            <c:when test="${(sessionScope.user.role ne 'admin') && (command eq 'go_to_personal_page')}">
                <c:redirect url="mainController?command=go_to_personal_page_orders"/>
            </c:when>
            <c:otherwise>
                <div id="user-logo" class="rowing-left">
                    <p>${sessionScope.user.login.charAt(0)}</p>
                </div>
                <h3>${hello} ${sessionScope.user.login}</h3>
                <p id="registration-date">${registration_date}: <mytag:dateFormatTag
                        localDateTime="${sessionScope.user.registrationDate}"/></p>
                <p>${role}: ${sessionScope.user.role} • ${rating}: ${sessionScope.user.rating}
                    • ${discount} <fmt:formatNumber type="number"
                                                    maxFractionDigits="0"
                                                    value="${sessionScope.user.discount}"/>%</p>

                <div class="user-data">
                    <div id="user-menu" class="clear">
                        <c:if test="${sessionScope.user.role eq 'admin'}">
                            <div class="user-menu-tab admin-tab rowing-left <c:if test="${command eq 'go_to_personal_page_all_orders'}">selected-tab</c:if>">
                                <a href="mainController?command=go_to_personal_page_all_orders">${all_orders}</a>
                            </div>
                            <div class="user-menu-tab admin-tab rowing-left <c:if test="${command eq 'go_to_personal_page_all_users'}">selected-tab</c:if>">
                                <a href="mainController?command=go_to_personal_page_all_users">${all_users}</a>
                            </div>
                        </c:if>
                        <div class="user-menu-tab rowing-left <c:if test="${command eq 'go_to_personal_page_orders'}">selected-tab</c:if>">
                            <a href="mainController?command=go_to_personal_page_orders">${orders_title}</a>
                        </div>
                        <div class="user-menu-tab rowing-left <c:if test="${command eq 'go_to_personal_page_details'}">selected-tab</c:if>">
                            <a href="mainController?command=go_to_personal_page_details">${details_title}</a>
                        </div>
                        <div class="user-menu-tab rowing-left <c:if test="${command eq 'go_to_personal_page_passport'}">selected-tab</c:if>">
                            <a href="mainController?command=go_to_personal_page_passport">${passport_data}</a>
                        </div>
                        <div class="user-menu-tab rowing-left
                            <c:if test="${command eq 'go_to_personal_page_bankcards'}">selected-tab</c:if>
                            <c:if test="${command eq 'go_to_personal_page_create_bankcard'}">selected-tab</c:if>">
                            <a href="mainController?command=go_to_personal_page_bankcards">${bankcards_title}</a>
                        </div>
                    </div>
                    <div id="user-menu-content">
                        <c:choose>
                            <c:when test="${command eq 'go_to_personal_page_orders'}">
                                <%@include file="orders.jsp" %>
                            </c:when>
                            <c:when test="${command eq 'go_to_personal_page_details'}">
                                <%@include file="userDetails.jsp" %>
                            </c:when>
                            <c:when test="${command eq 'go_to_personal_page_passport'}">
                                <%@include file="passport.jsp" %>
                            </c:when>
                            <c:when test="${command eq 'go_to_personal_page_bankcards'}">
                                <p class="link-add-bankcard">
                                    <a href="mainController?command=go_to_personal_page_create_bankcard">&#10010; ${bankcard_adding_title}</a>
                                </p>
                                <%@include file="bankcards.jsp" %>
                            </c:when>
                            <c:when test="${command eq 'go_to_personal_page_create_bankcard'}">
                                <%@include file="createBankcard.jsp" %>
                            </c:when>
                            <c:when test="${command eq 'go_to_personal_page_all_orders'}">
                                <%@include file="allOrders.jsp" %>
                            </c:when>
                            <c:when test="${command eq 'go_to_personal_page_all_users'}">
                                <%@include file="allUsers.jsp" %>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>

    </div>
</div>
<%--END MAIN-CONTENT--%>

<%@include file="../footer.jsp" %>