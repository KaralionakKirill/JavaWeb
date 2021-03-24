<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>
<html>
<head>
    <c:import url="/WEB-INF/pages/parts/header.jsp"/>
    <title><fmt:message key="error.title"/></title>
</head>
<body>
<c:import url="/WEB-INF/pages/parts/navbar.jsp"/>
<div class="alert alert-warning mt-4" role="alert">
    <h3 class="alert-heading"><fmt:message key="error.title"/></h3>
    <p><fmt:message key="error.message"/></p>
    <hr>
    <p class="me-2"><fmt:message key="error.tryLater"/></p>
</div>
<c:import url="/WEB-INF/pages/parts/footer.jsp"/>
</body>
</html>
