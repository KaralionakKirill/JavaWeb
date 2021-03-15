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
    <title><fmt:message key="login.title"/></title>
</head>
<body>
<c:import url="/WEB-INF/pages/parts/navbar.jsp"/>
<div class="container d-flex justify-content-center mt-4">
    <form name="login" action="<c:url value="/rest"/>" method="post" class="needs-validation w-25" novalidate>
        <h2><fmt:message key="login.caption"/></h2>
        <c:if test="${not empty requestScope.verification_message}">
            <p class="alert alert-success">${requestScope.verification_message}</p>
        </c:if>
        <p id="server_message">${server_message}</p>
        <input type="hidden" name="command" value="login">
        <div>
            <label class="form-label" for="emailLabel"><fmt:message key="email"/></label>
            <input type="email" class="form-control" name="email" id="emailLabel"
                   pattern="^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$" required/>
            <div class="invalid-feedback">
                <fmt:message key="prescription.email"/>
            </div>
        </div>
        <div>
            <label class="form-label" for="passwordLabel"><fmt:message key="password"/></label>
            <input type="password" name="password" class="form-control" id="passwordLabel"
                   pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z_!@#$%^&*]{6,}" required/>
            <div class="invalid-feedback">
                <fmt:message key="prescription.password"/>
            </div>
        </div>
        <div class="form-group mt-2">
            <button type="submit" class="btn btn-dark w-100"><fmt:message key="login.button"/></button>
            <div class="mt-2 d-flex justify-content-center">
                <span>
                    <fmt:message key="login.offer"/>
                    <a href="<c:url value="/controller?command=to_registration"/>"><fmt:message
                            key="login.signup"/></a>
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
                window.location.href = '<c:url value="/controller"/>' + "?command=" + redirectCommand;
            }
        }
    </script>
</div>
<c:import url="/WEB-INF/pages/parts/footer.jsp"/>
</body>
</html>
