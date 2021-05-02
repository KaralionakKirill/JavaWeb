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
<div class="container d-flex justify-content-center mt-2">
    <p id="server_message">${server_message}</p>
    <p id="confirmation_message">${confirmation_message}</p>
</div>
<c:if test="${not empty requestScope.pageContent}">
    <div class="d-flex justify-content-center">
        <div style="width: 70rem">
            <table class="table table-dark table-striped w-100">
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="username"/></th>
                    <th scope="col"><fmt:message key="email"/></th>
                    <th scope="col"><fmt:message key="loyaltyPoints"/></th>
                    <th scope="col"><fmt:message key="role"/></th>
                    <th scope="col"><fmt:message key="blocking"/></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.pageContent.objects}" var="user">
                    <tr>
                        <th scope="row">${user.login}</th>
                        <td>${user.email}</td>
                        <td>${user.loyaltyPoints}</td>
                        <td>
                            <select id="role-${user.id}" class="form-select">
                                <option value="${user.role}">${user.role}</option>
                                <option value="ADMIN">ADMIN</option>
                                <option value="USER">USER</option>
                                <option value="BARMAN">BARMAN</option>
                            </select>
                        </td>
                        <td>
                            <input name="block" class="form-check-input" type="checkbox"
                                   id="check-${user.id}" <c:if test="${user.blocked}">checked</c:if>>
                        </td>
                        <td>
                            <button type="button" class="btn btn-light" onclick="saveChanges(${user.id})">
                                <fmt:message key="button.save"/>
                            </button>
                        </td>
                    </tr>
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
    function saveChanges(id) {
        let data = new FormData();
        data.append('user_id', id);
        data.append('role', document.getElementById('role-' + id).value);
        data.append('block', document.getElementById('check-' + id).checked);
        data.append('command', 'update_user');
        jQuery.ajax({
            url: '<c:url value="/rest"/>',
            data: data,
            cache: false,
            contentType: false,
            processData: false,
            method: 'POST',
            success: successChange
        });
    }

    function successChange(data) {
        let pMessages = document.getElementById("server_message");
        pMessages.innerText = "";

        let cMessages = document.getElementById("confirmation_message");
        cMessages.innerText = "";

        let parse = JSON.parse(data);

        let serverMessages = parse.server_message;
        if (serverMessages != null) {
            pMessages.innerText += serverMessages + '\n';
            pMessages.classList.add("alert", "alert-danger");
        }

        let confirmMessages = parse.confirmation_message;
        if (confirmMessages != null) {
            cMessages.innerText += confirmMessages + '\n';
            cMessages.classList.add("alert", "alert-success");
        }

        setTimeout(() => {
            let redirectCommand = parse.redirect_command;
            if (redirectCommand != null) {
                window.location.href = '<c:url value="/controller"/>' + "?command=" + redirectCommand + "&page=1";
            }
        }, 3000)
    }
</script>
<c:import url="/WEB-INF/pages/parts/footer.jsp"/>
</body>
</html>
