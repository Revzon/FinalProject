<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="i18n" uri="i18n" %>


    <link href="css/flags.css" rel=stylesheet type="text/css">

    <div class="upper-panel form">

        <div class="left-pannel">
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

        <div class="title">
            ${i18n:getMessage("library")}
        </div>

        <div class="right-pannel plain-text">

            <c:choose>
                <c:when test="${user eq Nan}">
                    ${i18n:getMessage("info-msg")}
                    <a href="library?action=login">${i18n:getMessage("login")} </a>
                    ${i18n:getMessage("or")}
                    <a href="library?action=register"> ${i18n:getMessage("register")}</a>
                </c:when>

                <c:otherwise>

                    ${i18n:getMessage("loggedas")}
                    <a href="library?action=personal-cabinet">
                            ${user.login}
                    </a>

                    <a href="library?action=logout"> ${i18n:getMessage("logout")}</a>

                </c:otherwise>

            </c:choose>
        </div>

    </div>