<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://localhost:8080/CarRentalFinalProjectJWD/mytag" prefix="mytag"%>

<%@include file="../header.jsp"%>

<fmt:message key="data.retrieve_error" var="data_retrieve_error"/>
<fmt:message key="our_cars_title" var="our_cars_title"/>
<fmt:message key="car.transmission" var="transmission_title"/>
<fmt:message key="car.engine_size" var="engine_size"/>
<fmt:message key="car.engine_size_unit" var="engine_size_unit"/>
<fmt:message key="car.fuel_type" var="fuel_type"/>
<fmt:message key="price_per_day" var="price_per_day"/>
<fmt:message key="phone" var="phone"/>
<fmt:message key="to_rent" var="to_rent"/>
<title>${our_cars_title}</title>

<%--START MAIN-CONTENT--%>
<div id="content">
    <div class="container main-content">

        <h1>${our_cars_title}</h1>

        <c:choose>
            <c:when test="${requestScope.message eq 'data_retrieve_error'}">
                <p class="data-error">${data_retrieve_error}</p>
            </c:when>
            <c:otherwise>
                <c:forEach items="${requestScope.cars}" var="car">
                    <div class="car clear link">
                        <a href="mainController?command=go_to_car_page&car_id=${car.carID}">
                            <div class="car-image rowing-left">
                                <img src="${car.mainImageURI}" alt="carImage">
                            </div>
                            <div class="car-desc rowing-left">
                                <p class="car-title">${car.brand} ${car.model} ${car.yearProduction}</p>
                                <p>${transmission_title}: <fmt:message key="car.transmission.${car.transmission}"/></p>
                                <p>${engine_size}: ${car.engineSize} ${engine_size_unit}</p>
                                <p>${fuel_type}: <fmt:message key="car.fuel_type.${car.fuelType}"/></p>
                                <p>${car.comment}</p>
                            </div>
                        </a>
                        <div class="quick-order rowing-right">
                            <form action="mainController" method="post">
                                <div class="car-price">
                                    <div class="price-content">
                                        <div class="price-block">
                                            <c:choose>
                                                <c:when test="${sessionScope.user.discount gt 0}">
                                                    <span class="old-price">
                                                        <fmt:formatNumber minFractionDigits="2"
                                                                          value="${car.pricePerDay}"/>
                                                    </span>
                                                    <span class="price new-price">
                                                        <mytag:discountTag carPrice="${car.pricePerDay}"
                                                                           discount="${sessionScope.user.discount}"/>
                                                    </span>
                                                    <span>${price_per_day}</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="price">
                                                        <fmt:formatNumber minFractionDigits="2"
                                                                          value="${car.pricePerDay}"/>
                                                    </span>
                                                    <span>${price_per_day}</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                                <c:if test="${empty sessionScope.user}">
                                    <input type="tel" placeholder="${phone}">
                                </c:if>
                                <input type="hidden" name="command" value="quick_order&car_id=${car.carID}">
                                <button type="submit">${to_rent}</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>

    </div>
</div>
<%--END MAIN-CONTENT--%>

<%@include file="../footer.jsp"%>