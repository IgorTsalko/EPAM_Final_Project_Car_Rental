<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="header.jsp"%>
<title>${log_in}</title>

<c:if test="${not empty sessionScope.user}">
    <c:redirect url="mainController?command=go_to_main_page"/>
</c:if>

<fmt:message key="login" var="form_login"/>
<fmt:message key="password" var="form_password"/>
<fmt:message key="login.wrong_data" var="wrong_data"/>
<fmt:message key="data.incorrect_data" var="incorrect_data"/>
<fmt:message key="data.login_updated" var="login_updated"/>
<fmt:message key="login.error" var="login_error"/>
<fmt:message key="login_info" var="registration_info"/>
<fmt:message key="login.registration_link" var="registration_link"/>

<c:set var="message_authorization"
       value="${pageContext.request.getParameter(\"message_authorization\")}"/>

<%--START MAIN-CONTENT--%>
<div id="content">
    <div class="container main-content">

        <div class="login-block">
            <h1>${log_in}</h1>
            <div class="login-form">
                <form action="mainController" method="post">
                    <input type="hidden" name="command" value="authorization">
                    <input type="text" pattern="^[0-9a-zA-Z-+_]{3,25}$" name="login" placeholder="${form_login}" required>
                    <input type="password" pattern="^[^\s]{6,18}$" name="password" placeholder="${form_password}" required>
                    <button type="submit">${log_in}</button>
                </form>
                <c:if test="${pageContext.request.getParameter(\"message_update_login\") eq 'successfully'}">
                    <p class="data-updated login-updated">${login_updated}</p>
                </c:if>
                <c:if test="${not empty message_authorization}">
                    <c:choose>
                        <c:when test="${message_authorization eq 'wrong_data'}">
                            <p class="error-message">${wrong_data}</p>
                        </c:when>
                        <c:when test="${message_authorization eq 'incorrect_data'}">
                            <p class="error-message">${incorrect_data}</p>
                        </c:when>
                        <c:when test="${message_authorization eq 'error'}">
                            <p class="error-message">${login_error}</p>
                        </c:when>
                    </c:choose>
                </c:if>
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