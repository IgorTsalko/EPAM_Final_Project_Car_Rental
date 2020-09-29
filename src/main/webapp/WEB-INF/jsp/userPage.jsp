<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="header.jsp"%>

<fmt:message key="user.page.title" var="user_page_title"/>
<fmt:message key="user.id" var="user_id"/>
<fmt:message key="user.role" var="user_role"/>
<fmt:message key="user.rating" var="user_rating"/>

<%--START MAIN-CONTENT--%>
<div id="content">
    <div class="container main-content">

        <h1>${user_page_title}</h1>

        <c:if test="${sessionScope.user == null}">
            <c:redirect url="mainController?command=go_to_main_page"/>
        </c:if>

        <c:if test="${sessionScope.user != null}">
            ${hello} <strong>${sessionScope.user.login}</strong>
            <br>
            <p>${user_id}: ${sessionScope.user.id}</p>
            <p>${user_role}: ${sessionScope.user.role}</p>
            <p>${user_rating}: ${sessionScope.user.rating}</p>
            <br>
            <a href="mainController?command=logout">${log_out}</a>
        </c:if>

    </div>
</div>
<%--END MAIN-CONTENT--%>

<%@include file="footer.jsp"%>