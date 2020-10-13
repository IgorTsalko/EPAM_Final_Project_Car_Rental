<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<fmt:message key="error.data_retrieve" var="data_retrieve_error"/>
<fmt:message key="card.not_exists" var="card_not_exists"/>

<c:choose>
    <c:when test="${requestScope.message eq 'data_retrieve_error'}">
        <p class="data-error">${data_retrieve_error}</p>
    </c:when>
    <c:when test="${requestScope.user_card_accounts.size() lt 1}">
        <p class="message">${card_not_exists}</p>
    </c:when>
    <c:otherwise>
        <c:forEach items="${requestScope.user_card_accounts}" var="card_account">
            <div class="card">
                <p>${fn:substring(card_account, 0, 4)} ●●●● ●●●● ${fn:substring(card_account, 12, 16)}</p>
            </div>
        </c:forEach>
    </c:otherwise>
</c:choose>
