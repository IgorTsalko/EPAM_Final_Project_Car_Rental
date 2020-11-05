<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://localhost:8080/CarRentalFinalProjectJWD/mytag" prefix="mytag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@include file="header.jsp" %>

<fmt:message key="data.retrieve_error" var="data_retrieve_error"/>
<fmt:message key="data.incorrect_data" var="incorrect_data"/>
<fmt:message key="data.create_error" var="create_error"/>
<fmt:message key="data.successfully" var="successfully"/>

<fmt:message key="payment" var="payment_title"/>

<fmt:message key="bankcard.adding_title" var="bankcard_adding_title"/>
<fmt:message key="bankcard.number_title" var="bankcard_number_title"/>
<fmt:message key="bankcard.valid_true_title" var="bankcard_valid_true_title"/>
<fmt:message key="bankcard.month_title" var="bankcard_month_title"/>
<fmt:message key="bankcard.year_title" var="bankcard_year_title"/>
<fmt:message key="bankcard.owner_title" var="bankcard_owner_title"/>

<fmt:message key="order_title" var="order_title"/>
<fmt:message key="order.total" var="total_title"/>
<fmt:message key="order.days" var="days_title"/>
<fmt:message key="order.fill_out_data_in_office" var="fill_out_data_in_office"/>
<fmt:message key="order.update_passport_data_in_office" var="update_passport_data_in_office"/>
<fmt:message key="order.pay_in_office" var="pay_in_office"/>
<fmt:message key="order.use_other_card" var="use_other_card"/>
<fmt:message key="order.add_card" var="add_card"/>
<fmt:message key="order.use_linked_cards" var="use_linked_cards"/>

<fmt:message key="car.nearest_available_date" var="nearest_available_date"/>
<fmt:message key="price_per_day" var="price_per_day"/>
<fmt:message key="pick_up_date" var="pick_up_date"/>
<fmt:message key="drop_off_date" var="drop_off_date"/>
<fmt:message key="to_rent" var="to_rent"/>
<fmt:message key="phone" var="phone"/>
<fmt:message key="email" var="email"/>
<fmt:message key="car.discount_title" var="discount_title"/>

<fmt:message key="user.passport_data" var="passport_data"/>
<fmt:message key="surname" var="surname"/>
<fmt:message key="name" var="name"/>
<fmt:message key="passport.thirdname" var="thirdname"/>
<fmt:message key="passport.date_of_birth" var="date_of_birth"/>
<fmt:message key="passport.address" var="address"/>
<fmt:message key="passport.serial_and_number" var="passport_serial_and_number"/>
<fmt:message key="passport.issued_by" var="passport_issued_by"/>
<fmt:message key="passport.date_of_issue" var="passport_date_of_issue"/>

<c:set var="message_create_order"
       value="${pageContext.request.getParameter(\"message_create_order\")}"/>

<c:set var="user_details" value="${requestScope.user_details}"/>
<c:set var="passport" value="${requestScope.user_passport}"/>

<title>${order_title} ${requestScope.car.brand} ${requestScope.car.model} ${requestScope.car.yearProduction}</title>

