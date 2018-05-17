<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
    <head>
        <title>Search page</title>
        <link href="css/style.css" rel=stylesheet type="text/css">
        <link href="css/flags.css" rel=stylesheet type="text/css">
    </head>
    <body>
    <div id = container>
        <div id = "header"></div>

        <div id="upper-panel">
            <div id="lang">
                Language:
                <div id="lang-ru">
                    <img src="/img/blank.gif" class="flag flag-ru" alt="русский"/>
                    <a href="library?action=ref&lang=ru">русский</a>
                </div>
                <div id="lang-ua">
                    <img src="/img/blank.gif" class="flag flag-ua" alt="українська"/>
                    <a href="library?action=ref&lang=ua">українська</a>
                </div>
                <div id="lang-en">
                    <img src="/img/blank.gif" class="flag flag-gb" alt="english"/>
                    <a href="library?action=ref&lang=en">english</a>
                </div>
            </div>

            <c:if test="${!user.logged}">
                <fmt:message key="notlogged"/>
            </c:if>
            <c:if test="${user.logged}">
                <fmt:message key="loggedas"/>${user.name}, <c:if
                    test="${user.type eq 'ADMIN'}">
                <fmt:message key="admin"/>
            </c:if>
                <c:if test="${user.type eq 'READER'}">
                    <fmt:message key="user"/>
                </c:if>
                <a href="library?command=logout"><fmt:message key="logout"/> </a>
            </c:if>
        </div>

        <h1>Search books here</h1>

        <select name="searchTypelist" form="search">
            <option value="author">Author</option>
            <option value="title">Title</option>
            <option value="keyword">Keyword</option>
            <option value="genre">Genre</option>
        </select>

        <form method="POST" action="/library?action=findBook" id = "search">
            <input title="searchword" name="searchparameter" id="searchparameter" type="text">
            <input type="image" src="../img/search.png" size="10%" alt="Submit" style="float:inherit;width:32px;height:32px;border: medium">
        </form>
    </div>
    </body>
</html>
