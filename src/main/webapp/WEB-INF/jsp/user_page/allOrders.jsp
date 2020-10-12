<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<fmt:message key="user.order.date" var="order_date"/>
<fmt:message key="user.order.status" var="order_status"/>
<fmt:message key="user.order.rental_start" var="order_rental_start"/>
<fmt:message key="user.order.rental_end" var="order_rental_end"/>
<fmt:message key="user.order.car" var="order_car"/>
<fmt:message key="user.order.order_price" var="order_price"/>
<fmt:message key="user.order.comment" var="order_comment"/>
<fmt:message key="user.order.not_exists" var="order_not_exists"/>
<fmt:message key="error.data_retrieve" var="data_retrieve_error"/>
<fmt:message key="user.order.message_start" var="message_start"/>
<fmt:message key="user.order.message_end" var="message_end"/>

<table id="admin-all-orders">
    <tr id="admin-table-header">
        <th style="width: 20px;">User ID</th>
        <th style="width: 75px;">${order_date}</th>
        <th style="min-width: 70px;">${order_status}</th>
        <th style="width: 90px;">${order_rental_start}</th>
        <th style="width: 90px;">${order_rental_end}</th>
        <th style="width: 150px;">${order_car}</th>
        <th>${order_price}</th>
        <th>${order_comment}</th>
    </tr>

    <c:choose>
        <c:when test="${sessionScope.user.role ne 'admin'}">
            <c:redirect url="mainController?command=go_to_main_page"/>
        </c:when>
        <c:when test="${requestScope.message eq 'data_retrieve_error'}">
            </table>
            <p class="data-error">${data_retrieve_error}</p>
        </c:when>
        <c:otherwise>
            <c:forEach items="${requestScope.all_orders}" var="order">
                <tr>
                    <td>${order.userID}</td>
                    <td>${order.orderDate}</td>
                    <td><fmt:message key="user.order.status.${order.orderStatus}"/></td>
                    <td><fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${order.rentalStart}"/></td>
                    <td><fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${order.rentalEnd}"/></td>
                    <td>${order.carBrand} ${order.carModel}</td>
                    <td>${order.orderPrice}</td>
                    <td>${order.comment}</td>
                </tr>
            </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>