<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<fmt:message key="data.retrieve_error" var="data_retrieve_error"/>
<fmt:message key="login" var="login"/>
<fmt:message key="user.role" var="user_role"/>
<fmt:message key="user.rating" var="user_rating"/>

<c:choose>
    <c:when test="${sessionScope.user.role ne 'admin'}">
        <c:redirect url="mainController?command=go_to_main_page"/>
    </c:when>
    <c:when test="${requestScope.message_all_users eq 'data_retrieve_error'}">
        </table>
        <p class="data-error">${data_retrieve_error}</p>
    </c:when>
    <c:otherwise>
        <table>
            <tr>
                <th style="width: 45px;">User</th>
                <th>${login}</th>
                <th>${user_role}</th>
                <th>${user_rating}</th>
            </tr>
        <c:forEach items="${requestScope.all_users}" var="user">
            <tr>
                <td>${user.id}</td>
                <td><a href="mainController?command=go_to_all_user_data&user_id=${user.id}">${user.login}</a></td>
                <td>${user.role}</td>
                <td>${user.rating}</td>
            </tr>
        </c:forEach>
        </table>
    </c:otherwise>
</c:choose>
