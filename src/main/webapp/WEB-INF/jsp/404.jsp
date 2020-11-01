<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="header.jsp"%>

<fmt:message key="error_404_title" var="error_404_title"/>
<fmt:message key="go_to_main_page" var="go_to_main_page"/>
<title>${error_404_title}</title>

<%--START MAIN-CONTENT--%>
<div id="content">
    <div class="container main-content">
        <div class="code404 error-page-block">
            <img src="img/page404.png" alt="404">
            <h2>${error_404_title}</h2>
            <a href="${pageContext.request.contextPath}">
                <p>${go_to_main_page}</p>
            </a>
        </div>
    </div>
</div>
<%--END MAIN-CONTENT--%>

<%@include file="footer.jsp"%>