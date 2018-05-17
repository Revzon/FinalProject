<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:requestEncoding value="UTF-8" />
<fmt:setLocale value="${userLocale}" />
<fmt:bundle basename="properties.text">
    <%--
      Created by IntelliJ IDEA.
      User: olga
      Date: 15.05.18
      Time: 2:22
      To change this template use File | Settings | File Templates.
    --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div class="upper-panel form">
    <div class="lang">
    Language:
    </div>
    <div class = "lang lang-ua">
    <a href = "library?action=ref&lang=ua"><img src="/img/blank.gif" class="flag flag-ua" alt="Ukr" /></a>
    </div>

    <div class = "lang lang-ru">
    <a href = "library?action=ref&lang=ru"><img src="/img/blank.gif" class="flag flag-ru" alt="Ru" /></a>
    </div>

    <div class = "lang lang-en">
    <a href = "library?action=ref&lang=en"><img src="/img/blank.gif" class="flag flag-gb" alt="En" /></a>
    </div>
    </div>

</body>
</html>
