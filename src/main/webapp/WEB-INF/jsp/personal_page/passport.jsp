<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="passport" value="${requestScope.user_passport}"/>

<fmt:message key="passport.surname" var="surname"/>
<fmt:message key="passport.name" var="name"/>
<fmt:message key="passport.thirdname" var="thirdname"/>
<fmt:message key="passport.date_of_birth" var="date_of_birth"/>
<fmt:message key="passport.address" var="address"/>
<fmt:message key="passport.serial_and_number" var="passport_serial_and_number"/>
<fmt:message key="passport.issued_by" var="passport_issued_by"/>
<fmt:message key="passport.date_of_issue" var="passport_date_of_issue"/>

<fmt:message key="data.edit" var="data_edit"/>
<fmt:message key="data.confirm" var="data_confirm"/>
<fmt:message key="error.data_retrieve" var="data_retrieve_error"/>
<c:set var="passport" value="${requestScope.user_passport}"/>

<c:choose>
    <c:when test="${requestScope.message eq 'data_retrieve_error'}">
        <p class="data-error">${data_retrieve_error}</p>
    </c:when>
    <c:otherwise>
        <div class="user-details clear">
            <button class="edit-button"  id="passport-edit"
                    onclick="enableEditing('user-passport', 'passport-post', 'passport-edit')">
                    ${data_edit}
            </button>
            <button class="edit-button confirm-button" style="display: none;" id="passport-post" form="user-passport" type="submit">
                    ${data_confirm}
            </button>
            <form id="user-passport" action="mainController" method="post">
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
                        <input type="text" name="user_passport_surname" value="${passport.passportUserSurname}" disabled="">
                    </div>
                    <div class="data-field">
                        <input type="text" name="user_passport_name" value="${passport.passportUserName}" disabled="">
                    </div>
                    <div class="data-field">
                        <input type="text" name="user_passport_thirdname" value="${passport.passportUserThirdName}" disabled="">
                    </div>
                    <div class="data-field">
                        <input type="text" name="user_passport_date_of_birth" value="${passport.passportUserDateOfBirth}" disabled="">
                    </div>
                    <div class="data-field">
                        <input type="text" name="user_passport_address" value="${passport.passportUserAddress}" disabled="">
                    </div>
                    <div class="data-field">
                        <input style="width: 32px;" type="text" name="user_passport_series" value="${passport.passportSeries}" disabled="">
                        <input style="width: 263px; padding-right: 0; margin-right: 0;"
                               type="number" name="user_passport_number" value="${passport.passportNumber}" disabled="">
                    </div>
                    <div class="data-field">
                        <input type="text" name="user_passport_issued_by" value="${passport.passportIssuedBy}" disabled="">
                    </div>
                    <div class="data-field">
                        <input type="text" name="user_passport_date_of_issue" value="${passport.passportDateOfIssue}" disabled="">
                    </div>
                </div>
            </form>
        </div>
    </c:otherwise>
</c:choose>
