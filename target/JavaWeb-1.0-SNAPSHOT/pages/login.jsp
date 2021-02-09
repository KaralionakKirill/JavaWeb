<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<c:set var="page" value="/pages/login.jsp" scope="request"/>
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>
<html>
<head>
    <c:import url="header.jsp"/>
    <title><fmt:message key="login.title"/></title>
</head>
<body>
<c:import url="navbar.jsp"/>
<div class="container d-flex justify-content-center mt-4">
    <form action="<c:url value="/controller"/>" method="post" class="needs-validation w-25" novalidate>
        <h2><fmt:message key="login.caption"/></h2>
        <c:if test="${  not empty requestScope.server_message}">
            <p class="alert alert-danger" role="alert">${requestScope.server_message}</p>
        </c:if>
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
                   required>
            <div class="invalid-feedback">
                <fmt:message key="invalid.password"/>
            </div>
        </div>
        <div class="form-group mt-2">
            <button type="submit" class="btn btn-dark w-100" name="command"
                    value="login"><fmt:message key="login.button"/></button>
            <div class="mt-2 d-flex justify-content-center">
                <span>
                    <fmt:message key="login.offer"/>
                    <a href="${pageContext.request.contextPath}/pages/registration.jsp"><fmt:message
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
            }
            let redirectCommand = parse.redirect_command;
            if (redirectCommand != null) {
                window.location.href = '<c:url value="/controller"/>' + "?command=" + redirectCommand;
            }
        }
    </script>

</div>
<c:import url="footer.jsp"/>
</body>
</html>
