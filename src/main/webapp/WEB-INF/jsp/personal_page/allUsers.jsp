<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<fmt:message key="data.retrieve_error" var="data_retrieve_error"/>
<fmt:message key="login" var="login"/>
<fmt:message key="user.role" var="user_role"/>
<fmt:message key="user.rating" var="user_rating"/>

<c:choose>
    <c:when test="${sessionScope.user.role ne 'admin'}">
        <c:redirect url="mainController?command=go_to_main_page"/>
    </c:when>
    <c:when test="${requestScope.message_users eq 'data_retrieve_error'}">
        </table>
        <p class="data-error">${data_retrieve_error}</p>
    </c:when>
    <c:otherwise>
        <table>
            <tr>
                <th style="width: 20px;">№</th>
                <th>${login}</th>
                <th>${user_role}</th>
                <th>${user_rating}</th>
            </tr>
            <c:set var="lineNumber" value="${requestScope.offset + 1}"/>
            <c:forEach items="${requestScope.users}" var="user">
                <tr>
                    <td style="font-weight: 700">${lineNumber}</td>
                    <td><a href="mainController?command=go_to_all_user_data&user_id=${user.id}">${user.login}</a></td>
                    <td>${user.role}</td>
                    <td>${user.rating}</td>
                </tr>
                <c:set var="lineNumber" value="${lineNumber + 1}"/>
            </c:forEach>
        </table>
        <c:if test="${empty requestScope.last_page}">
            <div class="pages-back">
                <a href="mainController?command=go_to_personal_page_all_users&page=${requestScope.page + 1}">
                    назад &#9658;
                </a>
            </div>
        </c:if>
        <c:if test="${empty requestScope.first_page}">
            <div class="pages-forward">
                <a href="mainController?command=go_to_personal_page_all_users&page=${requestScope.page - 1}">
                    &#9668; вперед
                </a>
            </div>
        </c:if>
    </c:otherwise>
</c:choose>
