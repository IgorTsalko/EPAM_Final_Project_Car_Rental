<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="header.jsp"%>

<fmt:message key="error.data_retrieve" var="data_retrieve_error"/>
<fmt:message key="car.nearest_available_date" var="nearest_available_date"/>
<fmt:message key="car.transmission" var="transmission_title"/>
<fmt:message key="car.engine_size" var="engine_size"/>
<fmt:message key="car.engine_size_unit" var="engine_size_unit"/>
<fmt:message key="car.fuel_type" var="fuel_type"/>
<fmt:message key="car.description" var="description"/>
<fmt:message key="car.features" var="features"/>
<fmt:message key="price_per_day" var="price_per_day"/>
<fmt:message key="pick_up_date" var="pick_up_date"/>
<fmt:message key="drop_off_date" var="drop_off_date"/>
<fmt:message key="to_rent" var="to_rent"/>

<%--START MAIN-CONTENT--%>
<div id="content">
    <div class="container main-content clear">
        <c:choose>
            <c:when test="${requestScope.message eq 'data_retrieve_error'}">
                <p class="data-error">${data_retrieve_error}</p>
            </c:when>
            <c:otherwise>
                <div id="car-images" class="rowing-left">
                    <div id="main-car-image">
                        <img id="main_car_img" src="${pageContext.request.contextPath}${requestScope.car_images[0]}">
                    </div>
                    <c:forEach items="${requestScope.car_images}" var="car_image">
                        <a href="javascript:changeImage('${pageContext.request.contextPath}${car_image}')">
                            <img class="small-car-img" src="${pageContext.request.contextPath}${car_image}">
                        </a>
                    </c:forEach>
                </div>
                <div id="car-information" class="rowing-left">
                    <h1>${requestScope.car.brand} ${requestScope.car.model} ${requestScope.car.yearProduction}</h1>
                    <div id="car-details">
                        <div class="car-info">
                            <h3>${description}:</h3>
                            <p>${requestScope.car.comment}</p>
                        </div>
                        <div class="car-info">
                            <h3>${features}:</h3>
                            <ul class="car-features">
                                <li>${transmission_title}: <fmt:message key="car.transmission.${requestScope.car.transmission}"/></li>
                                <li>${engine_size}: ${requestScope.car.engineSize} ${engine_size_unit}</li>
                                <li>${fuel_type}: <fmt:message key="car.fuel_type.${requestScope.car.fuelType}"/></li>
                            </ul>
                        </div>
                    </div>

                    <p id="car-price">
                        <strong>
                            <fmt:formatNumber minFractionDigits="2" value="${requestScope.car.pricePerDay}"/>
                        </strong> ${price_per_day}</p>

                    <p>${nearest_available_date}: 2020-10-16</p>
                    <div id="order-form">
                        <form action="someAction" method="post">
                            <div class="rowing-left">
                                <p>${pick_up_date}</p>
                                <input type="date">
                            </div>
                            <div class="rowing-left">
                                <p>${drop_off_date}</p>
                                <input type="date">
                            </div>
                            <div>
                                <button type="submit">${to_rent}</button>
                            </div>
                        </form>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<%--END MAIN-CONTENT--%>

<%@include file="footer.jsp"%>