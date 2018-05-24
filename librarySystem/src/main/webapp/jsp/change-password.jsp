<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="i18n" uri="i18n" %>

<html>
<head>
    <link href="css/style.css" rel=stylesheet type="text/css">
</head>

<body>
<div id="container">
    <jsp:include page="upper-panel.jsp"/>

    <div class="panel">

        <jsp:include page="menu.jsp"/>

        <form method="POST" action="/library?action=change-pwd-submit" class="form main-form">


            <table class="plain-text">
                <%--<tr>--%>
                <%--<td  colspan="2">--%>
                <%--<h1><fmt:message key="password-change-form"/></h1>--%>
                <%--</td>--%>
                <%--</tr>--%>
                <%--<tr>--%>
                <%--<td  colspan="2" class="error-message">--%>
                <%--<c:if test='${errorMessage!=""}'>--%>
                <%--<c:out value="${errorMessage}"/>--%>
                <%--</c:if>--%>
                <%--</td>--%>
                <%--</tr>--%>

                <%--<tr>--%>
                <%--<td>Old Password</td>--%>
                <%--<td><input id="old-password" name="old-password" type="password" placeholder="********"--%>
                <%--pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"--%>
                <%--title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters--%>
                <%--required" onkeyup="validate()";></td>--%>
                <%--</tr>--%>

                <tr>
                    <td>
                        ${i18n:getMessage("new")} ${i18n:getMessage("password")}
                    </td>
                    <td><input id="password" name="password" type="password" placeholder="********"
                               pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                               title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters
                               required"
                    <%--onkeyup="validate()";--%>
                    ></td>
                </tr>

                <tr>
                    <td><input name="id" type="hidden" value="${id}"></td>
                </tr>
                <%--<tr>--%>
                <%--<td><fmt:message key="password-confirm"/> </td>--%>
                <%--<td><input id = "passwordConfirm" name="passwordConfirm" type="password" placeholder="********"--%>
                <%--pattern="(?=.{8,}$)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$"--%>
                <%--&lt;%&ndash;onkeyup="validate()";&ndash;%&gt;--%>
                <%--></td>--%>

                <%--</tr>--%>

                <%--<tr>--%>
                <%--<td colspan="2"> <span id="confirmMessage" class="confirmMessage"></span></td>--%>
                <%--</tr>--%>

                <%--<tr>--%>
                <%--<td colspan="2"><input name="login" type="submit" value="Change password" class="button"></td>--%>
                <%--</tr>--%>
                <tr>
                    <td colspan="2"><input name="change" type="submit" class="button" value="Change password"></td>
                </tr>

            </table>

            <%--<script type="text/javascript">--%>
            <%--function validate() {--%>
            <%--var password = document.getElementById("password");--%>
            <%--var confirmPassword = document.getElementById("passwordConfirm");--%>
            <%--var confirmMessage = document.getElementById("confirmMessage");--%>
            <%--var matchColor = "#66cc66";--%>
            <%--var notMatchColor = "#ff0000";--%>

            <%--if ((password.value == "") || (confirmPassword.value == (""))) {--%>
            <%--confirmPassword.style.borderColor = null;--%>
            <%--confirmMessage.style.color = null;--%>
            <%--confirmMessage.innerHTML = "";--%>
            <%--return false--%>

            <%--} else {--%>

            <%--if (password.value != confirmPassword.value) {--%>
            <%--confirmPassword.style.borderColor = notMatchColor;--%>
            <%--confirmMessage.style.color = notMatchColor;--%>
            <%--confirmMessage.innerHTML = "Passwords are not equal!";--%>
            <%--return false--%>
            <%--}--%>
            <%--else {--%>
            <%--confirmPassword.style.borderColor = matchColor;--%>
            <%--confirmMessage.style.color = matchColor;--%>
            <%--confirmMessage.innerHTML = "Passwords are equal!";--%>
            <%--return true--%>
            <%--}--%>
            <%--}--%>
            <%--}--%>
            <%--</script>--%>

        </form>

    </div>

</div>
</body>
</html>