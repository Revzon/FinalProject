<%@ taglib prefix="i18n" uri="i18n" %>
<%--${i18n:getMessage()}--%>

<div class="info-message">
    <c:if test='${infoMessage!=""}'>
        <c:out value="${infoMessage}"/>
    </c:if>
</div>
<div class="error-message">
    <c:if test='${errorMessage!=""}'>
        <c:out value="${errorMessage}"/>
    </c:if>
</div>
