<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="i18n" uri="i18n" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>${i18n:getMessage("library-users")}</title>
    <link href="css/style.css" rel=stylesheet type="text/css">
    <link href="css/tables.css" rel="stylesheet" type="text/css">
</head>

<body>
<div id="container">
    <jsp:include page="../upper-panel.jsp"/>

    <div class="panel">

        <jsp:include page="../menu.jsp"/>

        <div class="form main-form">
            <h1>${i18n:getMessage("library-users")}</h1>

            <div class="info-message">
                <c:if test='${infoMessage!=""}'>
                    <c:out value="${infoMessage}"/>
                </c:if>
            </div>

            <div>
                <a href="library?action=add-user">
                    <div class="button">
                        <img src="../../img/add.png" width="32px" height="x">
                        ${i18n:getMessage("add-user")}
                    </div>
                </a>
            </div>

            <table id="userTable" class="plain-text item-table">
                <thead>
                <tr>
                    <th>#</th>
                    <th>${i18n:getMessage("first-name")}</th>
                    <th>${i18n:getMessage("second-name")}</th>
                    <th>${i18n:getMessage("patronymic-name")}</th>
                    <th>${i18n:getMessage("role")}</th>
                    <th>${i18n:getMessage("login")}</th>
                    <th>${i18n:getMessage("phone")}</th>
                    <th>${i18n:getMessage("email")}</th>
                    <th></th>
                    <th></th>

                </tr>
                </thead>

                <c:forEach var="u" items="${users}" varStatus="loop">
                    <tr>
                        <td>${loop.index + 1}</td>
                        <td>${u.firstName}</td>
                        <td>${u.secondName}</td>
                        <td>${u.patronymicName}</td>

                        <td>${u.role.name()}</td>
                        <td>${u.login}</td>
                        <td>${u.email}</td>
                        <td>${u.phone}</td>
                        <td>
                            <a href="library?action=user-page&id=${u.id}">
                                <img src="../../img/edit.png" width="32px" height="32px">
                            </a>
                        </td>
                        <td>
                            <a href="library?action=delete-user&id=${u.id}">
                                <img src="../../img/delete.png" width="32px" height="32px">
                            </a>
                        </td>

                        <td>
                            <c:choose>
                                <c:when test="${u.role.name() eq 'READER'}">
                                    <a href="library?action=change-role&id=${u.id}&role=0">
                                        MAKE ADMIN
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${u.role.name() eq 'ADMIN'}">
                                        <a href="library?action=change-role&id=${u.id}&role=1">
                                            MAKE READER
                                        </a>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </td>

                    </tr>
                </c:forEach>
            </table>

            <%--For displaying Page numbers.
            The when condition does not display a link for the current page--%>
            <table cellpadding="5" cellspacing="5">
                <tr>
                    <%--For displaying Previous link except for the 1st page --%>
                    <c:if test="${currentPage ne '1'}">
                        <td><a href="library?action=show-users&page=${currentPage - 1}">Previous</a></td>
                    </c:if>

                    <c:forEach begin="1" end="${numberOfPages}" var="i">
                        <c:choose>
                            <c:when test="${currentPage eq i}">
                                <td>${i}</td>
                            </c:when>
                            <c:otherwise>
                                <td>
                                    <a href="library?action=show-users&page=${i}">
                                        <div class="button">
                                                ${i}
                                        </div>
                                    </a>
                                </td>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <%--For displaying Next link --%>
                    <c:if test="${currentPage lt numberOfPages}">
                        <td><a href="library?show-users&page=${currentPage + 1}">Next</a></td>
                    </c:if>
                </tr>
            </table>


        </div>
    </div>
</div>
</div>
</body>
</html>