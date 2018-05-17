<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:requestEncoding value="UTF-8" />
<fmt:setLocale value="${userLocale}" />
<fmt:bundle basename="properties.text">

    <html>
<head>
    <title>Taken books</title>
</head>
<body>
    <h1>List of taken books</h1>
<table border="1">
    <c:forEach var="b" items="${books}">
        <tr>
            <td>${b.id}</td>
            <td>${b.title}</td>
            <td>${b.authors}</td>
            <td>${b.reader}</td>
            <td>${b.dateOfReturn}</td>
            <td><a href="updateReturn?id=${b.id}">Returned</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>