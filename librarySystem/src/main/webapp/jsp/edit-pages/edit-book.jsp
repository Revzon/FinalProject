<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="i18n" uri="i18n" %>

<html>
<head>
    <link href="css/style.css" rel=stylesheet type="text/css">
    <link href="css/flags.css" rel=stylesheet type="text/css">
    <title>Edit book ${book.title}</title>
</head>

<body>
<div id="container">
    <jsp:include page="../upper-panel.jsp"/>

    <div class="panel">

        <jsp:include page="../menu.jsp"/>

        <div class="form main-form">

            <form method="POST" action="/library?action=edit-book-submit" accept-charset="utf-8">
                <table class="plain-text">
                    <tr colspan="2">
                        <h1>Edit book</h1>
                    </tr>

                    <tr>
                        <td><input name="id" type="hidden" value="${book.id}"></td>
                    </tr>

                    <tr>
                        <td>Title</td>
                        <td><input name="title" type="text" value="${book.title}"></td>
                    </tr>

                    <tr>
                        <td>Authors</td>
                        <td>
                            <div class="styled-select slate plain-text" style="width:200px;">
                                <select name="authors" multiple>
                                    <c:forEach items="${authorList}" var="author">
                                        <option value="${author.id}"
                                                c:if test="${lu:contains( book.authors, author) }"
                                                selected>
                                                ${author.firstName}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>Publishment</td>
                        <td>
                            <select name="publishment">
                                <c:forEach items="${publishmentList}" var="publishmentValue">
                                    <option value="${publishmentValue.id}"
                                            c:if test="${lu:same( book.publishment.id, publishmentValue.id) }"
                                            selected>
                                            ${publishmentValue.name}
                                    </option>
                                </c:forEach>
                            </select>

                        </td>
                    </tr>


                    <tr>
                        <td>Genre</td>
                        <td>
                            <div class="styled-select slate plain-text" style="width:200px;">
                                <select name="genre">
                                    <c:forEach items="${genreList}" var="genreValue">
                                        <option value="${genreValue.id}"
                                                c:if test="${lu:same( book.genre.id, genreValue.id) }"
                                                selected>
                                                ${genreValue.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </td>

                    </tr>

                    <tr>
                        <td>Keywords</td>
                        <td>
                            <div class="styled-select slate plain-text" style="width:200px;">
                                <select name="keywords" multiple>
                                    <c:forEach items="${keywordList}" var="keyword">
                                        <option value="${keyword.id}"
                                                c:if test="${lu:contains( book.keywords, keyword) }"
                                                selected>
                                                ${keyword.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </td>

                    </tr>


                    <tr>
                        <td>Location</td>
                        <td>
                            <div class="styled-select slate plain-text" style="width:200px;">
                                <select name="location">
                                    <c:forEach items="${shelfList}" var="shelfValue">
                                        <option value="${shelfValue.id}"
                                                c:if test="${lu:same( book.location.id, shelfValue.id) }"
                                                selected>
                                                ${shelfValue.section.name} ${shelfValue.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </td>
                    </tr>


                    <tr>
                        <td>Avilable</td>
                        <td>
                            <input id="avilable" name="avilable" type="checkbox" value="${book.avilable}">
                        </td>

                    </tr>

                    <tr>
                        <td>Reader</td>
                        <td>
                            <div class="styled-select slate plain-text" style="width:200px;">
                                <select name="reader">
                                    <c:forEach items="${userList}" var="userValue">
                                        <option value="${userValue.id}"
                                                c:if test="${lu:same( book.reader.id, userValue.id) }"
                                                selected>
                                                ${userValue.login}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </td>

                    </tr>

                    <tr>
                        <td>Date to return</td>
                        <td><input name="dateOfReturn" id="dateOfReturn" type="date" value="${book.dateOfReturn}">
                        </td>
                    </tr>


                    <tr>
                        <td colspan="2"><input name="edit-book" type="submit" class="button" value="Save"></td>
                    </tr>

                </table>

            </form>

        </div>

    </div>

</div>
</body>
</html>