<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<c:set var="page" value="/pages/login.jsp" scope="request"/>
<fmt:setLocale value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
          crossorigin="anonymous">
    <title><fmt:message key="loginTitle"/></title>
</head>
<body>
<c:import url="header.jsp"/>
<div class="container-fluid d-flex justify-content-center mt-4">
    <form action="<c:url value="/controller"/>" method="post" class="needs-validation w-25" novalidate>
        <div>
            <label class="form-label" for="loginLabel"><fmt:message key="email"/></label>
            <input type="email" class="form-control" placeholder="name@example.com" name="email"
                   id="loginLabel" pattern="([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}"
                   required>
            <div class="invalid-feedback">
                <fmt:message key="invalid.email"/>
            </div>
        </div>
        <div>
            <label class="form-label" for="passwordLabel"><fmt:message key="password"/></label>
            <input type="password" name="password" class="form-control" id="passwordLabel"
                   pattern="[a-zA-Z\d][a-zA-Z\d_]{3,16}" required>
            <div class="invalid-feedback">
                <fmt:message key="invalid.password"/>
            </div>
        </div>
        <div class="form-group mt-2">
            <button type="submit" class="btn btn-dark w-100" name="command" value="login"
                    style="margin-top: 1rem"><fmt:message key="login"/></button>
            <div class="mt-2 d-flex justify-content-center">
                <a href="${pageContext.request.contextPath}/pages/registration.jsp"><fmt:message
                        key="registration"/></a>
            </div>
        </div>
    </form>
</div>
<c:import url="scripts.jsp"/>
</body>
</html>
