<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="i18n" uri="i18n" %>
<%--${i18n:getMessage()}--%>
<html>
<head>
    <title>Login page</title>
    <link href="css/style.css" rel=stylesheet type="text/css">
    <link href="css/flags.css" rel=stylesheet type="text/css">
</head>

<body>
<div id="container">
    <jsp:include page="upper-panel.jsp"/>

    <div class="panel">

        <jsp:include page="menu.jsp"/>

        <div class="form main-form">
            <form method="POST" action="/library?action=login-submit">

                <table class="plain-text">
                    <tr>
                        <td colspan="2"><h1>${i18n:getMessage("authentification")}</h1></td>
                    </tr>

                    <tr>
                        <td colspan="2" class="error-message">
                            <c:if test='${errorMessage!=""}'>
                                <c:out value="${errorMessage}"/>
                            </c:if>
                        </td>
                    </tr>

                    <tr>
                        <td>${i18n:getMessage("username")}</td>
                        <td><input name="username" id="username" type="text" placeholder="username"
                                   pattern="[a-zA-Z][a-zA-Z0-9_]{4,20}"></td>
                    </tr>

                    <tr>
                        <td>${i18n:getMessage("password")}</td>
                        <td><input name="password" type="password" placeholder="Your password"
                                   pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                                   title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters"
                                   required></td>
                    </tr>

                    <tr>
                        <td colspan="2"><input name="login" type="submit" value="Login" class="button"></td>
                    </tr>

                </table>

                <a href="library?action=restore-password-start">
                    <div style="color: white">
                        ${i18n:getMessage("forgot-password-query")}
                    </div>
                </a>

            </form>
        </div>

    </div>
</div>
</body>
</html>