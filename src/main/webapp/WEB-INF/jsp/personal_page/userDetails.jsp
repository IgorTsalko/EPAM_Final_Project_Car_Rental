<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="passport" value="${requestScope.user_passport}"/>

<fmt:message key="user.role" var="role"/>
<fmt:message key="user.rating" var="rating"/>
<fmt:message key="phone" var="phone"/>
<fmt:message key="email" var="email"/>
<fmt:message key="login" var="login"/>

<fmt:message key="data.edit" var="data_edit"/>
<fmt:message key="data.change" var="change_edit"/>
<fmt:message key="data.confirm" var="data_confirm"/>
<fmt:message key="data.cancel" var="data_cancel"/>
<fmt:message key="data.update_error" var="update_error"/>
<fmt:message key="data.retrieve_error" var="data_retrieve_error"/>
<fmt:message key="data.incorrect_data" var="incorrect_data"/>
<fmt:message key="data.invalid_old_password" var="invalid_old_password"/>
<fmt:message key="data.retrieve_error" var="data_retrieve_error"/>
<fmt:message key="data.updated" var="data_updated"/>
<fmt:message key="user.old_password" var="old_password"/>
<fmt:message key="user.new_password" var="new_password"/>

<c:set var="user_details" value="${requestScope.user_details}"/>
<c:set var="all_roles" value="${requestScope.all_roles}"/>
<c:set var="all_ratings" value="${requestScope.all_ratings}"/>

<c:set var="param_user_id" value="${pageContext.request.getParameter(\"user_id\")}"/>
<c:set var="message_details_update"
       value="${pageContext.request.getParameter(\"message_details_update\")}"/>

