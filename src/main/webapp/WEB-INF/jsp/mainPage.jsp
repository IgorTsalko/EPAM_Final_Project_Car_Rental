<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@include file="header.jsp" %>
<title>Car_rental Igor Tsalko</title>

<fmt:message key="main_slogan" var="main_slogan"/>
<fmt:message key="search" var="search"/>
<fmt:message key="searching_form" var="form_title"/>
<fmt:message key="pick_up_date" var="pick_up_date"/>
<fmt:message key="drop_off_date" var="drop_off_date"/>

<%--START MAIN-CONTENT--%>
<div id="content">
    <div class="container main-content">

        <div class="main">

            <div id="main-search" class="searching-form boxShadow clear">
                <form action="mainController" method="post">
                    <input type="hidden" name="command" value="go_to_our_cars">
                    <div class="field">
                        <p id="searching-form-title">${form_title}</p>
                    </div>
                    <div class="field">
                        <p>${pick_up_date}</p>
                        <input type="date" name="pick_up_date"
                               value="<%=java.time.LocalDate.now()%>" required>
                    </div>
                    <div class="field">
                        <p>${drop_off_date}</p>
                        <input type="date" name="drop_off_date"
                               value="<%=java.time.LocalDate.now().plusDays(3)%>" required>
                    </div>
                    <div class="field">
                        <button type="submit">${search}</button>
                    </div>
                </form>
            </div>

            <div class="main-text">
                <p>${main_slogan}</p>
            </div>
            <div class="main-image">
                <img src="img/car_rental.jpg" alt="">
            </div>
        </div>

        <div class="features">
            <c:forEach items="${requestScope.all_news}" var="news" end="3">
                <c:choose>
                    <c:when test="${fn:startsWith(sessionScope.locale, 'en')}">
                        <div class="feature">
                            <div class="feature-title">
                                <h2>${news.titleEN}</h2>
                            </div>
                            <div class="feature-info">
                                <p>${news.textEN}</p>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="feature">
                            <div class="feature-title">
                                <h2>${news.titleRU}</h2>
                            </div>
                            <div class="feature-info">
                                <p>${news.textRU}</p>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </div>
</div>
<%--END MAIN-CONTENT--%>
<%@include file="footer.jsp" %>
