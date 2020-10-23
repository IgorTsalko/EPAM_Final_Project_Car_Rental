<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="header.jsp"%>

<fmt:message key="error_500_title" var="error_500_title"/>
<fmt:message key="error_500_desc" var="error_500_desc"/>
<fmt:message key="go_to_main_page" var="go_to_main_page"/>

<%--START MAIN-CONTENT--%>
<div id="content">
    <div class="container main-content">
        <div class="error-page-block">
            <img src="img/page500.png" alt="500">
            <h2>${error_500_title}</h2>
            <p>${error_500_desc}</p>
            <a href="${pageContext.request.contextPath}"><p>${go_to_main_page}</p></a>
        </div>
    </div>
</div>
<%--END MAIN-CONTENT--%>

<%@include file="footer.jsp"%>