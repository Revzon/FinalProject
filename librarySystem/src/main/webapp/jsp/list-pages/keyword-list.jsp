<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="i18n" uri="i18n" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>${i18n:getMessage("keywords")}</title>
    <link href="css/style.css" rel=stylesheet type="text/css">
    <link href="css/flags.css" rel=stylesheet type="text/css">
    <link href="css/tables.css" rel="stylesheet" type="text/css">
</head>

<body>

<div id="container">
    <jsp:include page="../upper-panel.jsp"/>

    <div class="panel">

        <jsp:include page="../menu.jsp"/>
        <div class="form main-form">
            <h1>${i18n:getMessage("keywords")}</h1>

            <div class="info-message">
                <c:if test='${infoMessage!=""}'>
                    <c:out value="${infoMessage}"/>
                </c:if>
            </div>

            <c:choose>
                <c:when test="${keywordToEdit ne Nan}">
                    <form method="POST" action="library?action=edit-keyword-submit">

                        <table class="plain-text">
                            <tr>
                                <td>
                                    <input name="id" type="hidden" value="${keywordToEdit.id}">
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <input name="name" type="text" value="${keywordToEdit.name}">
                                </td>

                                <td colspan="2"><input name="add" type="submit" class="button" value="Edit"></td>
                            </tr>

                        </table>

                    </form>
                </c:when>

                <c:otherwise>
                    <form method="POST" action="library?action=add-keyword">

                        <table class="plain-text">
                            <tr>
                                <td>
                                    <input name="name" type="text" placeholder="keyword">
                                </td>

                                <td colspan="2">
                                    <input name="add" type="submit" class="button" value="Add">
                                </td>
                            </tr>

                        </table>

                    </form>

                </c:otherwise>

            </c:choose>

            <table id="keywordTable" class="plain-text item-table">
                <thead>
                <tr>
                    <th>#</th>
                    <th>${i18n:getMessage("name")}</th>
                    <th></th>
                    <th></th>

                </tr>
                </thead>

                <c:forEach var="keyword" items="${keywords}" varStatus="loop">
                    <tr>
                        <td>${loop.index + 1 + currentPage * itemsOnPage}</td>

                        <td>
                            <a href="library?action=findBook&searchMode=ID&searchProperty=KEYWORD&searchparameter=${keyword.id}">
                                    ${keyword.name}
                            </a>
                        </td>


                        <td>
                            <a href="library?action=edit-keyword&id=${keyword.id}">
                                <img src="../../img/edit.png" width="32px" height="32px">
                            </a>
                        </td>

                        <td>
                            <a href="library?action=delete-keyword&id=${keyword.id}">
                                <img src="../../img/delete.png" width="32px" height="32px">
                            </a>
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
                        <td><a href="library?action=show-keywords&page=${currentPage - 1}">Previous</a></td>
                    </c:if>


                    <c:forEach begin="1" end="${numberOfPages}" var="i">
                        <c:choose>
                            <c:when test="${currentPage eq i}">
                                <td>${i}</td>
                            </c:when>
                            <c:otherwise>
                                <td>
                                    <a href="library?action=show-keywords&page=${i}">
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
                        <td><a href="library?show-keywords&page=${currentPage + 1}">Next</a></td>
                    </c:if>

                </tr>
            </table>

        </div>
    </div>
</div>
</div>
</body>
</html>