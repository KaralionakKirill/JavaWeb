<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>
<html>
<head>
    <c:import url="header.jsp"/>
    <title><fmt:message key="registration.title"/></title>
</head>
<body>
<c:import url="navbar.jsp"/>
<div class="card text-center mt-4 mx-auto" style="width: 30rem">
    <div class="card-body">
        <h5 class="card-title"><fmt:message key="registration.confirm"/></h5>
        <p class="card-text"><fmt:message key="registration.endingMessage"/></p>
        <a href="<c:url value="https://mail.ru/"/>" class="btn btn-primary"><fmt:message key="registration.buttonToEmail"/></a>
    </div>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
