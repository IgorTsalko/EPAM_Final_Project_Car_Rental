<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="header.jsp"%>
<title>Car_rental Igor Tsalko</title>

<fmt:message key="search" var="search"/>
<fmt:message key="searching_form" var="form_title"/>
<fmt:message key="pick_up_date" var="pick_up_date"/>
<fmt:message key="drop_off_date" var="drop_off_date"/>

<%--START MAIN-CONTENT--%>
<div id="content">
    <div class="container main-content">

        <div class="main">

            <div id="main-search" class="searching-form boxShadow clear">
                <form class="" action="someAction" method="post">
                    <div class="field">
                        <p id="searching-form-title">${form_title}</p>
                    </div>
                    <div class="field">
                        <p>${pick_up_date}</p>
                        <input type="date">
                    </div>
                    <div class="field">
                        <p>${drop_off_date}</p>
                        <input type="date">
                    </div>
                    <div class="field">
                        <button type="submit">${search}</button>
                    </div>
                </form>
            </div>

            <div class="main-text">
                <p>Аренда авто без залога!</p>
            </div>
            <div class="main-image">
                <img src="img/car_rental.jpg" alt="">
            </div>
        </div>

        <div class="features">
            <div class="feature">
                <div class="feature-title">
                    <h2>Аренда от одного дня</h2>
                </div>
                <div class="feature-info">
                    <p>Стоимость аренды от 45 руб/сутки. Нужен только паспорт и водительские права. Выдача авто в течении 15 минут!</p>
                </div>
                <a href="someLink">узнай больше..</a>
            </div>

            <div class="feature">
                <div class="feature-title">
                    <h2>Автокаско на все!</h2>
                </div>
                <div class="feature-info">
                    <p>Страховка все покроет! Полное автокаско на любые виды повреждений. С нами - вам не о чем беспокоиться!</p>
                </div>
                <a href="someLink">узнай больше..</a>
            </div>

            <div class="feature">
                <div class="feature-title">
                    <h2>Детское кресло - Бесплатно!</h2>
                </div>
                <div class="feature-info">
                    <p>Если вам нужно перевезти ребенка, мы выдадим вам автокресло абсолютно бессплатно!</p>
                </div>
                <a href="someLink">узнай больше..</a>
            </div>
        </div>
    </div>
</div>
<%--END MAIN-CONTENT--%>
<%@include file="footer.jsp"%>
