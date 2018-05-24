<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="i18n" uri="i18n" %>

<html>
<head>
    <title>${i18n:getMessage("error-page")}</title>
    <link href="css/style.css" rel=stylesheet type="text/css">
    <link href="css/flags.css" rel=stylesheet type="text/css">
</head>

<body>
<div id="container">
    <jsp:include page="upper-panel.jsp"/>

    <div class="panel">

        <jsp:include page="menu.jsp"/>

        <div class="form main-form">
            <h1>${i18n:getMessage("error-page")}</h1>
            <div class="menu-text">${i18n:getMessage("error-msg")}</div>
            <div><img src="/img/ducks.png"/></div>
        </div>
    </div>

</div>
</body>
</html>