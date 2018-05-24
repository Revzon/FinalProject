<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="i18n" uri="i18n" %>

<html>
<head>
    <link href="css/style.css" rel=stylesheet type="text/css">
    <link href="css/flags.css" rel=stylesheet type="text/css">
    <title>${i18n:getMessage("registration-page")}</title>
</head>

<body>
<div id="container">
    <jsp:include page="upper-panel.jsp"/>

    <div class="panel">

        <jsp:include page="menu.jsp"/>

        <form method="POST" action="/library?action=register-submit" class="form main-form">


            <table class="plain-text">
                <tr>
                    <td colspan="2">
                        <h1>${i18n:getMessage("registration-page")}</h1>
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
                    <td><input name="firstName" type="text" placeholder="First name" pattern="[a-яA-Я]+"></td>
                </tr>
                <tr>
                    <td>${i18n:getMessage("second-name")}</td>
                    <td><input name="secondName" type="text" placeholder="Second name" pattern="[a-яA-Я]+"></td>
                </tr>
                <tr>
                    <td>${i18n:getMessage("patronymic-name")}</td>
                    <td><input name="patronymicName" type="text" placeholder="Patronymic name" pattern="[a-яA-Я]+">
                    </td>
                </tr>
                <tr>
                    <td>${i18n:getMessage("email")}</td>
                    <td><input id="email" name="email" type="text" placeholder="E-mail"
                               pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}"></td>
                </tr>
                <tr>
                    <td>${i18n:getMessage("phone")}</td>
                    <td><input id="phone" name="phone" type="text" placeholder="Phone +380*********"
                               pattern="(?:0|\(?\+380\)?\s?|0033\s?)[5-79](?:[\.\-\s]?\d\d){4}"></td>
                </tr>
                <tr>
                    <td>${i18n:getMessage("login")}</td>
                    <td><input name="username" id="username" type="text" placeholder="username"
                               pattern="[a-zA-Z][a-zA-Z0-9_]{4,20}"></td>
                </tr>
                <tr>
                    <td>${i18n:getMessage("password")}</td>
                    <td><input id="password" name="password" type="password" placeholder="Your password"
                               pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                               title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters
                               required" onkeyup="validate()" ;></td>
                </tr>
                <tr>
                    <td>${i18n:getMessage("password-confirm")}</td>
                    <td><input id="passwordConfirm" name="passwordConfirm" type="password"
                               placeholder="Repeat password"
                               pattern="(?=.{8,}$)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$"
                               onkeyup="validate()" ;></td>
                </tr>
                <tr>
                    <td colspan="2"><span id="confirmMessage" class="confirmMessage"></span></td>
                </tr>
                <tr>
                    <td colspan="2"><input name="register" type="submit" class="button" value="Register"></td>
                </tr>

            </table>

            <script type="text/javascript">
                function validate() {
                    var password = document.getElementById("password");
                    var confirmPassword = document.getElementById("passwordConfirm");
                    var confirmMessage = document.getElementById("confirmMessage");
                    var matchColor = "#66cc66";
                    var notMatchColor = "#ff0000";

                    if ((password.value == "") || (confirmPassword.value == (""))) {
                        confirmPassword.style.borderColor = null;
                        confirmMessage.style.color = null;
                        confirmMessage.innerHTML = "";
                        return false

                    } else {

                        if (password.value != confirmPassword.value) {
                            confirmPassword.style.borderColor = notMatchColor;
                            confirmMessage.style.color = notMatchColor;
                            confirmMessage.innerHTML = "Passwords are not equal!";
                            return false
                        }
                        else {
                            confirmPassword.style.borderColor = matchColor;
                            confirmMessage.style.color = matchColor;
                            confirmMessage.innerHTML = "Passwords are equal!";
                            return true
                        }
                    }
                }
            </script>

        </form>

    </div>

</div>
</body>
</html>
