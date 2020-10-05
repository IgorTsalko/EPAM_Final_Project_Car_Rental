<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="header.jsp"%>

<fmt:message key="error_404_title" var="error_404_title"/>
<fmt:message key="go_to_main_page" var="go_to_main_page"/>

<%--START MAIN-CONTENT--%>
<div id="content">
    <div class="container main-content">
        <div class="code404">
            <img src="${pageContext.request.contextPath}/img/page500.png" alt="505">
        </div>
    </div>
</div>
<%--END MAIN-CONTENT--%>

<%@include file="footer.jsp"%>