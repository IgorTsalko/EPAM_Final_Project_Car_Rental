<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@include file="header.jsp"%>
<title>${news_title}</title>

<fmt:message key="news.all_news" var="all_news_title"/>

<%--START MAIN-CONTENT--%>
<div id="content">
    <div class="container main-content">

        <h1><span class="page-title">${all_news_title}</span></h1>

        <c:forEach items="${requestScope.all_news}" var="news">
            <c:choose>
                <c:when test="${fn:startsWith(sessionScope.locale, 'en')}">
                    <div class="news">
                        <div class="news-title">
                            <h2>${news.titleEN}</h2>
                        </div>
                        <div class="news-info">
                            <p>${news.textEN}</p>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="news">
                        <div class="news-title">
                            <h2>${news.titleRU}</h2>
                        </div>
                        <div class="news-info">
                            <p>${news.textRU}</p>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
            <hr>
        </c:forEach>
    </div>
</div>
<%--END MAIN-CONTENT--%>

<%@include file="footer.jsp"%>