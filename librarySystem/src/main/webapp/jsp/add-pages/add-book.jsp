<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="i18n" uri="i18n" %>

<html>
<head>
    <link href="css/style.css" rel=stylesheet type="text/css">
    <link href="css/flags.css" rel=stylesheet type="text/css">
    <title>Add book</title>
</head>

<body>
<div id="container">
    <jsp:include page="../upper-panel.jsp"/>

    <div class="panel">

        <jsp:include page="../menu.jsp"/>

        <div class="form main-form">

            <form method="POST" action="/library?action=add-book-submit">
                <table class="plain-text">
                    <tr colspan="2">
                        <h1>New book</h1>
                    </tr>

                    <tr>
                        <td>Title</td>
                        <td><input name="title" type="text" placeholder="Title"></td>
                    </tr>

                    <tr>
                        <td>Authors</td>
                        <td>
                            <div class="plain-text" style="width:200px;">
                                <select name="authors" multiple>
                                    <c:forEach items="${authorList}" var="author">
                                        <option value="${author.id}">
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
                                    <option value="${publishmentValue.id}">
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
                                        <option value="${genreValue.id}">
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
                            <div class="plain-text" style="width:200px;">
                                <select name="keywords" multiple>
                                    <c:forEach items="${keywordList}" var="keyword">
                                        <option value="${keyword.id}">
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
                                        <option value="${shelfValue.id}">
                                                ${shelfValue.section.name} ${shelfValue.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </td>
                    </tr>


                    <tr>
                        <td colspan="2"><input name="add-book" type="submit" class="button" value="Add"></td>
                    </tr>

                </table>

            </form>

        </div>

    </div>

</div>
</body>
</html>