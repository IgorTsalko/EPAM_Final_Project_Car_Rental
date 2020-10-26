<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<fmt:message key="data.add_error" var="add_error"/>
<fmt:message key="data.incorrect_data" var="incorrect_data"/>
<fmt:message key="bankcard.message.exists" var="card_exists"/>

<fmt:message key="bankcard.adding_title" var="bankcard_adding_title"/>
<fmt:message key="bankcard.number_title" var="bankcard_number_title"/>
<fmt:message key="bankcard.valid_true_title" var="bankcard_valid_true_title"/>
<fmt:message key="bankcard.month_title" var="bankcard_month_title"/>
<fmt:message key="bankcard.year_title" var="bankcard_year_title"/>
<fmt:message key="bankcard.owner_title" var="bankcard_owner_title"/>
<fmt:message key="surname" var="surname_title"/>
<fmt:message key="name" var="name_title"/>
<fmt:message key="bankcard.data_retention_guarantee" var="data_retention_guarantee"/>
<fmt:message key="save_title" var="save_title"/>
<fmt:message key="cancel_title" var="cancel_title"/>

<c:set var="message_bankcard_adding" value="${pageContext.request.getParameter(\"message_bankcard_adding\")}"/>

<h2>${bankcard_adding_title}</h2>
<c:choose>
    <c:when test="${message_bankcard_adding eq 'data_add_error'}">
        <p class="data-error">${add_error}</p>
    </c:when>
    <c:when test="${message_bankcard_adding eq 'incorrect_data'}">
        <p class="incorrect-data-error">${incorrect_data}</p>
    </c:when>
    <c:when test="${message_bankcard_adding eq 'bankcard_exists'}">
        <p class="incorrect-data-error">${card_exists}</p>
    </c:when>
</c:choose>
<div class="clear">
    <form id="adding-bankcard" action="mainController" method="post">
        <input type="hidden" name="command" value="add_bankcard">
        <input type="hidden" name="sender_login" value="${sessionScope.user.login}">
        <input type="hidden" name="user_id" value="${sessionScope.user.id}">
        <div id="front-side-bankcard">
            <div id="bankcard-number">
                <div>
                    <label>${bankcard_number_title}</label>
                </div>
                <input type="text" name="bankcard_number" placeholder="0000000000000000"
                       maxlength="16" minlength="16" pattern="^[\d]+$" autofocus required >
            </div>
            <div id="bankcard-valid-true">
                <div>
                    <label>${bankcard_valid_true_title}</label>
                </div>
                <input type="text" name="bankcard_valid_true_month" pattern="^[\d]+$"
                       placeholder="${bankcard_month_title}" maxlength="2" max="12" min="1" required>
                <input type="text" name="bankcard_valid_true_year" pattern="^[\d]+$"
                       placeholder="${bankcard_year_title}" maxlength="2" required>
            </div>
            <div id="bankcard-owner">
                <div>
                    <label>${bankcard_owner_title}</label>
                </div>
                <input type="text" name="bankcard_firstname" placeholder="${name_title}"
                       pattern="^[a-zA-Z]+$" maxlength="50" minlength="3" required>
                <input type="text" name="bankcard_lastname" placeholder="${surname_title}"
                       pattern="^[a-zA-Z]+$" maxlength="50" minlength="3" required>
            </div>
        </div>
        <div id="back-side-bankcard">
            <div id="bankcard-strip"></div>
            <div id="bankcard-cvv">
                <div>
                    <label>CVV</label>
                </div>
                <input type="text" name="bankcard_cvv" placeholder="000"
                       maxlength="3" minlength="3" pattern="^[\d]+$" required>
            </div>
        </div>
    </form>
</div>
<div id="add-bankcard-button">
    <p>${data_retention_guarantee}</p>
    <button type="submit" form="adding-bankcard">${save_title}</button>
    <button type="reset" form="adding-bankcard" class="cancel-button">${cancel_title}</button>
</div>


