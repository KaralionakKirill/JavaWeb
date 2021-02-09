<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<c:set var="page" value="/pages/registration.jsp" scope="request"/>
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
          crossorigin="anonymous">
    <title><fmt:message key="registration.title"/></title>
</head>
<body>
<c:import url="navbar.jsp"/>
<div class="container-fluid d-flex justify-content-center mt-4">
    <form action="<c:url value="/controller"/>" method="post" class="needs-validation w-25" novalidate>
        <h2><fmt:message key="registration.caption"/></h2>
        <c:if test="${  not empty requestScope.server_message}">
            <p class="alert alert-danger" role="alert">${requestScope.server_message}</p>
        </c:if>
        <div class="mt-2">
            <label for="inputEmail"><fmt:message key="email"/></label>
            <input id="inputEmail" class="form-control" type="email" name="email" required
                   placeholder="name@example.com"/>
        </div>
        <div>
            <label for="inputLogin"><fmt:message key="username"/></label>
            <input type="text" class="form-control" id="inputLogin"
                   name="login"
                   pattern="^[(\w)-]{4,20}" required
                   placeholder="<fmt:message key="placeholder.username"/>"/>
        </div>
        <div>
            <label for="inputPassword"><fmt:message key="password"/></label>
            <input type="password" class="form-control" id="inputPassword" name="password"
                   pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+={};:><.,/?`~±§-])(?=[^\r\n\t\f\v]+$).{8,20}$"
                   required/>
        </div>
        <div>
            <label for="repeatPassword"><fmt:message key="repeat.password"/></label>
            <input type="password" class="form-control" id="repeatPassword" name="password"
                   pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+={};:><.,/?`~±§-])(?=[^\r\n\t\f\v]+$).{8,20}$"
                   required/>
        </div>
        <div class="mt-2">
            <button type="submit" class="btn btn-dark w-100" name="command" value="registration"
                    style="margin-top: 1rem"><fmt:message key="registration.button"/></button>
            <div class="mt-2 d-flex justify-content-center">
                <span>
                    <fmt:message key="registration.offer"/>
                    <a href="${pageContext.request.contextPath}/pages/login.jsp"><fmt:message
                            key="registration.login"/></a>
                </span>
            </div>
        </div>
    </form>
</div>
<c:import url="footer.jsp"/>
</body>
</html>