<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://localhost:8080/CarRentalFinalProjectJWD/mytag" prefix="mytag"%>

<fmt:message key="login" var="login_title"/>
<fmt:message key="order.date" var="order_date"/>
<fmt:message key="order.status" var="order_status"/>
<fmt:message key="pick_up_date" var="pick_up_date"/>
<fmt:message key="drop_off_date" var="drop_off_date"/>
<fmt:message key="order.car" var="order_car"/>
<fmt:message key="order.bill_sum" var="bill_sum"/>
<fmt:message key="order.is_paid" var="is_paid"/>
<fmt:message key="order.comment" var="order_comment"/>
<fmt:message key="order.not_exists" var="order_not_exists"/>
<fmt:message key="data.retrieve_error" var="data_retrieve_error"/>
<fmt:message key="order.message_start" var="message_start"/>
<fmt:message key="order.message_end" var="message_end"/>
<fmt:message key="currency" var="currency"/>

<table>
    <tr>
        <th style="width: 115px;">${login_title}</th>
        <th style="width: 110px;">${order_date}</th>
        <th style="width: 65px; text-align: center;">${order_status}</th>
        <th style="width: 80px;">${pick_up_date}</th>
        <th style="width: 85px;">${drop_off_date}</th>
        <th style="width: 110px;">${order_car}</th>
        <th style="width: 75px;">${bill_sum}</th>
        <th>${is_paid}</th>
        <th>${order_comment}</th>
        <th style="width: 20px"></th>
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
            <c:set var="command"
                   value="${pageContext.request.getParameter(\"command\")}"/>
            <c:set var="lineNumber" value="${requestScope.offset + 1}"/>
            <form id="edit-order-form" action="mainController">
                <input type="hidden" name="command" value="go_to_edit_order">
                <input type="hidden" name="sender_login" value="${sessionScope.user.login}">
            </form>
            <c:forEach items="${requestScope.orders}" var="order">
                <tr <c:if test="${command eq 'go_to_personal_page_all_orders'}">class="changeable-order"</c:if>>
                    <td><a href="mainController?command=go_to_all_user_data&user_id=${order.userID}">${order.userLogin}</a></td>
                    <td><mytag:dateFormatTag localDateTime="${order.orderDate}"/></td>
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
                    <td>${order.pickUpDate}</td>
                    <td>${order.dropOffDate}</td>
                    <td>${order.car.brand} ${order.car.model}</td>
                    <td style="width: 80px; text-align: center;">
                        <fmt:formatNumber minFractionDigits="2" value="${order.totalSum}"/> ${currency}
                    </td>
                    <td style="text-align: center">
                        <c:if test="${order.paid eq true}">
                            <span style="color: green">&#10004;</span>
                        </c:if>
                    </td>
                    <td>${order.comment}</td>
                    <td class="change-order-btn">
                        <button form="edit-order-form" name="order_id" value="${order.orderId}">&#9998;</button>
                    </td>
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