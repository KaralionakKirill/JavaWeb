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
    <title><fmt:message key="barman.title"/></title>
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
                    <th scope="col"><fmt:message key="cocktailName"/></th>
                    <th scope="col"><fmt:message key="alcohol"/></th>
                    <th scope="col"><fmt:message key="author"/></th>
                    <th scope="col"><fmt:message key="rate"/></th>
                    <th scope="col"><fmt:message key="isApproved"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.pageContent.objects}" var="cocktail">
                    <tr data-bs-toggle="modal" data-bs-target="#modal"
                        onclick='cocktailViewing(${app:objToJSON(cocktail)})'>
                        <th scope="row">${cocktail.name}</th>
                        <td>${cocktail.alcohol}</td>
                        <td>${cocktail.author.login}</td>
                        <td>${cocktail.rate}/5</td>
                        <c:if test="${cocktail.approved eq 'false'}">
                            <td><fmt:message key="barman.cocktail.notApproved"/></td>
                        </c:if>
                        <c:if test="${cocktail.approved eq 'true'}">
                            <td><fmt:message key="barman.cocktail.approved"/></td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <div class="d-flex justify-content-center m-4">
        <ul class="pagination">
            <app:pagination pages="${requestScope.pageContent.totalPages}"
                            page="${requestScope.pageContent.page}"
                            url="/controller?command=to_all_cocktails&page="/>
        </ul>
    </div>
</c:if>

<div class="modal fade" id="modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog d-flex justify-content-center">
        <div class="modal-content" style="width: 50rem">
            <div class="modal-header">
                <h5 class="modal-title" id="modalTitle"></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form name="edit" action="<c:url value="/rest"/>" method="post" class="needs-validation" novalidate>
                <div class="modal-body">
                    <div class="d-flex justify-content-center">
                        <div style="width: 20rem; height: 25rem">
                            <img id="img" alt="" src="" style="object-fit: cover; max-width:100%; max-height:100%;">
                        </div>
                        <div style="width: 30rem">
                            <input type="hidden" name="command" id="command">
                            <input type="hidden" name="cocktail_id" id="cocktail_id">
                            <div class=" mt-4 ">
                                <label for="nameId" class="form-label"><fmt:message key="cocktail.name"/></label>
                                <input type="text" id="nameId" class="form-control" name="cocktail_name"
                                       pattern=".{4,30}" required/>
                                <div class="invalid-feedback">
                                    <fmt:message key="prescription.cocktailName"/>
                                </div>
                            </div>
                            <div class="mt-4">
                                <label for='composition' class="form-label"><fmt:message
                                        key="cocktail.composition"/></label>
                                <textarea id="composition" class="form-control h-50" type="text" name="composition"
                                          maxlength="500" minlength="10" required></textarea>
                                <div class="invalid-feedback">
                                    <fmt:message key="prescription.composition"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-bs-dismiss="modal">
                        <fmt:message key="button.close"/>
                    </button>
                    <button type="submit" class="btn btn-dark" onclick='changeInputValue("delete")'>
                        <fmt:message key="button.delete"/>
                    </button>
                    <button type="submit" class="btn btn-dark" onclick='changeInputValue("edit")'>
                        <fmt:message key="button.save"/>
                    </button>
                    <button id="approveButton" type="submit" onclick='changeInputValue("endorse")' class="btn btn-dark">
                        <fmt:message key="barman.button.approved"/>
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    function changeInputValue(command) {
        document.getElementById("command").value = command + "_cocktail";
    }

    function cocktailViewing(obj) {
        document.getElementById('cocktail_id').value = obj.id;
        document.getElementById('modalTitle').innerText = obj.name;
        document.getElementById('nameId').value = obj.name;
        document.getElementById('composition').value = obj.composition;
        document.getElementById('img').src = '/uploads/'.concat(obj.imgName);
        document.getElementById('approveButton').disabled = !!obj.approved;
    }

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
