<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<%@include file="../header.jsp"%>

<fmt:message key="phone" var="phone"/>
<fmt:message key="email" var="email"/>
<fmt:message key="general_information" var="general_information"/>
<fmt:message key="data.edit" var="data_edit"/>
<fmt:message key="data.confirm" var="data_confirm"/>

<fmt:message key="user.role" var="role"/>
<fmt:message key="user.rating" var="rating"/>
<fmt:message key="user.discount" var="discount"/>
<fmt:message key="user.orders" var="orders_title"/>
<fmt:message key="user.passport_data" var="passport_data"/>
<fmt:message key="user.cards" var="cards"/>
<fmt:message key="user.registration_date" var="registration_date"/>
<c:set var="user_details" value="${requestScope.user_details}"/>

<fmt:message key="passport.surname" var="surname"/>
<fmt:message key="passport.name" var="name"/>
<fmt:message key="passport.thirdname" var="thirdname"/>
<fmt:message key="passport.date_of_birth" var="date_of_birth"/>
<fmt:message key="passport.address" var="address"/>
<fmt:message key="passport.serial_and_number" var="passport_serial_and_number"/>
<fmt:message key="passport.issued_by" var="passport_issued_by"/>
<fmt:message key="passport.date_of_issue" var="passport_date_of_issue"/>
<c:set var="passport" value="${requestScope.user_passport}"/>

<fmt:message key="card.not_exist_2" var="not_exist_2"/>

<c:set var="command" value="${pageContext.request.getParameter(\"command\")}"/>

<%--START MAIN-CONTENT--%>
<div id="content" class="clear">
    <div class="container main-content">

        <div class="user-details clear">
            <div id="user-data-title">
                <h1>User: ${user_details.userLogin} • ID: ${user_details.userID}</h1>
                <p>${registration_date}: <fmt:formatDate pattern="yyyy-MM-dd / HH:mm"
                                                         value="${user_details.userRegistrationDate}"/></p>
            </div>
        </div>
            <hr>
        <div class="user-details clear">
            <h3>${general_information}</h3>
            <button class="edit-button"  id="desc-edit"
                    onclick="enableEditing('user-desc', 'desc-post', 'desc-edit', 'passport-edit')">
                ${data_edit}
            </button>
            <button class="edit-button confirm-button"  style="display: none;" id="desc-post" form="user-desc" type="submit">
                ${data_confirm}
            </button>

            <form id="user-desc" action="mainController" method="post">
                <div class="data-labels rowing-left">
                    <div class="data-field">
                        <p>${role}:</p>
                    </div>
                    <div class="data-field">
                        <p>${rating}:</p>
                    </div>
                    <div class="data-field">
                        <p>${phone}:</p>
                    </div>
                    <div class="data-field">
                        <p>${email}:</p>
                    </div>
                </div>
                <div class="user-data-fields rowing-left">
                    <div class="data-field">
                        <input type="text" name="user_role" value="${user_details.userRole}" disabled="">
                    </div>
                    <div class="data-field">
                        <input type="text" name="user_rating" value="${user_details.userRating}" disabled="">
                    </div>
                    <div class="data-field">
                        <input type="tel" name="user_phone" value="${user_details.userPhone}" disabled="">
                    </div>
                    <div class="data-field">
                        <input type="email" name="user_email" value="${user_details.userEmail}" disabled="">
                    </div>
                </div>
            </form>
        </div>
        <hr>
        <div class="user-details clear">
            <h3>${passport_data}</h3>

            <button class="edit-button"  id="passport-edit"
                    onclick="enableEditing('user-passport', 'passport-post', 'passport-edit', 'desc-edit')">
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
        <hr>
        <div class="user-details">
            <h3>${cards}</h3>
            <c:choose>
                <c:when test="${requestScope.user_card_accounts.size() lt 1}">
                    <p>${not_exist_2}</p>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${requestScope.user_card_accounts}" var="card_account">
                        <p>${fn:substring(card_account, 0, 4)} ●●●● ●●●● ${fn:substring(card_account, 12, 16)}</p>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>

    </div>
</div>
<%--END MAIN-CONTENT--%>

<%@include file="../footer.jsp"%>