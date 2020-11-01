<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://localhost:8080/CarRentalFinalProjectJWD/mytag" prefix="mytag"%>

<%@include file="../header.jsp"%>

<fmt:message key="general_information" var="general_information"/>
<fmt:message key="user.role" var="role"/>
<fmt:message key="user.rating" var="rating"/>
<fmt:message key="user.discount" var="discount"/>
<fmt:message key="user.orders" var="orders_title"/>
<fmt:message key="user.passport_data" var="passport_data"/>
<fmt:message key="user.bankcards_title" var="bankcards_title"/>
<fmt:message key="user.registration_date" var="registration_date"/>

<c:set var="user_id" value="${pageContext.request.getParameter(\"user_id\")}"/>
<c:set var="command" value="${pageContext.request.getParameter(\"command\")}"/>

<%--START MAIN-CONTENT--%>
<div id="content" class="clear">
    <div class="container main-content">

        <c:choose>
            <c:when test="${(user_id eq '0') || (empty user_id)}">
                <c:redirect url="mainController?command=go_to_main_page"/>
            </c:when>
            <c:otherwise>
                <div class="user-details clear">
                    <div id="user-data-title">
                        <h1>User: ${user_details.userLogin} • ID: ${user_details.userID}</h1>
                        <p>${registration_date}: <mytag:dateFormatTag localDateTime="${user_details.userRegistrationDate}"/></p>
                    </div>
                </div>
                <hr>
                <div class="user-details clear">
                    <h3>${general_information}</h3>
                    <form id="user-desc" action="mainController" method="post">
                        <input type="hidden" name="user_id" value="${user_id}">
                        <%@include file="userDetails.jsp"%>
                    </form>
                </div>
                <hr>
                <div class="user-details clear">
                    <h3>${passport_data}</h3>
                    <form id="user-passport" action="mainController" method="post">
                        <input type="hidden" name="user_id" value="${user_id}">
                        <%@include file="passport.jsp"%>
                    </form>
                </div>
                <hr>
                <div class="user-details">
                    <h3>${bankcards_title}</h3>
                    <%@include file="bankcards.jsp"%>
                </div>
            </c:otherwise>
        </c:choose>

    </div>
</div>
<%--END MAIN-CONTENT--%>

<%@include file="../footer.jsp"%>