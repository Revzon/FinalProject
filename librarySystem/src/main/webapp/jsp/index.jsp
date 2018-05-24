<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="i18n" uri="i18n" %>

    <html lang="${language}">

    <head>
        <title>${i18n:getMessage("library-homepage")}</title>
        <link href="css/style.css" rel=stylesheet type="text/css">
    </head>

    <body>
    <div id="container">
        <jsp:include page="upper-panel.jsp"/>

        <div class="panel">

            <jsp:include page="menu.jsp"/>

            <form class="form main-form">
                <div class="menu-text">
                    <h1>${i18n:getMessage("welcome-msg")}</h1>
                    <h1>${i18n:getMessage("start-msg")}</h1>
                </div>
                <div class="plain-text" align="left">
                    ${i18n:getMessage("about-library-msg1")}
                    <ul>
                        <li>${i18n:getMessage("about-library-li1")}</li>
                        <li>${i18n:getMessage("about-library-li2")}</li>
                        <li>${i18n:getMessage("about-library-li3")}</li>
                    </ul>

                        ${i18n:getMessage("about-library-par2")}
                </div>
            </form>
        </div>

    </div>

    </body>
    </html>
