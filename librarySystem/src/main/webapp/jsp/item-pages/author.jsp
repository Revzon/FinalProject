<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="i18n" uri="i18n" %>
<%--${i18n:getMessage()}--%>
<html>
<head>
    <link href="css/style.css" rel=stylesheet type="text/css">
    <link href="css/flags.css" rel=stylesheet type="text/css">
    <title>${author.firstName} page</title>
</head>

<body>
<div id="container">
    <jsp:include page="../upper-panel.jsp"/>

    <div class="panel">

        <jsp:include page="../menu.jsp"/>

        <div class="form main-form">

            <h1>${author.firstName} ${author.secondName} ${author.patronymicName}</h1>

            <table class="plain-text item-table">
                <tr>
                    <td>First name</td>
                    <td><c:out value="${author.firstName}"/></td>
                </tr>
                <tr>
                    <td>Second name</td>
                    <td><c:out value="${author.secondName}"/></td>
                </tr>
                <tr>
                    <td>Patronymic name</td>
                    <td><c:out value="${author.patronymicName}"/></td>
                </tr>
            </table>

            <jsp:include page="../list-pages/book-list.jsp">
                <jsp:param name="books" value="${books}"/>
            </jsp:include>

        </div>

    </div>

</div>
</body>
</html>