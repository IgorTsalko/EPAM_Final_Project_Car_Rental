<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<fmt:message key="more_about_us" var="more_about_us"/>
<fmt:message key="company_history" var="company_history"/>
<fmt:message key="mobile_app" var="mobile_app"/>
<fmt:message key="news_about_us" var="news_about_us"/>
<fmt:message key="reviews" var="reviews"/>
<fmt:message key="vacancies" var="vacancies"/>
<fmt:message key="about_rent" var="about_rent"/>
<fmt:message key="information" var="information"/>
<fmt:message key="test.404_page" var="page_404"/>
<fmt:message key="test.500_page" var="page_500"/>
<fmt:message key="copyright" var="copyright"/>

<!-- START FOOTER -->
        <footer>
            <div class="container">

                <div class="footer-info clear">
                    <div class="footer-block rowing-left">
                        <h3>${more_about_us}</h3>
                        <ul>
                            <li><a href="someLink">${company_history}</a></li>
                            <li><a href="https://play.google.com/store" target="_blank">${mobile_app}</a></li>
                            <li><a href="https://www.nytimes.com" target="_blank">${news_about_us}</a></li>
                            <li><a href="someLink">${reviews}</a></li>
                            <li><a href="someLink">${vacancies}</a></li>
                        </ul>
                    </div>

                    <div class="footer-block rowing-left">
                        <h3>${about_rent}</h3>
                        <ul>
                            <li><a href="mainController?command=go_to_contact_page">${contacts_title}</a></li>
                            <li><a href="mainController?command=go_to_news">${news_title}</a></li>
                            <li><a href="mainController?command=go_to_rules">${rules_title}</a></li>
                            <li><a href="mainController?command=go_to_stocks">${stocks_title}</a></li>
                        </ul>
                    </div>

                    <div class="footer-block rowing-left">
                        <h3>${information}</h3>
                        <ul>
                            <li><a href="some-page-that-does-not-exist">${page_404}</a></li>
                            <li><a href="mainController?command=go_to_test_page_500">${page_500}</a></li>
                        </ul>
                    </div>

                    <div class="cards rowing-right">
                        <a href="someLink" target="_blank"><img alt="" src="${pageContext.request.contextPath}/img/cards.png"></a>
                    </div>
                </div>

                <div id="footer-add" class="clear">
                    <div id="social-media" class="rowing-left">
                        <a href="http://facebook.com/" target="_blank"><img alt="" src="${pageContext.request.contextPath}/img/icon-footer-facebook.png"></a>
                        <a href="http://youtube.com/" target="_blank"><img alt="" src="${pageContext.request.contextPath}/img/icon-footer-youtube.png"></a>
                        <a href="http://twitter.com/" target="_blank"><img alt="" src="${pageContext.request.contextPath}/img/icon-footer-twitter.png"></a>
                    </div>
                    <div id="copyright" class="rowing-right">
                        <p>${copyright}: Tsalko Igor +375(33)357-76-60</p>
                    </div>
                </div>

            </div>
        </footer>
    <%--END FOOTER--%>
    </body>
</html>