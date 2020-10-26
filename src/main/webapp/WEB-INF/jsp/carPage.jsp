<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://localhost:8080/CarRentalFinalProjectJWD/mytag" prefix="mytag"%>
<%@include file="header.jsp"%>

<fmt:message key="data.retrieve_error" var="data_retrieve_error"/>
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
<fmt:message key="car.discount_title" var="discount_title"/>

<%--START MAIN-CONTENT--%>
<div id="content">
    <div class="container main-content">
        <c:choose>
            <c:when test="${requestScope.message eq 'data_retrieve_error'}">
                <p class="data-error">${data_retrieve_error}</p>
            </c:when>
            <c:otherwise>
            <div id="car-content" class="clear">
                <div id="car-images" class="rowing-left">
                    <div id="main-car-image">
                        <img id="main_car_img" src="${requestScope.car_images[0]}">
                    </div>
                    <c:forEach items="${requestScope.car_images}" var="car_image">
                        <a href="javascript:changeImage('${car_image}')">
                            <img class="small-car-img" src="${car_image}">
                        </a>
                    </c:forEach>
                </div>
                <div id="car-information" class="rowing-right">
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

                    <div class="car-price">
                        <div class="price-content">
                            <div class="price-block">
                                <c:choose>
                                    <c:when test="${sessionScope.user.discount gt 0}">
                                        <span class="old-price">
                                            <fmt:formatNumber minFractionDigits="2"
                                                              value="${requestScope.car.pricePerDay}"/>
                                        </span>
                                        <span class="price new-price">
                                            <mytag:discountTag carPrice="${requestScope.car.pricePerDay}"
                                                               discount="${sessionScope.user.discount}"/>
                                        </span>
                                        <span>${price_per_day}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="price">
                                            <fmt:formatNumber minFractionDigits="2"
                                                              value="${requestScope.car.pricePerDay}"/>
                                        </span>
                                        <span>${price_per_day}</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <c:if test="${sessionScope.user.discount gt 0}">
                                <div class="discount">${discount_title} ${sessionScope.user.discount}%</div>
                            </c:if>
                        </div>
                    </div>

                    <p id="available-date">${nearest_available_date}: 2020-10-16</p>
                    <div id="order-form">
                        <form action="someAction" method="post">
                            <div class="rowing-left">
                                <p>${pick_up_date}</p>
                                <input type="date">
                            </div>
                            <div class="rowing-right">
                                <p>${drop_off_date}</p>
                                <input type="date">
                            </div>
                            <div>
                                <button type="submit">${to_rent}</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div id="recommended-cars" class="clear">
                <h2>Так же вас может заинтересовать</h2>
                <c:forEach items="${requestScope.recommended_cars}" var="recommended_car">
                    <div class="recommended-car rowing-left">
                        <a href="mainController?command=go_to_car_page&car_id=${recommended_car.carID}">
                            <h2>${recommended_car.brand} ${recommended_car.model} ${recommended_car.yearProduction}</h2>
                            <img src="${recommended_car.mainImageURI}">
                        </a>
                        <div class="car-price">
                            <div class="price-content">
                                <div class="price-block">
                                    <c:choose>
                                        <c:when test="${sessionScope.user.discount gt 0}">
                                            <span class="old-price">
                                                <fmt:formatNumber minFractionDigits="2"
                                                                  value="${recommended_car.pricePerDay}"/>
                                            </span>
                                            <span class="price new-price">
                                                <mytag:discountTag carPrice="${recommended_car.pricePerDay}"
                                                                   discount="${sessionScope.user.discount}"/>
                                            </span>
                                            <span>${price_per_day}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="price">
                                                <fmt:formatNumber minFractionDigits="2"
                                                                  value="${recommended_car.pricePerDay}"/>
                                            </span>
                                            <span>${price_per_day}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<%--END MAIN-CONTENT--%>

<%@include file="footer.jsp"%>