<%--START MAIN-CONTENT--%>
<div id="content">
    <div class="container main-content">
        <c:choose>
            <c:when test="${requestScope.message_create_order eq 'data_retrieve_error'}">
                <p class="data-error">${data_retrieve_error}</p>
            </c:when>
            <c:otherwise>
                <div id="car-order">
                    <div class="clearfix">
                        <div id="customer-passport" class="rowing-right">
                            <span class="heading1">${passport_data}</span>
                            <div class="passport-in-office">
                                <input type="checkbox" id="passport-in-office">
                                <label for="passport-in-office">${fill_out_data_in_office}</label>
                            </div>

                            <div class="clearfix">
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
                                               maxlength="50" minlength="3" form="car-rental-form"
                                               value="${passport.passportUserSurname}" required>
                                    </div>
                                    <div class="data-field">
                                        <input type="text" name="user_passport_name" pattern="^[a-zA-Zа-яА-Я]+$"
                                               maxlength="50" minlength="3" form="car-rental-form"
                                               value="${passport.passportUserName}" required>
                                    </div>
                                    <div class="data-field">
                                        <input type="text" name="user_passport_thirdname" pattern="^[a-zA-Zа-яА-Я]+$"
                                               maxlength="50" minlength="3" form="car-rental-form"
                                               value="${passport.passportUserThirdName}" required>
                                    </div>
                                    <div class="data-field">
                                        <input type="date" name="user_passport_date_of_birth" form="car-rental-form"
                                               value="${passport.passportUserDateOfBirth}" required>
                                    </div>
                                    <div class="data-field">
                                        <input type="text" name="user_passport_address" maxlength="255" form="car-rental-form"
                                               value="${passport.passportUserAddress}" required>
                                    </div>
                                    <div class="data-field">
                                        <input style="width: 40px; text-align: center;" type="text" name="user_passport_series" pattern="^[a-zA-Zа-яА-Я]+$"
                                               maxlength="2" minlength="2" form="car-rental-form"
                                               value="${passport.passportSeries}" required>
                                        <input style="width: 305px; padding-right: 0; margin-right: 0;" type="text" name="user_passport_number" maxlength="7"
                                               minlength="7" pattern="^[\d]+$" form="car-rental-form"
                                               value="${passport.passportNumber}" required>
                                    </div>
                                    <div class="data-field">
                                        <input type="text" name="user_passport_issued_by" maxlength="255"
                                               form="car-rental-form" value="${passport.passportIssuedBy}" required>
                                    </div>
                                    <div class="data-field">
                                        <input type="date" name="user_passport_date_of_issue" form="car-rental-form"
                                               value="${passport.passportDateOfIssue}" required>
                                    </div>
                                </div>
                            </div>

                            <div>
                                <input type="checkbox" id="update-passport" name="update_passport"
                                       form="car-rental-form" value="true">
                                <label for="update-passport">${update_passport_data_in_office}</label>
                            </div>
                        </div>

                        <div class="rent-car rowing-left clearfix">
                            <p class="car-title">${requestScope.car.brand} ${requestScope.car.model} ${requestScope.car.yearProduction}</p>
                            <div class="car-price">
                                <c:choose>
                                    <c:when test="${sessionScope.user.discount gt 0}">
                                        <span id="common-price" class="old-price">
                                            <fmt:formatNumber minFractionDigits="2"
                                                              value="${requestScope.car.pricePerDay}"/>
                                        </span>
                                        <span id="discounted-price" class="price new-price">
                                            <mytag:discountTag
                                                    carPrice="${requestScope.car.pricePerDay}"
                                                    discount="${sessionScope.user.discount}"/>
                                        </span>
                                        <span>${price_per_day}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span id="common-price" class="price">
                                            <fmt:formatNumber minFractionDigits="2"
                                                              value="${requestScope.car.pricePerDay}"/>
                                        </span>
                                        <span>${price_per_day}</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div id="total-price">
                                <p>${total_title} <span id="calculated-price"></span> ${days_title}</p>
                            </div>

                            <form id="car-rental-form" action="mainController" method="post">
                                <input type="hidden" name="command" value="create_order">
                                <input type="hidden" name="car_id" value="${requestScope.car.carID}">
                                <div class="rowing-left">
                                    <p>${pick_up_date}</p>
                                    <input id="pickUpDate" type="date" name="pick_up_date"
                                           value="<%=java.time.LocalDate.now()%>" required>
                                </div>
                                <div class="rowing-right">
                                    <p>${drop_off_date}</p>
                                    <input id="dropOffDate" type="date" name="drop_off_date"
                                           value="<%=java.time.LocalDate.now().plusDays(3)%>" required>
                                </div>
                                <div class="rowing-left">
                                    <input type="tel" name="phone" pattern="^[0-9\s-+()]+$" maxlength="30" minlength="7"
                                           placeholder="${phone}" form="car-rental-form"
                                           value="${user_details.userPhone}" required>
                                </div>
                                <div class="rowing-right">
                                    <input type="email" name="email" pattern="^[^\s]+@[^\s]+\.[^\s]+$"
                                           placeholder="${email}" form="car-rental-form" maxlength="30" minlength="3"
                                           value="${user_details.userEmail}">
                                </div>
                                <button type="submit">${to_rent}</button>
                            </form>
                            <p id="available-date">${nearest_available_date}: 2020-10-16</p>
                            <c:choose>
                                <c:when test="${message_create_order eq 'create_order_successfully'}">
                                    <p class="data-updated">${successfully}</p>
                                </c:when>
                                <c:when test="${message_create_order eq 'incorrect_data'}">
                                    <p class="data-error">${incorrect_data}</p>
                                </c:when>
                                <c:when test="${message_create_order eq 'create_order_error'}">
                                    <p class="data-error">${create_error}</p>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>

                    <hr>

                    <div class="user-payment">
                        <span class="heading1">${payment_title}</span>

                        <input type="checkbox" id="payment-in-office" name="pay_in_office" form="car-rental-form" value="true">
                        <label for="payment-in-office">${pay_in_office}</label>

                        <div class="bankcards">
                            <c:forEach items="${requestScope.bankcard_numbers}"
                                       var="bankcard_number">
                                <div class="bankcard">
                                    <div class="select-card">
                                        <input type="radio" name="bankcard_number" form="car-rental-form"
                                               value="${bankcard_number}" required>
                                    </div>
                                    <div class="bankcard-image">
                                        <mytag:paymentSystem cardNumber="${bankcard_number}"/>
                                    </div>
                                    <div class="bankcard-number">
                                        <p>${fn:substring(bankcard_number, 0, 4)} ●●●●
                                            ●●●● ${fn:substring(bankcard_number, 12, 16)}</p>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <label id="use-other-card">${use_other_card}</label>

                    <div class="add-bankcard">
                        <input type="checkbox" id="add-bankcard" name="add_bankcard"
                               form="car-rental-form" value="true">
                        <label for="add-bankcard">${add_card}</label>
                    </div>
                    <div id="adding-bankcard" class="clearfix">
                        <div id="front-side-bankcard">
                            <div id="bankcard-number">
                                <div>
                                    <label>${bankcard_number_title}</label>
                                </div>
                                <input type="text" name="bankcard_number"
                                       placeholder="0000000000000000"
                                       maxlength="16" minlength="16" pattern="^[\d]+$" autofocus
                                       disabled>
                            </div>
                            <div id="bankcard-valid-true">
                                <div>
                                    <label>${bankcard_valid_true_title}</label>
                                </div>
                                <input type="text" name="bankcard_valid_true_month" pattern="^[\d]+$"
                                       placeholder="${bankcard_month_title}" maxlength="2" max="12" min="1" disabled>
                                <input type="text" name="bankcard_valid_true_year" pattern="^[\d]+$"
                                       placeholder="${bankcard_year_title}" maxlength="2" disabled>
                            </div>
                            <div id="bankcard-owner">
                                <div>
                                    <label>${bankcard_owner_title}</label>
                                </div>
                                <input type="text" name="bankcard_firstname" placeholder="${name}"
                                       pattern="^[a-zA-Z]+$" maxlength="50" minlength="3" disabled>
                                <input type="text" name="bankcard_lastname" placeholder="${surname}"
                                       pattern="^[a-zA-Z]+$" maxlength="50" minlength="3" disabled>
                            </div>
                        </div>
                        <div id="back-side-bankcard">
                            <div id="bankcard-strip"></div>
                            <div id="bankcard-cvv">
                                <div>
                                    <label>CVV</label>
                                </div>
                                <input type="text" name="bankcard_cvv" placeholder="000"
                                       maxlength="3" minlength="3" pattern="^[\d]+$" disabled>
                            </div>
                        </div>
                    </div>
                    <label id="use-linked-cards">${use_linked_cards}</label>
                </div>


            </c:otherwise>
        </c:choose>
    </div>
</div>
<%--END MAIN-CONTENT--%>
<script type="text/javascript" async="" src="js/order.js"></script>

<%@include file="footer.jsp" %>