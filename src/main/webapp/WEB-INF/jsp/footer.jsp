<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
        <!-- START FOOTER -->
        <footer>
            <div class="container">

                <div class="footer-info clear">
                    <div class="footer-block rowing-left">
                        <h3>Помощь</h3>
                        <ul>
                            <li><a href="someLink">Some link №1</a></li>
                            <li><a href="someLink">Some link №2</a></li>
                            <li><a href="someLink">Some link №3</a></li>
                            <li><a href="someLink">Some link №4</a></li>
                            <li><a href="someLink">Some link №5</a></li>
                        </ul>
                    </div>

                    <div class="footer-block rowing-left">
                        <h3>Об аренде</h3>
                        <ul>
                            <li><a href="mainController?command=go_to_contact_page">О нас</a></li>
                            <li><a href="mainController?command=go_to_news">Новости</a></li>
                            <li><a href="mainController?command=go_to_regulations">Правила аренды</a></li>
                            <li><a href="mainController?command=go_to_stocks">Акции</a></li>
                        </ul>
                    </div>

                    <div class="footer-block rowing-left">
                        <h3>Информация</h3>
                        <ul>
                            <li><a href="someLink">Some info №1</a></li>
                            <li><a href="someLink">Some info №2</a></li>
                            <li><a href="someLink">Some info №3</a></li>
                        </ul>
                    </div>

                    <div class="cards footer-block rowing-right">
                        <a href="someLink" target="_blank"><img alt="" src="${pageContext.request.contextPath}/img/cards.png"></a>
                    </div>
                </div>

                <div class="social-media">
                    <a href="http://facebook.com/" target="_blank"><img alt="" src="${pageContext.request.contextPath}/img/icon-footer-facebook.png"></a>
                    <a href="http://youtube.com/" target="_blank"><img alt="" src="${pageContext.request.contextPath}/img/icon-footer-youtube.png"></a>
                    <a href="http://twitter.com/" target="_blank"><img alt="" src="${pageContext.request.contextPath}/img/icon-footer-twitter.png"></a>
                </div>

            </div>
        </footer>
    <%--END FOOTER--%>
    </body>
</html>