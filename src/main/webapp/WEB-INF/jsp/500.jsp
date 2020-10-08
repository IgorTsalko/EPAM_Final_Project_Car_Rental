<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="header.jsp"%>

<fmt:message key="error_500_title" var="error_500_title"/>
<fmt:message key="error_500_desc" var="error_500_desc"/>
<fmt:message key="stack-trace-title" var="stack_trace_title"/>
<fmt:message key="go_to_main_page" var="go_to_main_page"/>

<%--START MAIN-CONTENT--%>
<div id="content">
    <div class="container main-content">
        <div class="error-page-block">
            <img src="${pageContext.request.contextPath}/img/page500.png" alt="500">
            <h2>${error_500_title}</h2>
            <p>${error_500_desc}</p>
            <a href="${pageContext.request.contextPath}">
                <p>${go_to_main_page}</p>
            </a>
        </div>
        <c:if test="${not empty pageContext.request.getAttribute(\"exception\")}">
            <div id="stack-trace">
                <h4>${stack_trace_title}</h4>
                <p>Exception: ${requestScope.exception}</p>
                <p>
                    <c:forEach items="${requestScope.exception.stackTrace}" var="trace">
                        ${trace}<br>
                    </c:forEach>
            </div>
        </c:if>
    </div>
</div>
<%--END MAIN-CONTENT--%>

<%@include file="footer.jsp"%>