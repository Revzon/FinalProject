<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="i18n" uri="i18n" %>

<html>
<head>
    <link href="css/style.css" rel=stylesheet type="text/css">
    <link href="css/flags.css" rel=stylesheet type="text/css">
    <title>${i18n:getMessage("edit-personal-data")}/></title>
</head>

<body>
<div id="container">
    <jsp:include page="../upper-panel.jsp"/>

    <div class="panel">

        <jsp:include page="../menu.jsp"/>

        <form method="POST" action="/library?action=edit-user-submit" class="form main-form">

            <table class="plain-text">
                <tr>
                    <td colspan="2">
                        <h1>${i18n:getMessage("edit-personal-data")}</h1>
                    </td>
                </tr>
                <tr>
                    <td><input name="id" type="hidden" value="${user.id}">
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <h1>${user.firstName} ${user.secondName}${user.patronymicName}</h1>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" class="error-message">
                        <c:if test='${errorMessage!=""}'>
                            <c:out value="${errorMessage}"/>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>${i18n:getMessage("first-name")}</td>
                    <td><input name="firstName" type="text" value="${user.firstName}"></td>
                </tr>
                <tr>
                    <td>${i18n:getMessage("second-name")}</td>
                    <td><input name="secondName" type="text" value="${user.secondName}"></td>
                </tr>
                <tr>
                    <td>${i18n:getMessage("patronymic-name")}</td>
                    <td><input name="patronymicName" type="text" value="${user.patronymicName}">
                    </td>
                </tr>
                <tr>
                    <td>${i18n:getMessage("email")}</td>
                    <td><input id="email" name="email" type="text" value="${user.email}"
                               pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}"></td>
                </tr>
                <tr>
                    <td>${i18n:getMessage("phone")}</td>
                    <td><input id="phone" name="phone" type="text" value="${user.phone}"
                               pattern="(?:0|\(?\+380\)?\s?|0033\s?)[5-79](?:[\.\-\s]?\d\d){4}"></td>
                </tr>
                <tr>
                    <td colspan="2"><input name="submit" type="submit" class="button" value="Submit"></td>
                </tr>

            </table>

        </form>

    </div>

</div>
</body>
</html>