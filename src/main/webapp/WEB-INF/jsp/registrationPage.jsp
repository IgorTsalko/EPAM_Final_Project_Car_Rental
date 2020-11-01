<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="header.jsp"%>
<title>${registration}</title>

<fmt:message key="email" var="email"/>
<fmt:message key="phone" var="phone"/>
<fmt:message key="login" var="form_login"/>
<fmt:message key="password" var="form_password"/>
<fmt:message key="register" var="form_register"/>
<fmt:message key="reg.successful" var="reg_successful"/>
<fmt:message key="reg.error" var="reg_error"/>
<fmt:message key="data.incorrect_data" var="incorrect_data"/>
<fmt:message key="reg_info.text.start" var="info_text_start"/>
<fmt:message key="reg_info.text.link" var="login_page"/>
<fmt:message key="reg_info.text.end" var="info_text_end"/>
<fmt:message key="reg.user_exists" var="reg_user_exists"/>
<fmt:message key="reg.agreement" var="reg_agreement"/>
<fmt:message key="reg.agreement.link" var="reg_agreement_link"/>

<c:if test="${not empty sessionScope.user}">
    <c:redirect url="mainController?command=go_to_main_page"/>
</c:if>

<%--START MAIN-CONTENT--%>
<div id="content">
    <div class="container main-content">

        <div class="registration-block">
            <h1>${registration}</h1>
            <div class="registration-form">
                <form action="mainController" method="post">
                    <input type="hidden" name="command" value="registration">
                    <input name="login" type="text" pattern="^[^\s$/()]+$" maxlength="25" minlength="3"
                           placeholder="* ${form_login}" required>
                    <input name="password" type="password" pattern="^[^\s]+$" maxlength="18" minlength="6"
                           placeholder="* ${form_password}" required>
                    <input name="phone" type="tel" pattern="^[0-9\s-+()]+$" maxlength="30" minlength="7"
                           placeholder="* ${phone}" required>
                    <input name="email" type="email" pattern="^[^\s]+@[^\s]+\.[^\s]+$" maxlength="30" minlength="3"
                           placeholder="  ${email}">
                    <button id="registration_button" class="inactive" type="submit" disabled>${form_register}</button>
                </form>
                <c:choose>
                    <c:when test="${pageContext.request.getParameter(\"message_registration\") eq 'successful'}">
                        <h3>${reg_successful}</h3>
                        <p>${info_text_start}
                            <a href="mainController?command=go_to_login_page">${login_page}</a>
                                ${info_text_end}
                        </p>
                    </c:when>
                    <c:when test="${pageContext.request.getParameter(\"message_registration\") eq 'user_exists'}">
                        <p class="error-message">${reg_user_exists}</p>
                    </c:when>
                    <c:when test="${pageContext.request.getParameter(\"message_registration\") eq 'error'}">
                        <p class="error-message">${reg_error}</p>
                    </c:when>
                    <c:when test="${pageContext.request.getParameter(\"message_registration\") eq 'incorrect_data'}">
                        <p class="error-message">${incorrect_data}</p>
                    </c:when>
                </c:choose>
            </div>
            <div class="info-registration-block">
                <label class="checkbox1">
                    <input type="checkbox" id="politics" onclick="check('registration_button');" autocomplete="off">
                    ${reg_agreement} <a href="mainController?command=go_to_login_page">${reg_agreement_link}</a>
                    <span class="checkmark"></span>
                </label>
            </div>
        </div>

    </div>
</div>
<%--END MAIN-CONTENT--%>

<%@include file="footer.jsp"%>
