<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Library Homepage</title>
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

        <form class="form main-form">
            <c:out value="Welcome to the library. Let's get started!"/>
        </form>
    </div>

</div>

</body>
</html>

