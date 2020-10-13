<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<fmt:message key="order.date" var="order_date"/>
<fmt:message key="order.status" var="order_status"/>
<fmt:message key="order.rental_start" var="order_rental_start"/>
<fmt:message key="order.rental_end" var="order_rental_end"/>
<fmt:message key="order.car" var="order_car"/>
<fmt:message key="order.price" var="order_price"/>
<fmt:message key="order.comment" var="order_comment"/>
<fmt:message key="order.not_exists" var="order_not_exists"/>
<fmt:message key="error.data_retrieve" var="data_retrieve_error"/>
<fmt:message key="order.message_start" var="message_start"/>
<fmt:message key="order.message_end" var="message_end"/>

<table>
    <tr>
        <th style="width: 50px;">User ID</th>
        <th style="width: 75px;">${order_date}</th>
        <th style="min-width: 70px; text-align: center;">${order_status}</th>
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
                    <td style="text-align: center;">${order.userID}</td>
                    <td>${order.orderDate}</td>
                    <td style="text-align: center;" class="
                        <c:choose>
                            <c:when test="${order.orderStatus eq 'new'}">
                                new-order
                            </c:when>
                            <c:when test="${order.orderStatus eq 'expired'}">
                                expired-order
                            </c:when>
                         </c:choose>">
                        <fmt:message key="order.status.${order.orderStatus}"/>
                    </td>
                    <td>${order.rentalStart}</td>
                    <td>${order.rentalEnd}</td>
                    <td>${order.carBrand} ${order.carModel}</td>
                    <td style="width: 80px; text-align: center;">${order.orderPrice}</td>
                    <td>${order.comment}</td>
                </tr>
            </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>