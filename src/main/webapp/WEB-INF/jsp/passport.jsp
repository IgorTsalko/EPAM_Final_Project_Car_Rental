<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="passport" value="${requestScope.user_passport}"/>

<fmt:message key="user.surname" var="surname"/>
<fmt:message key="user.name" var="name"/>
<fmt:message key="user.thirdname" var="thirdname"/>
<fmt:message key="user.date_of_birth" var="date_of_birth"/>
<fmt:message key="user.address" var="address"/>
<fmt:message key="user.passport.serial_and_number" var="passport_serial_and_number"/>
<fmt:message key="user.passport.issued_by" var="passport_issued_by"/>
<fmt:message key="user.passport.date_of_issue" var="passport_date_of_issue"/>
<fmt:message key="error.data_retrieve" var="data_retrieve_error"/>

<c:choose>
    <c:when test="${pageContext.request.getAttribute(\"message\") eq 'data_retrieve_error'}">
        <p class="data-error">${data_retrieve_error}</p>
    </c:when>
    <c:otherwise>
        <p><strong>${surname}:</strong> ${passport.passportUserSurname}</p>
        <p><strong>${name}:</strong> ${passport.passportUserName}</p>
        <p><strong>${thirdname}:</strong> ${passport.passportUserThirdName}</p>
        <p><strong>${date_of_birth}:</strong> ${passport.passportUserDateOfBirth}</p>
        <p><strong>${address}:</strong> ${passport.passportUserAddress}</p>
        <p><strong>${passport_serial_and_number}:</strong> ${passport.passportSeries} ${passport.passportNumber}</p>
        <p><strong>${passport_issued_by}:</strong> ${passport.passportIssuedBy}</p>
        <p><strong>${passport_date_of_issue}:</strong> ${passport.passportDateOfIssue}</p>
    </c:otherwise>
</c:choose>
