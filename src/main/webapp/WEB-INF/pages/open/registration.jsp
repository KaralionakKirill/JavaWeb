<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<c:set var="page" value="/WEB-INF/pages/open/registration.jsp" scope="request"/>
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>
<html>
<head>
    <c:import url="../parts/header.jsp"/>
    <title><fmt:message key="registration.title"/></title>
</head>
<body>
<c:import url="../parts/navbar.jsp"/>
<div class="container-fluid d-flex justify-content-center mt-4">
    <form name="registration" action="<c:url value="/rest"/>" method="post" class="needs-validation w-25" novalidate>
        <h2><fmt:message key="registration.caption"/></h2>
        <p id="server_message">${server_message}</p>
        <input type="hidden" name="command" value="registration">
        <div class="mt-2">
            <label for="inputEmail"><fmt:message key="email"/></label>
            <input id="inputEmail" class="form-control" type="email" name="email" required
                   placeholder="name@example.com"/>
            <div class="invalid-feedback">
                <fmt:message key="invalid.email"/>
            </div>
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
            <div class="invalid-feedback">
                <fmt:message key="invalid.password"/>
            </div>
        </div>
        <div>
            <label for="repeatPassword"><fmt:message key="repeat.password"/></label>
            <input type="password" class="form-control" id="repeatPassword" name="password"
                   pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+={};:><.,/?`~±§-])(?=[^\r\n\t\f\v]+$).{8,20}$"
                   required/>
            <div class="invalid-feedback">
                <fmt:message key="invalid.password"/>
            </div>
        </div>
        <div class="mt-2">
            <button id="submitButton" type="submit" class="btn btn-dark w-100"><fmt:message key="registration.button"/></button>
            <div class="mt-2 d-flex justify-content-center">
                <span>
                    <fmt:message key="registration.offer"/>
                    <a href="<c:url value="/controller?command=to_login"/>"><fmt:message
                            key="registration.login"/></a>
                </span>
            </div>
        </div>
    </form>
    <script>
        function onAjaxSuccess(data) {
            let pMessages = document.getElementById("server_message");
            pMessages.innerText = "";
            let parse = JSON.parse(data);

            let serverMessages = parse.server_message;
            if (serverMessages != null) {
                pMessages.innerText += serverMessages + '\n';
                pMessages.classList.add("alert", "alert-danger");
            }
            let redirectCommand = parse.redirect_command;
            if (redirectCommand != null) {
                window.location.href = '<c:url value="/controller"/>' + "?command=" + redirectCommand
            }
        }
    </script>
</div>
<c:import url="../parts/footer.jsp"/>
</body>
</html>