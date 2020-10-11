<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="header.jsp"%>

<fmt:message key="error.data_retrieve" var="data_retrieve_error"/>
<fmt:message key="car.transmission_title" var="transmission_title"/>
<fmt:message key="car.transmission.at" var="at"/>
<fmt:message key="car.transmission.mt" var="mt"/>

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
                        <a href="1.html">
                            <div class="car-image rowing-left">
                                <img width="250" src="${pageContext.request.contextPath}${car.mainImageURI}" alt="carImage">
                            </div>
                            <div class="car-desc rowing-left">
                                <p class="car-title">${car.brand} ${car.model} ${car.yearProduction}</p>
                                <p>Доступна: ${car.available}</p>
                                <p>${transmission_title}: <fmt:message key="car.transmission.${car.transmission}"/></p>
                                <p>Объем двигателя: ${car.engineSize}</p>
                                <p>Тип топлива: ${car.fuelType}</p>
                                <p><strong>${car.pricePerDay}</strong> руб в день</p>
                                <p>${car.comment}</p>
                            </div>
                        </a>
                    </div>
                </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>

    </div>
</div>
<%--END MAIN-CONTENT--%>

<%@include file="footer.jsp"%>