<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="i18n" uri="i18n" %>

<html>
<head>
    <title>${i18n:getMessage("library-catalog")}</title>
    <link href="css/style.css" rel=stylesheet type="text/css">
    <link href="css/selector.css" rel="stylesheet" type="text/css">
</head>

<body>
<div id="container">
    <jsp:include page="upper-panel.jsp"/>

    <div class="panel">

        <jsp:include page="menu.jsp"/>

        <div class="form main-form">

            <h1>${i18n:getMessage("book-list")}</h1>

            <form method="POST" action="/library?action=findBook">

                <table class="plain-text">
                    <tr id="search">
                        <td colspan="2">${i18n:getMessage("search")}</td>
                        <td>
                            <div class="styled-select slate plain-text" style="width:200px;">
                                <select name="searchProperty">
                                    <option value="TITLE">${i18n:getMessage("title")}</option>
                                    <option value="AUTHOR">${i18n:getMessage("author")}</option>
                                    <option value="KEYWORD">${i18n:getMessage("keyword")}</option>
                                    <option value="GENRE">${i18n:getMessage("genre")}</option>
                                    <option value="PUBLISHMENT">${i18n:getMessage("publishment")}</option>
                                </select>
                            </div>
                        </td>
                        <td><input class="search-input" title="searchword" name="searchparameter"
                                   id="searchparameter" type="text"></td>
                        <td><input name="searchMode" type="hidden" value="NON_STRICT"></td>
                        <td><input type="submit" class="button" name="search" id="search-button" value="Search">
                        </td>
                    </tr>
                </table>
            </form>

            <jsp:include page="list-pages/book-list.jsp">
                <jsp:param name="books" value="${books}"/>
            </jsp:include>

        </div>
    </div>

</div>
</body>
</html>
