<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="i18n" uri="i18n" %>

<div class="form sidenav">
    <table>

        <tr>
            <td><a href="library?action=homepage">${i18n:getMessage("welcome-page")}</a></td>
        </tr>

        <tr>
            <td><a href="library?action=search-page">${i18n:getMessage("search-books")}</a></td>
        </tr>

        <c:if test="${user != Nan}">

            <c:if test="${user.role.name() eq 'ADMIN'}">

                <tr>
                    <td><a href="library?action=overdued-books-list">
                            ${i18n:getMessage("borrowed")}
                            ${i18n:getMessage("books")}</a>
                    </td>
                </tr>

                <tr>
                    <td>
                        <a href="library?action=show-users">
                                ${i18n:getMessage("users-list")}
                        </a>
                    </td>
                </tr>

                <tr>
                    <td>
                        <a href="library?action=show-shelfs">
                                ${i18n:getMessage("shelfs")}
                        </a>
                    </td>
                </tr>


                <tr>
                    <td>
                        <a href="library?action=show-sections">
                                ${i18n:getMessage("sections")}
                        </a>
                    </td>
                </tr>

                <tr>
                    <td>
                        <a href="library?action=show-keywords">
                                ${i18n:getMessage("keywords")}
                        </a>
                    </td>
                </tr>

                <tr>
                    <td>
                        <a href=" library?action=show-authors">
                                ${i18n:getMessage("authors")}
                        </a>
                    </td>
                </tr>

                <tr>
                    <td>
                        <a href="library?action=show-publishments">
                                ${i18n:getMessage("publishments")}
                        </a>
                    </td>
                </tr>

                <tr>
                    <td>
                        <a href=" library?action=show-genres">
                                ${i18n:getMessage("genres")}
                        </a>
                    </td>
                </tr>

            </c:if>

        </c:if>

    </table>

</div>