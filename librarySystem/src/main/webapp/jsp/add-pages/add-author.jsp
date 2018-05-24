<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="i18n" uri="i18n" %>
<%--${i18n:getMessage()}--%>
    <html>
    <head>
        <link href="css/style.css" rel=stylesheet type="text/css">
        <title>${i18n:getMessage("new-author")}</title>
    </head>

        <%----%>

    <body>
    <div id="container">
        <jsp:include page="../upper-panel.jsp"/>

        <div class="panel">

            <jsp:include page="../menu.jsp"/>

            <form method="POST" action="/library?action=add-author-submit" class="form main-form">


                <table class="plain-text">
                    <tr>
                        <td colspan="2">
                            <h1>${i18n:getMessage("new-author")}</h1>
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
                        <td colspan="2"><input name="register" type="submit" class="button" value="Add"></td>
                    </tr>

                </table>

            </form>

        </div>

    </div>
    </body>
    </html>