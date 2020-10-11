<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<fmt:message key="user.order.date" var="order_date"/>
<fmt:message key="user.order.status" var="order_status"/>
<fmt:message key="user.order.rental_start" var="order_rental_start"/>
<fmt:message key="user.order.rental_end" var="order_rental_end"/>
<fmt:message key="user.order.car" var="order_car"/>
<fmt:message key="user.order.order_price" var="order_price"/>
<fmt:message key="user.order.not_exists" var="order_not_exists"/>
<fmt:message key="error.data_retrieve" var="data_retrieve_error"/>
<fmt:message key="user.order.message_start" var="message_start"/>
<fmt:message key="user.order.message_end" var="message_end"/>

<table>
    <tr>
        <th>${order_date}</th>
        <th>${order_status}</th>
        <th>${order_rental_start}</th>
        <th>${order_rental_end}</th>
        <th>${order_car}</th>
        <th>${order_price}</th>
    </tr>

    <c:choose>
        <c:when test="${requestScope.message eq 'data_retrieve_error'}">
            </table>
            <p class="data-error">${data_retrieve_error}</p>
        </c:when>
        <c:when test="${requestScope.user_orders.size() lt 1}">
            </table>
            <p class="message">${order_not_exists}</p>
            <p class="message">${message_start}<a href="mainController?command=go_to_catalog"> ${catalog_title} </a>${message_end}</p>
        </c:when>
        <c:otherwise>
            <c:forEach items="${requestScope.user_orders}" var="order">
                <tr>
                    <td>${order.orderDate}</td>
                    <td><fmt:message key="user.order.status.${order.orderStatus}"/></td>
                    <td><fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${order.rentalStart}"/></td>
                    <td><fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${order.rentalEnd}"/></td>
                    <td>${order.carBrand} ${order.carModel}</td>
                    <td>${order.orderPrice}</td>
                </tr>
            </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>