<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="i18n" uri="i18n" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Library DataBase</title>
    <link href="css/style.css" rel=stylesheet type="text/css">
    <link href="css/flags.css" rel=stylesheet type="text/css">
    <link href="css/tables.css" rel="stylesheet" type="text/css">
</head>

<body>
<div id="container">
    <jsp:include page="upper-panel.jsp"/>
    <div class="panel">
        <jsp:include page="menu.jsp"/>
        <%--&lt;%&ndash;<c:if test="${errorMessage}" &ndash;%&gt;--%>
        <div class="form main-form">
            <h1>Books List</h1>
            <table id="bookTable" class="plain-text item-table">
                <thead>
                <tr>
                    <th>#</th>
                    <th>${i18n:getMessage("title")}</th>
                    <th>${i18n:getMessage("authors")}</th>
                    <th>${i18n:getMessage("publishment")}</th>
                    <th>${i18n:getMessage("genre")}</th>
                    <th>${i18n:getMessage("keywords")}</th>
                    <th>${i18n:getMessage("reader-head")}</th>
                    <th>${i18n:getMessage("phone")}</th>
                    <th>${i18n:getMessage("return-date")}</th>
                    <th>${i18n:getMessage("overdued-period")}</th>
                </tr>
                </thead>

                <c:forEach var="book" items="${books}">
                    <tr>
                        <td>${loop.index + 1}</td>
                        <td>${book.title}</td>
                        <td>

                            <c:forEach var="author" items="${book.authors}">
                                <a href="library?action=show-author-page&id=${author.id}">${author.firstName}</a>
                            </c:forEach>

                        </td>

                        <td>${book.publishment.name}</td>
                        <td>${book.genre.name}</td>

                        <td>

                            <c:forEach var="keyword" items="${book.keywords}">
                                <a href="library?action=show-books-by-keyword&id=${keyword.id}">${keyword.name}</a>
                            </c:forEach>

                        </td>
                        <td><a href="reader_info?id=${book.reader.id}">${book.reader.login}</a></td>
                        <td>${book.reader.phone}</td>
                        <td>${book.dateOfReturn}</td>
                        <td>${book.dateOfReturn}</td>
                            <%--- currentDate--%>

                        <td>
                            <a href="library?action=update?id=${book.id}">
                                <img src="../img/edit.png" width="32px" height="32px">
                            </a>
                        </td>
                        <td>
                            <a href="library?action=delete?id=${book.id}"><img src="../img/delete.png" width="32px"
                                                                               height="32px">
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>