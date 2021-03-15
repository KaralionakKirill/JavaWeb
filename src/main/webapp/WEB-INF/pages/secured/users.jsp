<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="app" uri="appTag" %>
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>
<html>
<head>
    <c:import url="/WEB-INF/pages/parts/header.jsp"/>
    <title><fmt:message key="users.title"/></title>
</head>
<body>
<c:import url="/WEB-INF/pages/parts/navbar.jsp"/>
<p id="server_message">${server_message}</p>
<c:if test="${not empty requestScope.pageContent}">
    <div class="d-flex justify-content-center">
        <div style="width: 70rem">
            <table class="table table-dark table-striped w-100">
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="username"/></th>
                    <th scope="col"><fmt:message key="email"/></th>
                    <th scope="col"><fmt:message key="firstname"/></th>
                    <th scope="col"><fmt:message key="lastname"/></th>
                    <th scope="col"><fmt:message key="loyaltyPoints"/></th>
                    <th scope="col"><fmt:message key="role"/></th>
                    <th scope="col"><fmt:message key="isActivated"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.pageContent.objects}" var="user">
                    <form name="${user.login}" action="<c:url value="/rest"/>" method="post">
                        <tr>
                            <th scope="row">${user.login}</th>
                            <td>${user.email}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.loyaltyPoints}</td>
                            <td>
                                <input type="hidden" name="command" value="change_role">
                                <input type="hidden" name="user_id" value="${user.id}">
                                <select name="role" onchange="changeRole(form.name)" class="form-select">
                                    <option value="${user.role}">${user.role}</option>
                                    <option value="ADMIN">ADMIN</option>
                                    <option value="USER">USER</option>
                                    <option value="BARMAN">BARMAN</option>
                                </select>
                            </td>
                            <c:if test="${user.activated eq 'false'}">
                                <td><fmt:message key="activated"/></td>
                            </c:if>
                            <c:if test="${user.activated eq 'true'}">
                                <td><fmt:message key="notActivated"/></td>
                            </c:if>
                        </tr>
                    </form>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="d-flex justify-content-center m-4">
        <ul class="pagination text-dark">
            <app:pagination pages="${requestScope.pageContent.totalPages}"
                            page="${requestScope.pageContent.page}"
                            url="/controller?command=to_users&page="/>
        </ul>
    </div>
</c:if>
<script>
    function changeRole(name) {
        let form = document.forms[name];
        let data = new FormData(form);
        jQuery.ajax({
            url: '<c:url value="/rest"/>',
            data: data,
            cache: false,
            contentType: false,
            processData: false,
            method: 'POST',
            success: onAjaxSuccess
        });
    }

    function onAjaxSuccess(data) {
        let pMessages = document.getElementById("server_message");
        pMessages.innerText = "";
        let parse = JSON.parse(data);
        let serverMessages = parse.server_message;
        if (serverMessages != null) {
            pMessages.innerText += serverMessages + '\n';
            pMessages.classList.add("alert", "alert-danger");
        }
    }
</script>
<c:import url="/WEB-INF/pages/parts/footer.jsp"/>
</body>
</html>
