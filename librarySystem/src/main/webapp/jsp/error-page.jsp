<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <title>Error page</title>
    <link href="css/style.css" rel=stylesheet type="text/css">
    <link href="css/flags.css" rel=stylesheet type="text/css">
</head>

<body>

<div id=container>
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


    <div class="form">
        <h1>Error page</h1>
        <div id="error-img"></div>
    </div>
</div>
</body>
</html>
