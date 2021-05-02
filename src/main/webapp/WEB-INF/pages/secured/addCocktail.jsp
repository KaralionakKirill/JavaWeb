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
    <title><fmt:message key="cocktailAdd.title"/></title>
</head>
<body>
<c:import url="/WEB-INF/pages/parts/navbar.jsp"/>
<div class="container d-flex justify-content-center">
    <form name="add-cocktail" action="<c:url value="/rest"/>" method="post" class="needs-validation w-50" novalidate
          enctype="multipart/form-data">
        <div class="mt-2">
            <p id="server_message">${server_message}</p>
            <p id="confirmation_message">${confirmation_message}</p>
        </div>
        <input type="hidden" name="command" value="add_cocktail">
        <div class="form-group">
            <label for="img" class="form-label"><fmt:message key="cocktailAdd.title"/></label>
            <input id="img" class="form-control" type="file" name="image" accept="image/x-png,image/jpeg,image/jpg"
                   required/>
            <div class="invalid-feedback">
                <fmt:message key="prescription.img"/>
            </div>
        </div>
        <div class="form-group mt-4 row">
            <div class="col-7">
                <label for="nameId" class="form-label"><fmt:message key="cocktail.name"/></label>
                <input type="text" id="nameId" class="form-control" name="cocktail_name" pattern=".{4,30}" required/>
                <div class="invalid-feedback">
                    <fmt:message key="prescription.cocktailName"/>
                </div>
            </div>
            <div class="col-5">
                <label for="alcohol" class="form-label"><fmt:message key="cocktail.chooseAlcohol"/></label>
                <select id="alcohol" class="form-select" name="alcohol" required>
                    <option value="VODKA"><fmt:message key="vodka"/></option>
                    <option value="WHISKEY"><fmt:message key="whiskey"/></option>
                    <option value="TEQUILA"><fmt:message key="tequila"/></option>
                    <option value="GIN"><fmt:message key="gin"/></option>
                    <option value="RUM"><fmt:message key="rum"/></option>
                    <option value="CHAMPAGNE"><fmt:message key="champagne"/></option>
                </select>
            </div>
        </div>
        <div class="form-group mt-4">
            <label for='composition' class="form-label"><fmt:message key="cocktail.composition"/></label>
            <textarea id="composition" class="form-control" rows="15" type="text" name="composition"
                      maxlength="1000" minlength="10" required></textarea>
            <div class="invalid-feedback">
                <fmt:message key="prescription.composition"/>
            </div>
        </div>
        <div class="d-flex justify-content-center">
            <button type="submit" class="btn btn-dark mt-4 w-50">
                <fmt:message key="cocktailAdd.button.add"/></button>
        </div>
    </form>
</div>
<script>
    function onAjaxSuccess(data) {
        let sMessage = document.getElementById("server_message");
        sMessage.innerText = "";
        let aMessage = document.getElementById("confirmation_message");
        aMessage.innerText = "";

        let parse = JSON.parse(data);

        let serverMessage = parse.server_message;
        if (serverMessage != null) {
            sMessage.innerText += serverMessage + '\n';
            sMessage.classList.add("alert", "alert-danger");
        }

        let confirmationMessage = parse.confirmation_message;
        if (confirmationMessage != null) {
            aMessage.innerText += confirmationMessage + '\n';
            aMessage.classList.add("alert", "alert-success");
        }

        setTimeout(() => {
            let redirectCommand = parse.redirect_command;
            if (redirectCommand != null) {
                window.location.href = '<c:url value="/controller"/>' + "?command=" + redirectCommand;
            }
        }, 3000)
    }
</script>
<c:import url="/WEB-INF/pages/parts/footer.jsp"/>
</body>
</html>
