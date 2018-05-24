<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="i18n" uri="i18n" %>


<html>
<head>
    <link href="css/style.css" rel=stylesheet type="text/css">
    <link href="css/flags.css" rel=stylesheet type="text/css">
    <title>${i18n:getMessage("personal-page")}: ${user.firstName} </title>
</head>

<body>
<div id="container">
    <jsp:include page="../upper-panel.jsp"/>

    <div class="panel">

        <jsp:include page="../menu.jsp"/>

        <div class="form main-form">
            <div>
                <h1>${user.firstName} ${user.secondName} ${user.patronymicName}</h1>

                <table class="plain-text">
                    <tr>
                        <td>${i18n:getMessage("first-name")}</td>
                        <td><c:out value="${user.firstName}"/></td>
                    </tr>
                    <tr>
                        <td>${i18n:getMessage("second-name")}</td>
                        <td><c:out value="${user.secondName}"/></td>
                    </tr>
                    <tr>
                        <td>${i18n:getMessage("patronymic-name")}</td>
                        <td><c:out value="${user.patronymicName}"/></td>
                    </tr>
                    <tr>
                        <td>${i18n:getMessage("phone")}</td>
                        <td><c:out value="${user.phone}"/></td>
                    </tr>
                    <tr>
                        <td>${i18n:getMessage("email")}</td>
                        <td><c:out value="${user.email}"/></td>
                    </tr>
                    <tr>
                        <td>${i18n:getMessage("login-head")}</td>
                        <td><c:out value="${user.login}"/></td>
                    </tr>


                    <tr>
                        <td colspan="2">
                            <a href="library?action=edit-user&id=${user.id}">
                                <div class="button">
                                    <img src="../../img/edit.png" width="32px" height="32px">
                                    ${i18n:getMessage("edit-personal-info-link")}
                                </div>
                            </a>
                        </td>
                    </tr>

                    <tr>
                        <td colspan="2">

                            <a href="library?action=change-password&id=${user.id}">
                                <div class="button">
                                    <img src="../../img/spy.png" width="32px" height="32px">
                                    ${i18n:getMessage("change-password")}
                                </div>
                            </a>
                        </td>
                    </tr>
                </table>


                <jsp:include page="../list-pages/book-list.jsp">
                    <jsp:param name="books" value="${books}"/>
                </jsp:include>

            </div>
            <c:if test="${borrowedBooks.size() > 0 }">
                <div>

                    <h1>${i18n:getMessage("book-list")}</h1>
                    <table id="bookTable" class="plain-text item-table">
                        <thead>
                        <tr>

                            <th>#</th>
                            <th>${i18n:getMessage("title")}</th>
                            <th>${i18n:getMessage("authors")}</th>
                            <th>${i18n:getMessage("publishment")}</th>
                            <th>${i18n:getMessage("genre")}</th>
                            <th>${i18n:getMessage("keywords")}</th>
                            <th>${i18n:getMessage("return-date")}</th>
                            <th>${i18n:getMessage("time-left")}</th>

                        </tr>
                        </thead>

                        <c:forEach var="book" items="${borrowedBooks}" varStatus="loop">
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

                                <td>${book.dateOfReturn}</td>
                                <td>${book.dateOfReturn.before(book.dateOfReturn)}</td>

                                <td>
                                    <a href="library?action=return-book&id=${book.id}">
                                        <div class="button">
                                            <img src="../../img/submit.png" width="32px" height="32px" class="button"
                                                 title="Return book">
                                                ${i18n:getMessage("return")}
                                        </div>
                                    </a>
                                </td>

                            </tr>
                        </c:forEach>
                    </table>

                </div>
            </c:if>
        </div>

</body>
</html>

