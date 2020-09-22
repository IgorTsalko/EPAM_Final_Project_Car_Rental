<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="header.jsp"%>

<%--START MAIN-CONTENT--%>
<div id="content">
    <div class="container main-content">

        <div class="main">

            <div class="searching-form boxShadow clear">
                <form class="" action="someAction" method="post">
                    <div class="field rowing-left">
                        <p>Начало аренды</p>
                        <input class="" type="date">
                    </div>

                    <div class="field rowing-left">
                        <p>Время</p>
                        <select>
                            <option value="0800">8:00</option>
                            <option value="0900">9:00</option>
                            <option value="1000">10:00</option>
                            <option value="1100" selected="selected">11:00</option>
                            <option value="1200">12:00</option>
                            <option value="1300">13:00</option>
                            <option value="1400">14:00</option>
                            <option value="1500">15:00</option>
                            <option value="1600">16:00</option>
                            <option value="1700">17:00</option>
                            <option value="1800">18:00</option>
                            <option value="1900">19:00</option>
                            <option value="2000">20:00</option>
                        </select>
                    </div>

                    <div class="field rowing-left">
                        <p>Конец аренды</p>
                        <input class="" type="date">
                    </div>

                    <div class="field rowing-left">
                        <p>Время</p>
                        <select>
                            <option value="0800">8:00</option>
                            <option value="0900">9:00</option>
                            <option value="1000">10:00</option>
                            <option value="1100" selected="selected">11:00</option>
                            <option value="1200">12:00</option>
                            <option value="1300">13:00</option>
                            <option value="1400">14:00</option>
                            <option value="1500">15:00</option>
                            <option value="1600">16:00</option>
                            <option value="1700">17:00</option>
                            <option value="1800">18:00</option>
                            <option value="1900">19:00</option>
                            <option value="2000">20:00</option>
                        </select>
                    </div>

                    <div class="form-button rowing-right">
                        <button type="submit">Найти</button>
                    </div>
                </form>
            </div>

            <div class="main-text">
                <p>Крутой, рекламный текст!</p>
            </div>
            <div class="main-image">
                <img src="${pageContext.request.contextPath}/img/car_rental.jpg" alt="">
            </div>
        </div>

        <div class="features clear">
            <div class="rowing-left">
                <p class="features-header">Аренда от одного дня</p>
                <p>Стоимость аренды от 45 руб/сутки. Нужен только паспорт и водительские права. Выдача авто в течении 15 минут!</p>
                <a href="someLink"><p><strong>узнай больше</strong></p></a>
            </div>

            <div class="rowing-left">
                <p class="features-header">Автокаско</p>
                <p>Страховка все покроет! Полное автокаско на любые виды повреждений. С нами - вам не о чем беспокоиться!</p>
                <a href="someLink"><p><strong>узнай больше</strong></p></a>
            </div>

            <div class="rowing-left">
                <p class="features-header">Детское кресло - Бесплатно!</p>
                <p>Если вам нужно перевезти ребенка, мы выдадим вам автокресло абсолютно бессплатно!</p>
                <a href="someLink"><p><strong>узнай больше</strong></p></a>
            </div>
        </div>
    </div>
</div>
<%--END MAIN-CONTENT--%>
<%@include file="footer.jsp"%>
