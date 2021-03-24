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
    <title><fmt:message key="main.title"/></title>
</head>
<body style="background-image: url(<c:url value="/images/menu.jpg"/>); background-size: cover">
<c:import url="/WEB-INF/pages/parts/navbar.jsp"/>


<c:import url="/WEB-INF/pages/parts/footer.jsp"/>
</body>
</html>