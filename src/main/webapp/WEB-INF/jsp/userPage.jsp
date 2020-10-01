<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="header.jsp"%>

<fmt:message key="user.page.title" var="user_page_title"/>
<fmt:message key="user.role" var="user_role"/>
<fmt:message key="user.rating" var="user_rating"/>

<%--START MAIN-CONTENT--%>
<div id="content">
    <div class="container main-content">

        <c:if test="${sessionScope.user == null}">
            <c:redirect url="mainController?command=go_to_main_page"/>
        </c:if>

        <c:if test="${sessionScope.user != null}">
            <div class="clear">
                <div class="rowing-left">
                    <div id="user-logo">
                        <p>${sessionScope.user.login.charAt(0)}</p>
                    </div>
                    <a href="mainController?command=logout">${log_out}</a>
                </div>
                <div id="user-data" class="rowing-left">
                    <h2>${hello} <strong>${sessionScope.user.login}</strong></h2>

                    <p>${user_role}: ${sessionScope.user.role}</p>
                    <p>${user_rating}: ${sessionScope.user.rating}</p>
                    <br>
                </div>
                <div id="user-menu" class="rowing-left">
                    <div class="clear">
                        <div class="user-menu-tab rowing-left">
                            <a href="">Заказы</a>
                        </div>
                        <div class="user-menu-tab rowing-left">
                            <a href="">Паспортные данные</a>
                        </div>
                        <div class="user-menu-tab rowing-left">
                            <a href="">Карточки</a>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>

    </div>
</div>
<%--END MAIN-CONTENT--%>

<%@include file="footer.jsp"%>