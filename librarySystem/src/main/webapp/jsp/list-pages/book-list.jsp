<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="i18n" uri="i18n" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="css/style.css" rel=stylesheet type="text/css">
<link href="css/tables.css" rel="stylesheet" type="text/css">

<c:choose>
    <c:when test="${user.role.name() eq 'ADMIN'}">
        <div>
            <a href="library?action=add-book">
                <div class="button">
                    <img src="../../img/add.png" width="32px" height="32px">
                        ${i18n:getMessage("add")}
                </div>
            </a>
        </div>
    </c:when>
</c:choose>

<c:choose>
    <c:when test="${books.size() eq '0'}">
        <h2>
                ${i18n:getMessage("no-books-found")}
        </h2>
    </c:when>

    <c:otherwise>

        <table id="bookTable" class="plain-text item-table">
            <thead>
            <tr>
                <th>#</th>
                <th>${i18n:getMessage("title")}</th>
                <th>${i18n:getMessage("authors")}</th>
                <th>${i18n:getMessage("publishment")}</th>
                <th>${i18n:getMessage("genre")}</th>
                <th>${i18n:getMessage("keywords")}</th>
                <th>${i18n:getMessage("avilable")}</th>

                <c:if test="${user ne Nan}">
                    <th>${i18n:getMessage("return-date")}</th>
                    <c:if test="${user.role.name() eq 'ADMIN'}">
                        <th>${i18n:getMessage("location")}</th>
                        <th>${i18n:getMessage("reader-head")}</th>
                        <th></th>
                        <th></th>
                    </c:if>
                    <th></th>
                </c:if>
            </tr>
            </thead>

            <c:forEach var="book" items="${books}" varStatus="loop">

                <tr>
                    <td>${loop.index + 1 + currentPage * itemsOnPage}</td>
                    <td>${book.title}</td>

                    <td>
                        <c:forEach var="author" items="${book.authors}">
                            <a href="library?action=findBook&searchMode=ID&searchProperty=AUTHOR&searchparameter=${author.id}">
                                    ${author.firstName}
                            </a>
                        </c:forEach>

                    </td>

                    <td>
                        <a href="library?action=findBook&searchMode=ID&searchProperty=PUBLISHMENT&searchparameter=${book.publishment.id}">
                                ${book.publishment.name}
                        </a>
                    </td>

                    <td>
                        <a href="library?action=findBook&searchMode=ID&searchProperty=GENRE&searchparameter=${book.genre.id}">
                                ${book.genre.name}
                        </a>
                    </td>

                    <td>
                        <c:forEach var="keyword" items="${book.keywords}">
                            <a href="library?action=findBook&searchMode=ID&searchProperty=KEYWORD&searchparameter=${keyword.id}">
                                    ${keyword.name}
                            </a>
                        </c:forEach>
                    </td>


                    <td>
                        <c:if test="${book.avilable}">
                            <img src="../../img/ok-red.png" width="32px" height="32px">
                        </c:if>
                    </td>

                    <c:if test="${user ne Nan}">

                        <td>${book.dateOfReturn}</td>

                        <c:if test="${user.role.name() eq 'ADMIN'}">

                            <td>
                                <a href="library?action=findBook&searchMode=ID&searchProperty=LOCATION&searchparameter=${book.location.id}">
                                        ${book.location.section.name}/${book.location.name}
                                </a>
                            </td>

                            <td>
                                <a href="library?action=findBook&searchMode=ID&searchProperty=READER&searchparameter=${book.reader.id}">
                                        ${book.reader.login}
                                </a>
                            </td>

                            <td>
                                <a href="library?action=edit-book&id=${book.id}">
                                    <img src="../../img/edit.png" width="32px" height="32px">
                                </a>
                            </td>
                            <td>
                                <a href="library?action=delete-book&id=${book.id}">
                                    <img src="../../img/delete.png" width="32px" height="32px">
                                </a>
                            </td>
                        </c:if>

                        <td>
                            <c:choose>
                                <c:when test="${book.avilable}">
                                    <a href="library?action=borrow-book&id=${book.id}">
                                        <img src="../../img/hand.png" width="32px"
                                             height="32px">
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${user.id == book.reader.id}">
                                        <a href="library?action=return-book&id=${book.id}">
                                            <img src="../../img/submit.png" width="32px"
                                                 height="32px">
                                        </a>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <%--</c:choose>--%>
                    </c:if>
                </tr>
            </c:forEach>
        </table>


        <%--For displaying Page numbers.
        The when condition does not display a link for the current page--%>
        <table cellpadding="5" cellspacing="5">
            <tr>
                    <%--For displaying Previous link except for the 1st page --%>
                <c:if test="${currentPage ne '1'}">
                    <td><a href="library?action=show-books&page=${currentPage - 1}">Previous</a></td>
                </c:if>
                <c:forEach begin="1" end="${numberOfPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <td>${i}</td>
                        </c:when>
                        <c:otherwise>
                            <td>
                                <a href="library?action=show-books&page=${i}">
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
                    <td><a href="library?action=show-books&page=${currentPage + 1}">Next</a></td>
                </c:if>
            </tr>
        </table>

    </c:otherwise>

</c:choose>