<c:choose>
    <c:when test="${requestScope.message_details eq 'data_retrieve_error'}">
        <p class="data-error">${data_retrieve_error}</p>
    </c:when>
    <c:otherwise>
        <div class="personal-data clearfix">
            <form id="user-desc" action="mainController" method="post">
                <div>
                    <button type="button" name="edit-button" class="edit-button"
                            onclick="enableEditing('user-desc', 'user-post', 'user-cancel')">
                            ${data_edit}
                    </button>
                    <button type="submit" id="user-post" class="edit-button confirm-button"
                            style="display: none;">
                            ${data_confirm}
                    </button>
                    <button type="button" id="user-cancel" class="edit-button"
                            style="display: none;"
                            onclick="disableEditing('user-desc', 'user-post', 'user-cancel')">
                            ${data_cancel}
                    </button>
                </div>
                <div>
                    <c:choose>
                        <c:when test="${message_details_update eq 'data_update_error'}">
                            <p class="update-error">${update_error}</p>
                        </c:when>
                        <c:when test="${message_details_update eq 'incorrect_data'}">
                            <p class="incorrect-data-error">${incorrect_data}</p>
                        </c:when>
                        <c:when test="${message_details_update eq 'data_updated'}">
                            <p class="data-updated">${data_updated}</p>
                        </c:when>
                    </c:choose>
                </div>
                <input type="hidden" name="command" value="update_user_details">
                <input type="hidden" name="sender_login" value="${sessionScope.user.login}">
                <c:choose>
                    <c:when test="${not empty param_user_id}">
                        <input type="hidden" name="user_id" value="${param_user_id}">
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" name="user_id" value="${sessionScope.user.id}">
                    </c:otherwise>
                </c:choose>
                <div class="data-labels rowing-left">
                    <div class="data-field">
                        <p>${role}:</p>
                    </div>
                    <div class="data-field">
                        <p>${rating}:</p>
                    </div>
                    <div class="data-field">
                        <p>${phone}:</p>
                    </div>
                    <div class="data-field">
                        <p>${email}:</p>
                    </div>
                </div>
                <div class="user-data-fields rowing-left">
                    <div class="data-field">
                        <select name="user_role_id" disabled required>
                            <c:forEach items="${all_roles}" var="role">
                                <option <c:if test="${role.roleName eq user_details.userRole.roleName}">
                                    selected
                                </c:if> value="${role.roleID}">${role.roleName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="data-field">
                        <select name="user_rating_id" disabled required>
                            <c:forEach items="${all_ratings}" var="rating">
                                <option <c:if
                                        test="${rating.ratingName eq user_details.userRating.ratingName}">
                                    selected
                                </c:if> value="${rating.ratingID}">${rating.ratingName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="data-field">
                        <input type="tel" name="user_phone" pattern="^[0-9\s-+()]+$"
                               maxlength="30" minlength="7" value="${user_details.userPhone}"
                               disabled="" required>
                    </div>
                    <div class="data-field">
                        <input type="email" name="user_email" pattern="^[^\s]+@[^\s]+\.[^\s]+$"
                               maxlength="30" minlength="3" value="${user_details.userEmail}"
                               disabled="">
                    </div>
                </div>
            </form>
        </div>

        <c:if test="${pageContext.request.getParameter(\"command\") eq 'go_to_personal_page_details'}">
            <c:set var="message_update_login"
                   value="${pageContext.request.getParameter(\"message_update_login\")}"/>
            <c:set var="message_update_password"
                   value="${pageContext.request.getParameter(\"message_update_password\")}"/>
            <hr>
            <c:if test="${not empty message_update_login}">
                <c:choose>
                    <c:when test="${message_update_login eq 'data_update_error'}">
                        <p class="update-error">${update_error}</p>
                    </c:when>
                    <c:when test="${message_update_login eq 'incorrect_data'}">
                        <p class="incorrect-data-error">${incorrect_data}</p>
                    </c:when>
                </c:choose>
            </c:if>
            <div class="user-login clearfix">
                <form id="user-login" action="mainController" method="post">
                    <div>
                        <button type="submit"
                                class="edit-button confirm-button">${change_edit}</button>
                    </div>
                    <input type="hidden" name="command" value="update_user_login">
                    <input type="hidden" name="sender_login" value="${sessionScope.user.login}">
                    <input type="hidden" name="user_id" value="${sessionScope.user.id}">

                    <div class="data-labels rowing-left">
                        <div class="data-field">
                            <p>${login}:</p>
                        </div>
                    </div>
                    <div class="user-data-fields rowing-left">
                        <div class="data-field">
                            <input type="text" pattern="^[0-9a-zA-Z-+_]{3,25}$" name="new_login"
                                   maxlength="25" minlength="3" value="${sessionScope.user.login}"
                                   required>
                        </div>
                    </div>
                </form>
            </div>

            <hr>
            <c:if test="${not empty message_update_password}">
                <c:choose>
                    <c:when test="${message_update_password eq 'data_updated'}">
                        <p class="data-updated">${data_updated}</p>
                    </c:when>
                    <c:when test="${message_update_password eq 'data_update_error'}">
                        <p class="update-error">${update_error}</p>
                    </c:when>
                    <c:when test="${message_update_password eq 'incorrect_data'}">
                        <p class="incorrect-data-error">${incorrect_data}</p>
                    </c:when>
                    <c:when test="${message_update_password eq 'invalid_old_password'}">
                        <p class="incorrect-data-error">${invalid_old_password}</p>
                    </c:when>
                </c:choose>
            </c:if>
            <div class="user-change-password clearfix">
                <form id="change-password" action="mainController" method="post">
                    <div>
                        <button type="submit"
                                class="edit-button confirm-button">${change_edit}</button>
                    </div>
                    <input type="hidden" name="command" value="update_user_password">
                    <input type="hidden" name="sender_login" value="${sessionScope.user.login}">
                    <input type="hidden" name="user_id" value="${sessionScope.user.id}">

                    <div class="data-labels rowing-left">
                        <div class="data-field">
                            <p>${old_password}:</p>
                        </div>
                        <div class="data-field">
                            <p>${new_password}:</p>
                        </div>
                    </div>
                    <div class="user-data-fields rowing-left">
                        <div class="data-field">
                            <input type="password" name="old_password" pattern="^[^\s]+$"
                                   maxlength="18" minlength="6" required>
                        </div>
                        <div class="data-field">
                            <input type="password" name="new_password" pattern="^[^\s]+$"
                                   maxlength="18" minlength="6" required>
                        </div>
                    </div>
                </form>
            </div>
        </c:if>
    </c:otherwise>
</c:choose>
