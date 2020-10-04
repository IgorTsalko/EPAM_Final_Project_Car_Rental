<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="header.jsp"%>

<%--START MAIN-CONTENT--%>
<div id="content">
    <div class="container main-content">
        <div class="code404">
            <img src="${pageContext.request.contextPath}/img/404.png" alt="404">
            <h2>Извините, страница не найдена!</h2>
            <a href="${pageContext.request.contextPath}">
                <p>вернуться на главную</p>
            </a>
        </div>
    </div>
</div>
<%--END MAIN-CONTENT--%>

<%@include file="footer.jsp"%>