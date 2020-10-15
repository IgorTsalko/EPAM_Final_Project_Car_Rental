<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="header.jsp"%>

<fmt:message key="error.data_retrieve" var="data_retrieve_error"/>
<fmt:message key="car.transmission" var="transmission_title"/>
<fmt:message key="car.engine_size" var="engine_size"/>
<fmt:message key="car.engine_size_unit" var="engine_size_unit"/>
<fmt:message key="car.fuel_type" var="fuel_type"/>
<fmt:message key="price_per_day" var="price_per_day"/>
<fmt:message key="phone" var="phone"/>
<fmt:message key="to_rent" var="to_rent"/>

<%--START MAIN-CONTENT--%>
<div id="content">
    <div class="container main-content">

        <h1>${catalog_title}</h1>

        <c:choose>
            <c:when test="${requestScope.message eq 'data_retrieve_error'}">
                <p class="data-error">${data_retrieve_error}</p>
            </c:when>
            <c:otherwise>
                <c:forEach items="${requestScope.cars}" var="car">
                    <div class="car clear link">
                        <a href="mainController?command=go_to_car_page&car_id=${car.carID}">
                            <div class="car-image rowing-left">
                                <img width="250" src="${pageContext.request.contextPath}${car.mainImageURI}" alt="carImage">
                            </div>
                            <div class="car-desc rowing-left">
                                <p class="car-title">${car.brand} ${car.model} ${car.yearProduction}</p>
                                <p>${transmission_title}: <fmt:message key="car.transmission.${car.transmission}"/></p>
                                <p>${engine_size}: ${car.engineSize} ${engine_size_unit}</p>
                                <p>${fuel_type}: <fmt:message key="car.fuel_type.${car.fuelType}"/></p>
                                <p>${car.comment}</p>
                            </div>
                        </a>
                        <div class="quick-order rowing-left">
                            <form action="mainController" method="post">
                                <label>
                                    <strong><fmt:formatNumber minFractionDigits="2" value="${car.pricePerDay}"/>
                                    </strong> ${price_per_day}
                                </label>
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

<%@include file="footer.jsp"%>