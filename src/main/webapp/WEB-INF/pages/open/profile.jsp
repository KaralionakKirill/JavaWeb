<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>
<html>
<head>
    <c:import url="/WEB-INF/pages/parts/header.jsp"/>
    <title><fmt:message key="profile.title"/></title>
</head>
<body style="background-image: url(<c:url value="/images/profile.jpg"/>); background-size: cover">
<c:import url="/WEB-INF/pages/parts/navbar.jsp"/>
<div class="container d-flex justify-content-center mt-2">
    <p id="server_message">${server_message}</p>
    <p id="confirmation_message">${confirmation_message}</p>
</div>
<div class="d-flex justify-content-center">
    <div class="card mt-4">
        <h4 class="card-header"><fmt:message key="profile.data"/></h4>
        <div class="card-body" style="width: 750px;">
            <div class="row">
                <p class="card-text col"><fmt:message key="username"/>: ${user.login}</p>
                <p class="card-text col"><fmt:message key="loyaltyPoints"/>: ${user.loyaltyPoints}</p>
            </div>
            <p class="card-text"><fmt:message key="email"/>: ${user.email}</p>
            <div class="row">
                <p class="card-text col"><fmt:message key="firstname"/>: ${user.firstName}</p>
                <p class="card-text col"><fmt:message key="lastname"/>: ${user.lastName}</p>
            </div>
            <button data-bs-toggle="modal" data-bs-target="#modal" class="btn btn-dark mt-2" type="button">
                <fmt:message key="profile.button.edit"/>
            </button>
        </div>
    </div>
</div>
<div class="modal fade" id="modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"><fmt:message key="profile.title.edit"/></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form name="edit" action="<c:url value="/rest"/>" method="post" class="needs-validation"
                  novalidate accept-charset="UTF-8">
                <div class="modal-body">
                    <input type="hidden" name="command" value="user_edit_profile">
                    <div class="row mt-3">
                        <div class="col-6">
                            <label for="first-name" class="form-label"><fmt:message key="firstname"/></label>
                            <input type="text" id="first-name" name="first_name" class="form-control"
                                   value='${user.firstName}' pattern="^[A-Za-zА-Яа-яЁё']{2,20}?$" required/>
                            <div class="invalid-feedback">
                                <fmt:message key="prescription.firstName"/>
                            </div>
                        </div>
                        <div class="col-6">
                            <label for="last-name" class="form-label"><fmt:message key="lastname"/></label>
                            <input type="text" id="last-name" name="last_name" class="form-control"
                                   value='${user.lastName}' pattern="^[A-Za-zА-Яа-яЁё']{2,20}?$" required/>
                            <div class="invalid-feedback">
                                <fmt:message key="prescription.lastName"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        <fmt:message key="button.close"/>
                    </button>
                    <button type="submit" class="btn btn-dark">
                        <fmt:message key="button.save"/>
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    function onAjaxSuccess(data) {
        $('#modal').modal('hide');
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
