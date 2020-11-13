<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://localhost:8080/CarRentalFinalProjectJWD/mytag" prefix="mytag"%>

<fmt:message key="payment.to_pay" var="to_pay"/>
<fmt:message key="order.date" var="order_date"/>
<fmt:message key="order.status" var="order_status"/>
<fmt:message key="pick_up_date" var="pick_up_date"/>
<fmt:message key="drop_off_date" var="drop_off_date"/>
<fmt:message key="order.car" var="order_car"/>
<fmt:message key="order.bill_sum" var="bill_sum"/>
<fmt:message key="order.is_paid" var="is_paid"/>
<fmt:message key="order.not_exists" var="order_not_exists"/>
<fmt:message key="data.retrieve_error" var="data_retrieve_error"/>
<fmt:message key="order.message_start" var="message_start"/>
<fmt:message key="order.message_end" var="message_end"/>
<fmt:message key="currency" var="currency"/>
<fmt:message key="our_cars_title" var="our_cars_title"/>

<table>
    <tr>
        <th>${order_date}</th>
        <th>${order_status}</th>
        <th>${pick_up_date}</th>
        <th>${drop_off_date}</th>
        <th>${order_car}</th>
        <th>${bill_sum}</th>
        <th style="text-align: center;">${is_paid}</th>
    </tr>
    <c:choose>
        <c:when test="${requestScope.message_orders eq 'data_retrieve_error'}">
            </table>
            <p class="data-error">${data_retrieve_error}</p>
        </c:when>
        <c:when test="${requestScope.user_orders.size() lt 1}">
            </table>
            <p class="message">${order_not_exists}</p>
            <p class="message">${message_start}
                <a href="mainController?command=go_to_our_cars"> ${our_cars_title} </a>
                    ${message_end}
            </p>
        </c:when>
        <c:otherwise>
            <form id="pay-order-form" action="mainController">
                <input type="hidden" name="command" value="go_to_payment_page">
            </form>
            <c:forEach items="${requestScope.user_orders}" var="order">
                <tr class="<c:if test="${order.orderStatus.status eq 'denied'}">denied-order</c:if>">
                    <td><mytag:dateFormatTag localDateTime="${order.orderDate}"/></td>
                    <td style="<c:if test="${order.orderStatus.status eq 'denied'}">color: red;</c:if>">
                        <fmt:message key="order.status.${order.orderStatus.status}"/></td>
                    <td>${order.pickUpDate}</td>
                    <td>${order.dropOffDate}</td>
                    <td>${order.car.brand} ${order.car.model}</td>
                    <td>
                        <fmt:formatNumber minFractionDigits="2" value="${order.totalSum}"/> ${currency}
                    </td>
                    <td style="text-align: center">
                        <c:choose>
                            <c:when test="${order.paid eq true}">
                                <span style="color: green">&#10004;</span>
                            </c:when>
                            <c:when test="${order.orderStatus.status eq 'denied'}">
                                <span style="color: #b9b9b9">&#10006;</span>
                            </c:when>
                            <c:otherwise>
                                <button id="postpaid-btn" form="pay-order-form" type="submit" name="order_id" value="${order.orderId}">${to_pay}</button>
                                <span style="text-transform: lowercase;"></span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>