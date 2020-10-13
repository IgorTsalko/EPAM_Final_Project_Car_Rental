<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<fmt:message key="error.data_retrieve" var="data_retrieve_error"/>
<fmt:message key="login" var="login"/>
<fmt:message key="user.role" var="user_role"/>
<fmt:message key="user.rating" var="user_rating"/>

<table>
    <tr>
        <th style="width: 50px;">User ID</th>
        <th>${login}</th>
        <th>${user_role}</th>
        <th>${user_rating}</th>
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
            <c:forEach items="${requestScope.all_users}" var="user">
                <tr>
                    <td style="text-align: center;">${user.id}</td>
                    <td>${user.login}</td>
                    <td>${user.role}</td>
                    <td>${user.rating}</td>
                </tr>
            </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>