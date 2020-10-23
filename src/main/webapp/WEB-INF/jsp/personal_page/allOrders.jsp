<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<fmt:message key="login" var="login_title"/>
<fmt:message key="order.date" var="order_date"/>
<fmt:message key="order.status" var="order_status"/>
<fmt:message key="order.rental_start" var="order_rental_start"/>
<fmt:message key="order.rental_end" var="order_rental_end"/>
<fmt:message key="order.car" var="order_car"/>
<fmt:message key="order.bill_sum" var="bill_sum"/>
<fmt:message key="order.comment" var="order_comment"/>
<fmt:message key="order.not_exists" var="order_not_exists"/>
<fmt:message key="data.retrieve_error" var="data_retrieve_error"/>
<fmt:message key="order.message_start" var="message_start"/>
<fmt:message key="order.message_end" var="message_end"/>
<fmt:message key="currency" var="currency"/>

<table>
    <tr>
        <th style="width: 20px;">№</th>
        <th style="width: 120px;">${login_title}</th>
        <th style="width: 85px;">${order_date}</th>
        <th style="width: 70px; text-align: center;">${order_status}</th>
        <th style="width: 85px;">${order_rental_start}</th>
        <th style="width: 85px;">${order_rental_end}</th>
        <th style="width: 120px;">${order_car}</th>
        <th style="width: 75px;">${bill_sum}</th>
        <th>${order_comment}</th>
    </tr>

    <c:choose>
        <c:when test="${sessionScope.user.role ne 'admin'}">
            <c:redirect url="mainController?command=go_to_main_page"/>
        </c:when>
        <c:when test="${requestScope.message_orders eq 'data_retrieve_error'}">
            </table>
            <p class="data-error">${data_retrieve_error}</p>
        </c:when>
        <c:otherwise>
            <c:set var="lineNumber" value="${requestScope.offset + 1}"/>
            <c:forEach items="${requestScope.orders}" var="order">
                <tr>
                    <td style="font-weight: 700">${lineNumber}</td>
                    <td><a href="mainController?command=go_to_all_user_data&user_id=${order.userID}">${order.userLogin}</a></td>
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
                    <td style="width: 80px; text-align: center;">
                        <fmt:formatNumber minFractionDigits="2" value="${order.billSum}"/> ${currency}
                    </td>
                    <td>${order.comment}</td>
                </tr>
                <c:set var="lineNumber" value="${lineNumber + 1}"/>
            </c:forEach>
            </table>
            <c:if test="${empty requestScope.last_page}">
                <div class="pages-back">
                    <a href="mainController?command=go_to_personal_page_all_orders&page=${requestScope.page + 1}">
                        назад &#9658;
                    </a>
                </div>
            </c:if>
            <c:if test="${empty requestScope.first_page}">
                <div class="pages-forward">
                    <a href="mainController?command=go_to_personal_page_all_orders&page=${requestScope.page - 1}">
                        &#9668; вперед
                    </a>
                </div>
            </c:if>
        </c:otherwise>
    </c:choose>