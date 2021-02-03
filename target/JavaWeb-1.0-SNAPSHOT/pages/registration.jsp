<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<c:set var="page" value="/pages/registration.jsp" scope="request"/>
<fmt:setLocale value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
          crossorigin="anonymous">
    <title><fmt:message key="registration"/></title>
</head>
<body>
<c:import url="header.jsp"/>
<div class="container-fluid d-flex justify-content-center mt-4">
    <form action="${pageContext.request.contextPath}/controller" method="post" class="needs-validation w-25"
          novalidate>
        <div class="row-6 mt-2">
            <div class="col-8">
                <label for="inputFirstname"><fmt:message key="firstname"/></label>
                <input id="inputFirstname" class="form-control" type="text" name="first_name"
                       pattern="[A-Za-zА-Яа-яЁё]{2,30}" required=""/>
            </div>
            <div class="col-8">
                <label for="inputLastname"><fmt:message key="lastname"/></label>
                <input id="inputLastname" class="form-control" type="text" name="last_name"
                       pattern="[A-Za-zА-Яа-яЁё]{2,30}" required=""/>
            </div>
        </div>
        <div class="row-6 mt-2">
            <div class="col-8">
                <label for="inputLogin"><fmt:message key="username"/></label>
                <input type="text" class="form-control" id="inputLogin"
                       name="login"
                       pattern="^[(\w)-]{4,20}" required=""
                       placeholder="<fmt:message key="placeholder.username"/>"/>
            </div>
            <div class="col-8 mt-2">
                <label for="inputEmail"><fmt:message key="email"/></label>
                <input id="inputEmail" class="form-control" type="email" name="email" required=""
                       placeholder="name@example.com"/>
            </div>
        </div>
        <div class="mt-2">
            <label for="inputPassword"><fmt:message key="password"/></label>
            <input type="password" class="form-control" id="inputPassword" name="password"
                   pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+={};:><.,/?`~±§-])(?=[^\r\n\t\f\v]+$).{8,20}$"
                   required=""/>
        </div>
        <div class="mt-2">
            <button type="submit" class="btn btn-dark" name="command" value="registration"
                    style="margin-top: 1rem"><fmt:message key="registration"/></button>
        </div>
    </form>
</div>
<c:import url="scripts.jsp"/>
</body>
</html>