<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<html>
<body>
<nav class="navbar navbar-dark bg-primary">
    <form action="<c:url value="/controller"/>" method="post">
        <input type="hidden" name="command" value="set_locale">
        <input type="hidden" name="page" value="${ requestScope.page }">
        <select name="locale" onchange="submit()" class="form-select">
            <option value="en_US" <c:if test="${locale eq 'en_US'}">selected</c:if>>English</option>
            <option value="ru_RU" <c:if test="${locale eq 'ru_RU'}">selected</c:if>>Русский</option>
        </select>
    </form>
</nav>
</body>
</html>
