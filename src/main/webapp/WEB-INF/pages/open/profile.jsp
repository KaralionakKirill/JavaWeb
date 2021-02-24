<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="page" value="/WEB-INF/pages/open/profile.jsp" scope="request"/>
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>
<html>
<head>
    <c:import url="/WEB-INF/pages/parts/header.jsp"/>
    <title><fmt:message key="profile.title"/></title>
</head>
<body>
<c:import url="/WEB-INF/pages/parts/navbar.jsp"/>
<div class="d-flex justify-content-center">
    <div class="w-50">
        <div class="card mt-4">
            <h4 class="card-header"><fmt:message key="profile.data"/></h4>
            <div class="card-body" style="width: 500px;">
                <p class="card-text"><fmt:message key="loyaltyPoints"/>: ${user.loyaltyPoints}</p>
                <p class="card-text"><fmt:message key="username"/>: ${user.login}</p>
                <p class="card-text"><fmt:message key="email"/>: ${user.email}</p>
            </div>
        </div>
    </div>
</div>
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
            window.location.href = '<c:url value="/controller"/>' + "?command=" + redirectCommand
        }
    }
</script>
<c:import url="/WEB-INF/pages/parts/footer.jsp"/>
</body>
</html>
