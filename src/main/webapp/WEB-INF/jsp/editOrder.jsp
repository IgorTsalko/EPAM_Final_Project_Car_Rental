<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://localhost:8080/CarRentalFinalProjectJWD/mytag" prefix="mytag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@include file="header.jsp" %>

<fmt:message key="user.all_orders" var="all_orders"/>

<fmt:message key="data.incorrect_data" var="incorrect_data"/>
<fmt:message key="data.update_error" var="update_error"/>
<fmt:message key="data.change" var="change"/>

<fmt:message key="order_title" var="order_title"/>
<fmt:message key="data.edit" var="data_edit"/>
<fmt:message key="data.cancel" var="data_cancel"/>
<fmt:message key="data.editing" var="editing"/>
<fmt:message key="order.status" var="order_status"/>
<fmt:message key="order.car" var="order_car"/>
<fmt:message key="order.comment" var="comment"/>
<fmt:message key="order.status.paid" var="status_paid"/>
<fmt:message key="order.status.not_paid" var="status_not_paid"/>
<fmt:message key="pick_up_date" var="pick_up_date"/>
<fmt:message key="drop_off_date" var="drop_off_date"/>
<fmt:message key="currency" var="currency"/>

<fmt:message key="return_date" var="return_date"/>
<fmt:message key="car_odometer" var="car_odometer"/>
<fmt:message key="car_damage" var="car_damage"/>
<fmt:message key="fine" var="fine"/>

<c:set var="order" value="${requestScope.order}"/>
<c:set var="car" value="${requestScope.order.car}"/>
<c:set var="returnAct" value="${requestScope.order.returnAct}"/>
<c:set var="cars" value="${requestScope.cars}"/>
<c:set var="orderStatuses" value="${requestScope.order_statuses}"/>

<c:set var="message_update_order"
       value="${pageContext.request.getParameter(\"message_update_order\")}"/>

<title>${editing} ${order_title}
    ID: ${order.orderId} ${requestScope.car.brand} ${requestScope.car.model} ${requestScope.car.yearProduction}</title>

<%--START MAIN-CONTENT--%>
<div id="content">
    <div class="container main-content">

        <div id="order-editing">
            <div class="clearfix">

                <h1><span class="page-title">${editing} • ${order_title} ID: ${order.orderId}</span>
                </h1>

                <div class="order-car-img rowing-right">
                    <img src="${car.carImages[0]}">
                </div>

                <div class="order-details rowing-left">
                    <div style="margin-bottom: 20px; margin-top: -15px;">
                        <a href="mainController?command=go_to_personal_page_all_orders">&#9668; ${all_orders}</a>
                        <button style="display: none" id="enable-order-editing" type="button"
                                name="edit-button" class="edit-button">
                            ${data_edit}
                        </button>
                        <button style="display: none;" id="cancel-order-editing" type="button"
                                id="passport-cancel" class="edit-button">
                            ${data_cancel}
                        </button>
                    </div>
                    <span><a
                            href="mainController?command=go_to_all_user_data&user_id=${order.userID}">${order.userLogin}</a></span>
                    <span>•</span>
                    <span><mytag:dateFormatTag localDateTime="${order.orderDate}"/></span>
                    <span>•</span>
                    <span><fmt:formatNumber minFractionDigits="2"
                                            value="${order.totalSum}"/> ${currency}</span>
                    <span>•</span>
                    <span>
                        <c:choose>
                            <c:when test="${order.paid eq true}">
                                ${status_paid}
                            </c:when>
                            <c:otherwise>
                                ${status_not_paid}
                            </c:otherwise>
                        </c:choose>
                    </span>

                    <form id="change-order-form" action="mainController" method="post">
                        <input id="command" type="hidden" name="command" value="update_order">
                        <input type="hidden" name="order_id" value="${order.orderId}">
                        <input type="hidden" name="sender_login" value="${sessionScope.user.login}">

                        <div id="order-edit">
                            <div class="data-labels rowing-left">
                                <div class="data-field">
                                    <p>${order_car}:</p>
                                </div>
                                <div class="data-field">
                                    <p>${order_status}:</p>
                                </div>
                                <div class="data-field">
                                    <p>${pick_up_date}:</p>
                                </div>
                                <div class="data-field">
                                    <p>${drop_off_date}:</p>
                                </div>
                                <div class="data-field comment">
                                    <p>${comment}:</p>
                                </div>

                            </div>
                            <div class="user-data-fields rowing-right">
                                <div class="data-field">
                                    <select id="car-id" name="car_id" required="">
                                        <c:forEach items="${cars}" var="car">
                                            <option
                                                    <c:if test="${car.carID eq order.car.carID}">selected=""</c:if>
                                                    value="${car.carID}">${car.brand} ${car.model} ${car.yearProduction}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="data-field">
                                    <select id="order-status" name="order_status" required="">
                                        <c:forEach items="${orderStatuses}" var="orderStatus">
                                            <option <c:if
                                                    test="${orderStatus.status eq order.orderStatus.status}">
                                                selected=""
                                            </c:if>
                                                    value="${orderStatus.statusID}">
                                                <fmt:message
                                                        key="order.status.${orderStatus.status}"/>
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="data-field">
                                    <input id="pick-up-date" type="date" name="pick_up_date"
                                           value="${order.pickUpDate}" required="">
                                </div>
                                <div class="data-field">
                                    <input id="drop-off-date" type="date" name="drop_off_date"
                                           value="${order.dropOffDate}" required="">
                                </div>
                                <div class="data-field comment">
                                    <textarea id="order-comment" name="comment"
                                              maxlength="255">${order.comment}</textarea>
                                </div>
                            </div>
                        </div>

                        <div id="return-act">
                            <div class="data-labels rowing-left">
                                <div class="data-field">
                                    <p>${return_date}:</p>
                                </div>
                                <div class="data-field">
                                    <p>${car_odometer}:</p>
                                </div>
                                <div class="data-field comment">
                                    <p>${car_damage}:</p>
                                </div>
                                <div class="data-field">
                                    <p>${fine}:</p>
                                </div>
                                <div class="data-field comment">
                                    <p>${comment}:</p>
                                </div>
                            </div>


                            <div class="user-data-fields rowing-left">
                                <div class="data-field">
                                    <input id="return-date" type="date" name="return_date"
                                           value="${returnAct.returnDate}">
                                </div>
                                <div class="data-field">
                                    <input id="new-car-odometer" type="text" name="car_odometer"
                                           value="${returnAct.carOdometer}">
                                </div>
                                <div class="data-field comment">
                                    <textarea name="car_damage" maxlength="255">${returnAct.carDamage}</textarea>
                                </div>
                                <div class="data-field">
                                    <input type="text" name="order_fine"
                                           pattern="^[\d]+$" value="${returnAct.fine}">
                                </div>
                                <div class="data-field comment">
                                    <textarea name="act_comment" maxlength="255">${returnAct.actComment}</textarea>
                                </div>
                            </div>
                        </div>
                    </form>

                    <div class="rowing-right">
                        <button id="change-order-btn" form="change-order-form"
                                type="submit">${change}</button>
                        <c:if test="${not empty message_update_order}">
                            <c:choose>
                                <c:when test="${message_update_order eq 'data_update_error'}">
                                    <p class="update-error">${update_error}</p>
                                </c:when>
                                <c:when test="${message_update_order eq 'incorrect_data'}">
                                    <p class="incorrect-data-error">${incorrect_data}</p>
                                </c:when>
                            </c:choose>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" async="" src="js/editOrder.js"></script>
<%--END MAIN-CONTENT--%>
<%@include file="footer.jsp" %>