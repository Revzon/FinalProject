<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="i18n" uri="i18n" %>

<html>
<head>
    <link href="css/style.css" rel=stylesheet type="text/css">
    <link href="css/flags.css" rel=stylesheet type="text/css">
    <title>Password restore</title>
</head>

<body>
<div id="container">
    <jsp:include page="upper-panel.jsp"/>

    <div class="panel">

        <jsp:include page="menu.jsp"/>

        <form method="POST" action="/library?action=find-user-to-restore-password" class="form main-form">


            <table class="plain-text">
                <tr>
                    <td colspan="2">
                        <h1>${i18n:getMessage("restore-password")}</h1>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <h1>Step1</h1>
                    </td>
                </tr>
                <tr>s
                    <td colspan="2" class="error-message">
                        <c:if test='${errorMessage!=""}'>
                            <c:out value="${errorMessage}"/>
                        </c:if>
                    </td>
                </tr>

                <tr>
                    <td>${i18n:getMessage("username")}</td>
                    <td><input id="username" name="username" type="text" placeholder="username"></td>
                </tr>

                <tr>
                    <td colspan="2"><input name="get-username" type="submit" class="button" value="Continue"></td>
                </tr>

            </table>
        </form>

    </div>

</div>
</body>
</html>
