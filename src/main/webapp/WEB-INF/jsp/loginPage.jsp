<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="header.jsp"%>

<fmt:message key="form.login" var="login"/>
<fmt:message key="form.password" var="password"/>
<fmt:message key="login.wrong_data" var="wrong_data"/>
<fmt:message key="login.error" var="bd_error"/>
<fmt:message key="login_info" var="registration_info"/>
<fmt:message key="login.registration_link" var="registration_link"/>

<%--START MAIN-CONTENT--%>
<div id="content">
    <div class="container main-content">

        <div class="login-block">
            <h1>${log_in}</h1>
            <div class="login-form">
                <form action="mainController" method="post">
                    <input type="hidden" name="command" value="authorization">
                    <input type="text" name="login" placeholder="${login}">
                    <input type="password" name="password" placeholder="${password}">
                    <button type="submit">${log_in}</button>
                    <c:if test="${sessionScope.authorization_message eq 'wrong_data'}">
                        <p class="error-message">${wrong_data}</p>
                    </c:if>
                    <c:if test="${sessionScope.authorization_message eq 'bd_error'}">
                        <p class="error-message">${bd_error}</p>
                    </c:if>
                </form>
            </div>
            <div class="info-login-block">
                <p>
                    ${registration_info} <a href="mainController?command=go_to_registration_page">
                    ${registration_link}</a>
                </p>
            </div>
        </div>

    </div>
</div>
<%--END MAIN-CONTENT--%>

<%@include file="footer.jsp"%>