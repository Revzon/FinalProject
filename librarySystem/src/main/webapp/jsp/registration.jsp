<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <title>Registration page</title>
    <link href="css/style.css" rel=stylesheet type="text/css">
    <link href="css/flags.css" rel=stylesheet type="text/css">
</head>

<%----%>

<body>
<div id="container">
    <div class="upper-panel form">
        <div class="lang">
            Language:
        </div>
        <div class="lang lang-ua">
            <a href="library?action=ref&lang=ua"><img src="/img/blank.gif" class="flag flag-ua" alt="Ukr"/></a>
        </div>

        <div class="lang lang-ru">
            <a href="library?action=ref&lang=ru"><img src="/img/blank.gif" class="flag flag-ru" alt="Ru"/></a>
        </div>

        <div class="lang lang-en">
            <a href="library?action=ref&lang=en"><img src="/img/blank.gif" class="flag flag-gb" alt="En"/></a>
        </div>

    </div>


    <div class="panel">

        <ul class="form sidebar">
            <li><a href="library?action=login">Welcome page</a></li>
            <li><a href="search-page.jsp">Search book</a></li>
            <li><a href="error-page.jsp">Library catalog</a></li>
        </ul>

        <form method="POST" action="/library?action=register-submit" class="form main-form">

            <table class="plain-text">
                <tr>
                    <td><h1>Registration form</h1></td>
                </tr>
                <tr>
                    <td>First name</td>
                    <td><input name="firstName" type="text" placeholder="First name" pattern="[a-яA-Я]+"></td>
                </tr>
                <tr>
                    <td>Second name</td>
                    <td><input name="secondName" type="text" placeholder="Second name" pattern="[a-яA-Я]+"></td>
                </tr>
                <tr>
                    <td>Patronimyc name</td>
                    <td><input name="patronimycName" type="text" placeholder="Patronimyc name" pattern="[a-яA-Я]+"></td>
                </tr>
                <tr>
                    <td>Email</td>
                    <td><input id="email" name="email" type="text" placeholder="E-mail"
                               pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}"></td>
                </tr>
                <tr>
                    <td>Phone</td>
                    <td><input id="phone" name="phone" type="text" placeholder="Phone +380*********"
                               pattern="(?:0|\(?\+380\)?\s?|0033\s?)[5-79](?:[\.\-\s]?\d\d){4}"></td>
                </tr>
                <tr>
                    <td>Username</td>
                    <td><input name="username" id="username" type="text" placeholder="username"
                               pattern="[a-zA-Z][a-zA-Z0-9_]{4,20}"></td>
                </tr>
                <tr>
                    <td>Date of birth</td>
                    <td><input name="birthday" type="date" placeholder="Date of birth"
                               pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}"></td>
                </tr>

                <tr>
                    <td>Password</td>
                    <td><input name="password" type="password" placeholder="Your password"
                               pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                               title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters"
                               required></td>
                </tr>
                <tr>
                    <td>Password (repeat)</td>
                    <td><input name="passwordControl" type="password" placeholder="Repeat password"
                               pattern="(?=.{8,}$)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$"></td>
                </tr>
                <tr>
                    <td><input name="register" type="submit" class="button" value="Register"></td>
                </tr>

            </table>

        </form>
    </div>

</div>
</body>
</html>

