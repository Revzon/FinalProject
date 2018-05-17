<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:requestEncoding value="UTF-8" />
<fmt:setLocale value="${userLocale}" />
<fmt:bundle basename="properties.text">

    <html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Library DataBase</title>
    </head>
<body>
    <h1>Books List</h1>
<table border="1">
    <c:forEach var="b" items="${books}">
        <tr>
            <td>${b.id}</td>
            <td>${b.title}</td>
            <td>${b.authors}</td>
            <td>${b.publishment}</td>
            <td>${b.genre}</td>
            <td>${b.keywords}</td>
            <td>${b.aviable}</td>
            <td>${b.location}</td>
            <%--<td><a href="reader_info?id=${b.reader.id}"></a>${b.reader.name}</td>--%>
            <td>${b.dateOfReturn}</td>
            <td><a href="delete?id=${b.id}">Delete</a></td>
            <td><a href="update?id=${b.id}">Edit</a></td>
        </tr>
    </c:forEach>
</table>
<form action="addbook" method="POST">
    Новая запись<br/>
    id <input type="text" name="id" value="" /><br/>
    title<input type="text" name="title" value="" /><br/>
    authors<input type="text" name="authors" value="" /><br/>
    publishment<input type="text" name="publishment" value="" /><br/>
    genre<input type="text" name="genre" value="" /><br/>
    keywords<input type="text" name="keywords" value="" /><br/>
    aviable<input type="text" name="aviable" value="" /><br/>
    reader<input type="text" name="reader" value="" /><br/>
    dateOfReturn<input type="date" name="dateOfReturn" value="" /><br/>
    location<input type="text" name="location" value="" /><br/>
    <input type="submit" value="Add" />
</form>
</body>
</html>
