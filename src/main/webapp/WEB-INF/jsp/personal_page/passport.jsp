<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="passport" value="${requestScope.user_passport}"/>

<fmt:message key="surname" var="surname"/>
<fmt:message key="name" var="name"/>
<fmt:message key="passport.thirdname" var="thirdname"/>
<fmt:message key="passport.date_of_birth" var="date_of_birth"/>
<fmt:message key="passport.address" var="address"/>
<fmt:message key="passport.serial_and_number" var="passport_serial_and_number"/>
<fmt:message key="passport.issued_by" var="passport_issued_by"/>
<fmt:message key="passport.date_of_issue" var="passport_date_of_issue"/>

<fmt:message key="data.edit" var="data_edit"/>
<fmt:message key="data.confirm" var="data_confirm"/>
<fmt:message key="data.cancel" var="data_cancel"/>
<fmt:message key="data.retrieve_error" var="data_retrieve_error"/>
<fmt:message key="data.incorrect_data" var="incorrect_data"/>
<fmt:message key="data.retrieve_error" var="data_retrieve_error"/>
<fmt:message key="data.updated" var="data_updated"/>

<c:set var="passport" value="${requestScope.user_passport}"/>
<c:set var="previous_command" value="${pageContext.request.getParameter(\"command\")}"/>
<c:set var="param_user_id" value="${pageContext.request.getParameter(\"user_id\")}"/>
<c:set var="message_passport_update" value="${pageContext.request.getParameter(\"message_passport_update\")}"/>

<form id="user-passport" action="mainController" method="post">
<c:choose>
    <c:when test="${requestScope.message_passport eq 'data_retrieve_error'}">
        <p class="data-error">${data_retrieve_error}</p>
    </c:when>
    <c:otherwise>
        <div>
            <button type="button" name="edit-button" class="edit-button"
                    onclick="enableEditing('user-passport', 'passport-post', 'passport-cancel')">
                    ${data_edit}
            </button>
            <button type="submit" id="passport-post" class="edit-button confirm-button" style="display: none;">
                    ${data_confirm}
            </button>
            <button type="button" id="passport-cancel" class="edit-button" style="display: none;"
                    onclick="disableEditing('user-passport', 'passport-post', 'passport-cancel')">
                    ${data_cancel}
            </button>
        </div>
        <div>
            <c:choose>
                <c:when test="${message_passport_update eq 'data_update_error'}">
                    <p class="update-error">${update_error}</p>
                </c:when>
                <c:when test="${message_passport_update eq 'incorrect_data'}">
                    <p class="incorrect-data-error">${incorrect_data}</p>
                </c:when>
                <c:when test="${message_passport_update eq 'data_updated'}">
                    <p class="data-updated">${data_updated}</p>
                </c:when>
            </c:choose>
        </div>
        <input type="hidden" name="command" value="update_user_passport">
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
        <div class="data-labels rowing-left">
            <div class="data-field">
                <p>${surname}:</p>
            </div>
            <div class="data-field">
                <p>${name}:</p>
            </div>
            <div class="data-field">
                <p>${thirdname}:</p>
            </div>
            <div class="data-field">
                <p>${date_of_birth}:</p>
            </div>
            <div class="data-field">
                <p>${address}:</p>
            </div>
            <div class="data-field">
                <p>${passport_serial_and_number}:</p>
            </div>
            <div class="data-field">
                <p>${passport_issued_by}:</p>
            </div>
            <div class="data-field">
                <p>${passport_date_of_issue}:</p>
            </div>
        </div>
        <div class="user-data-fields rowing-left">
            <div class="data-field">
                <input type="text" name="user_passport_surname" pattern="^[a-zA-Zа-яА-Я]+$"
                       maxlength="50" minlength="3"
                       value="${passport.passportUserSurname}" disabled="" required>
            </div>
            <div class="data-field">
                <input type="text" name="user_passport_name" pattern="^[a-zA-Zа-яА-Я]+$"
                       maxlength="50" minlength="3"
                       value="${passport.passportUserName}" disabled="" required>
            </div>
            <div class="data-field">
                <input type="text" name="user_passport_thirdname" pattern="^[a-zA-Zа-яА-Я]+$"
                       maxlength="50" minlength="3"
                       value="${passport.passportUserThirdName}" disabled="" required>
            </div>
            <div class="data-field">
                <input type="date" name="user_passport_date_of_birth"
                       value="${passport.passportUserDateOfBirth}" disabled="" required>
            </div>
            <div class="data-field">
                <input type="text" name="user_passport_address" maxlength="255"
                       value="${passport.passportUserAddress}" disabled="" required>
            </div>
            <div class="data-field">
                <input style="width: 40px; text-align: center;" type="text" name="user_passport_series" pattern="^[a-zA-Zа-яА-Я]+$"
                       maxlength="2" minlength="2"
                       value="${passport.passportSeries}" disabled="" required>
                <input style="width: 255px; padding-right: 0; margin-right: 0;"
                       type="text" name="user_passport_number" maxlength="7" minlength="7" pattern="^[\d]+$"
                       value="${passport.passportNumber}" disabled="" required>
            </div>
            <div class="data-field">
                <input type="text" name="user_passport_issued_by" maxlength="255"
                       value="${passport.passportIssuedBy}" disabled="" required>
            </div>
            <div class="data-field">
                <input type="date" name="user_passport_date_of_issue"
                       value="${passport.passportDateOfIssue}" disabled="" required>
            </div>
        </div>
    </c:otherwise>
</c:choose>
</form>
