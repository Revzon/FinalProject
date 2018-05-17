<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <title>Login page</title>
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

    <h1>Authentification</h1>

    <form method="POST" action="/library?action=authentificate" id="form-style">
        <label for="username">Username</label>
        <input name="username" id = "username" type="text" placeholder="username" pattern="[a-zA-Z][a-zA-Z0-9_]{4,20}">
        <label for="password">Password</label>
        <input id = "password" name="password" type="password" placeholder="Your password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
               title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters" required>
        <p><input name="login" id="button" type="submit" value="Login"></p>
    </form>
</div>
</body>
</html>

