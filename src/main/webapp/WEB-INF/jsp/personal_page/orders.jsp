<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://localhost:8080/CarRentalFinalProjectJWD/mytag" prefix="mytag"%>

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
            <c:forEach items="${requestScope.user_orders}" var="order">
                <tr>
                    <td><mytag:dateFormatTag localDateTime="${order.orderDate}"/></td>
                    <td><fmt:message key="order.status.${order.orderStatus}"/></td>
                    <td>${order.pickUpDate}</td>
                    <td>${order.dropOffDate}</td>
                    <td>${order.carBrand} ${order.carModel}</td>
                    <td>
                        <fmt:formatNumber minFractionDigits="2" value="${order.billSum}"/> ${currency}
                    </td>
                    <td style="text-align: center">
                        <c:if test="${order.paid eq true}">
                            <span style="color: green">&#10004;</span>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>