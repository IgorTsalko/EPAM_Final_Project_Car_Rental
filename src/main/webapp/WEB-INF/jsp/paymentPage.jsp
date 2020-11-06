<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://localhost:8080/CarRentalFinalProjectJWD/mytag" prefix="mytag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@include file="header.jsp" %>

<fmt:message key="data.retrieve_error" var="data_retrieve_error"/>
<fmt:message key="data.incorrect_data" var="incorrect_data"/>
<fmt:message key="data.create_error" var="create_error"/>
<fmt:message key="data.successfully" var="successfully"/>

<fmt:message key="surname" var="surname"/>
<fmt:message key="name" var="name"/>

<fmt:message key="payment_title" var="payment_title"/>
<fmt:message key="payment_order" var="payment_order"/>
<fmt:message key="payment.total_sum" var="total_sum"/>
<fmt:message key="payment.use_another_bankcard" var="use_another_bankcard"/>
<fmt:message key="payment.use_linked_cards" var="use_linked_cards"/>
<fmt:message key="payment.to_pay" var="to_pay"/>
<fmt:message key="payment.skip" var="skip"/>

<fmt:message key="bankcard.adding_title" var="bankcard_adding_title"/>
<fmt:message key="bankcard.number_title" var="bankcard_number_title"/>
<fmt:message key="bankcard.valid_true_title" var="bankcard_valid_true_title"/>
<fmt:message key="bankcard.month_title" var="bankcard_month_title"/>
<fmt:message key="bankcard.year_title" var="bankcard_year_title"/>
<fmt:message key="bankcard.owner_title" var="bankcard_owner_title"/>
<fmt:message key="bankcard.adding_title" var="adding_bankcard"/>

<c:set var="message_create_order"
       value="${pageContext.request.getParameter(\"message_create_order\")}"/>

<c:set var="order" value="${requestScope.order}"/>
<c:set var="bankcards" value="${requestScope.bankcards}"/>

<title>${payment_title} ${requestScope.car.brand} ${requestScope.car.model} ${requestScope.car.yearProduction}</title>

<%--START MAIN-CONTENT--%>
<div id="content">
    <div class="container main-content">
        <c:choose>
            <c:when test="${requestScope.message_create_order eq 'data_retrieve_error'}">
                <p class="data-error">${data_retrieve_error}</p>
            </c:when>
            <c:otherwise>
                <div id="order-payment">

                    <div class="order-payment-title">
                        <span class="heading3">${payment_order} №${order.orderId}</span>
                        <span class="heading2">&#8221;${order.car.brand} ${order.car.model} ${order.car.yearProduction}&#8221;</span>
                        <span class="heading3">${total_sum} <fmt:formatNumber minFractionDigits="2" value="${order.totalSum}"/> BYN</span>
                    </div>

                    <div class="bankcards">
                        <label id="use-another-bankcard">${use_another_bankcard}</label>

                        <c:forEach items="${bankcards}" var="bankcard">
                            <div class="bankcard">
                                <div class="select-card">
                                    <input type="radio" name="bankcard_id" form="pay-order-form"
                                           value="${bankcard.bankcardID}" required>
                                </div>
                                <div class="bankcard-image">
                                    <mytag:paymentSystem cardNumber="${bankcard.bankcardNumber}"/>
                                </div>
                                <div class="bankcard-number">
                                    <p>${fn:substring(bankcard.bankcardNumber, 0, 4)} ●●●●
                                        ●●●● ${fn:substring(bankcard.bankcardNumber, 12, 16)}</p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <div class="another-bankcard">
                        <label id="use-linked-cards">${use_linked_cards}</label>
                        <div id="adding-bankcard" class="clearfix">
                            <input type="hidden" name="use_another_bankcard" value="true" disabled>
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

                        <div class="add-bankcard">
                            <input type="checkbox" id="add-bankcard" name="add_bankcard"
                                   form="pay-order-form" value="true">
                            <label for="add-bankcard">${adding_bankcard}</label>
                        </div>
                    </div>

                    <div class="payment-buttons">
                        <form id="pay-order-form" action="mainController" method="post" class="rowing-left">
                            <input type="hidden" name="command" value="pay_order">
                            <input type="hidden" name="order_id" value="${order.orderId}">
                            <button>${to_pay}</button>
                        </form>

                        <form action="mainController">
                            <input type="hidden" name="command" value="go_to_personal_page_orders">
                            <button class="skip-button">${skip}</button>
                        </form>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<%--END MAIN-CONTENT--%>
<script type="text/javascript" async="" src="js/payment.js"></script>

<%@include file="footer.jsp" %>