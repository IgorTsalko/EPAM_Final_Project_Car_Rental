<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<fmt:message key="order.date" var="order_date"/>
<fmt:message key="order.status" var="order_status"/>
<fmt:message key="order.rental_start" var="order_rental_start"/>
<fmt:message key="order.rental_end" var="order_rental_end"/>
<fmt:message key="order.car" var="order_car"/>
<fmt:message key="order.bill_sum" var="bill_sum"/>
<fmt:message key="order.not_exists" var="order_not_exists"/>
<fmt:message key="error.data_retrieve" var="data_retrieve_error"/>
<fmt:message key="order.message_start" var="message_start"/>
<fmt:message key="order.message_end" var="message_end"/>
<fmt:message key="currency" var="currency"/>

<table>
    <tr>
        <th>${order_date}</th>
        <th>${order_status}</th>
        <th>${order_rental_start}</th>
        <th>${order_rental_end}</th>
        <th>${order_car}</th>
        <th>${bill_sum}</th>
    </tr>

    <c:choose>
        <c:when test="${requestScope.message eq 'data_retrieve_error'}">
            </table>
            <p class="data-error">${data_retrieve_error}</p>
        </c:when>
        <c:when test="${requestScope.user_orders.size() lt 1}">
            </table>
            <p class="message">${order_not_exists}</p>
            <p class="message">${message_start}
                <a href="mainController?command=go_to_catalog"> ${catalog_title} </a>
                    ${message_end}
            </p>
        </c:when>
        <c:otherwise>
            <c:forEach items="${requestScope.user_orders}" var="order">
                <tr>
                    <td>${order.orderDate}</td>
                    <td><fmt:message key="order.status.${order.orderStatus}"/></td>
                    <td>${order.rentalStart}</td>
                    <td>${order.rentalEnd}</td>
                    <td>${order.carBrand} ${order.carModel}</td>
                    <td>
                        <fmt:formatNumber minFractionDigits="2" value="${order.billSum}"/> ${currency}
                    </td>
                </tr>
            </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>