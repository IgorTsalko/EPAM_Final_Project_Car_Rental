<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://localhost:8080/CarRentalFinalProjectJWD/mytag" prefix="mytag"%>

<fmt:message key="data.retrieve_error" var="data_retrieve_error"/>
<fmt:message key="data.delete_error" var="data_delete_error"/>
<fmt:message key="warning.delete" var="delete_warning"/>
<fmt:message key="bankcard.message.not_exists" var="bankcard_message_not_exists"/>

<c:set var="previous_command" value="${pageContext.request.getParameter(\"command\")}"/>
<c:set var="param_user_id" value="${pageContext.request.getParameter(\"user_id\")}"/>

<c:set var="message_bankcard_delete" value="${pageContext.request.getParameter(\"message_bankcard_delete\")}"/>
<c:set var="message_bankcards" value="${pageContext.request.getParameter(\"message_bankcards\")}"/>

<c:choose>
    <c:when test="${requestScope.message_bankcards eq 'data_retrieve_error'}">
        <p class="data-error">${data_retrieve_error}</p>
    </c:when>
    <c:when test="${requestScope.bankcard_numbers.size() lt 1}">
        <p class="message">${bankcard_message_not_exists}</p>
    </c:when>
    <c:when test="${message_bankcard_delete eq 'data_delete_error'}">
        <p class="data-error">${data_delete_error}</p>
    </c:when>
    <c:otherwise>
        <form action="mainController" method="post" onsubmit="return confirmAction('${delete_warning}')">
            <input type="hidden" name="command" value="delete_bankcard">
            <input type="hidden" name="sender_login" value="${sessionScope.user.login}">
            <input type="hidden" name="previous_command" value="${previous_command}">
            <c:choose>
                <c:when test="${not empty param_user_id}">
                    <input type="hidden" name="user_id" value="${param_user_id}">
                </c:when>
                <c:otherwise>
                    <input type="hidden" name="user_id" value="${sessionScope.user.id}">
                </c:otherwise>
            </c:choose>
            <c:forEach items="${requestScope.bankcard_numbers}" var="bankcard_number">
                <div class="bankcard">
                    <div class="bankcard-delete">
                        <button type="submit" name="bankcard_number" value="${bankcard_number}">&#10006;</button>
                    </div>
                    <div class="bankcard-image"><mytag:paymentSystem cardNumber="${bankcard_number}"/></div>
                    <div class="bankcard-number">
                        <p>${fn:substring(bankcard_number, 0, 4)} ●●●● ●●●● ${fn:substring(bankcard_number, 12, 16)}</p>
                    </div>
                </div>
            </c:forEach>
        </form>
    </c:otherwise>
</c:choose>
