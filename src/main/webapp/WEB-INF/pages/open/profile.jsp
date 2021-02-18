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
<body style="background-image: url(/images/3.jpg)">
<c:import url="/WEB-INF/pages/parts/navbar.jsp"/>
<div class="d-flex justify-content-center">
    <div class="w-50">
        <p class="display-2 text-center">Профиль</p>
        <div class="card mt-4">
            <h4 class="card-header">Данные</h4>
            <div class="card-body" style="width: 500px;">
                <h5 class="card-title">Info</h5>
                <p class="card-text"><fmt:message key="loyaltyPoints"/>: ${user.loyaltyPoints}</p>
                <p class="card-text"><fmt:message key="username"/>: ${user.login}</p>
                <p class="card-text"><fmt:message key="email"/>: ${user.email}</p>
                <c:if test="${user.role eq 'ADMIN'}">
                    <div>
                        <table class="table">
                            <thead>
                            <tr>
                                <th scope="col"><fmt:message key="username"/></th>
                                <th scope="col"><fmt:message key="email"/></th>
                                <th scope="col"><fmt:message key="firstname"/></th>
                                <th scope="col"><fmt:message key="lastname"/></th>
                                <th scope="col">Role</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${requestScope.users}" var="user">
                                <tr>
                                    <th scope="row">${user.login}</th>
                                    <td>${user.email}</td>
                                    <td>${user.firstName}</td>
                                    <td>${user.lastName}</td>
                                    <td>${user.role}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
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
