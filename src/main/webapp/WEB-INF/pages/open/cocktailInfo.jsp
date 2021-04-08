<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>
<c:set value="${sessionScope.cocktail}" var="cocktail"/>
<html>
<head>
    <c:import url="/WEB-INF/pages/parts/header.jsp"/>
    <title>${cocktail.name}</title>
</head>
<body style="position: relative">
<c:import url="/WEB-INF/pages/parts/navbar.jsp"/>
<div class="container">
    <div class="d-flex justify-content-center mt-4">
        <div class="row">
            <div class="col-4">
                <img src="<c:url value='/uploads/${cocktail.imgName}'/>"
                     style="object-fit: cover; max-width:100%; max-height:100%; " alt="">
            </div>
            <div class="col-7">
                <div class="card">
                    <div class="card-header mt-2 text-center">
                        <h3>${cocktail.name}</h3>
                    </div>
                    <div class="card-body">
                        <div class="card-title mt-2"><h5><fmt:message key="rate"/> ${cocktail.rate}/5</h5></div>
                        <pre class="card-text mt-2">${cocktail.composition}</pre>
                        <div class="card-footer">
                            <a class="btn btn-dark"
                               href="<c:url value="/controller?command=to_cocktails&alcohol=${cocktail.alcohol}"/>">
                                <fmt:message key="button.back"/>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="mt-2 mb-2">
        <p id="server_message"></p>
    </div>
    <div class="mt-4 mb-4 d-flex justify-content-center">
        <div class="card" style="width: 50rem">
            <div class="card-header">
                <h4><fmt:message key="review.title"/></h4>
            </div>
            <div class="card-body">
                <form name="reviews" method="post" action="<c:url value="/rest"/>" class="needs-validation" novalidate>
                    <input type="hidden" name="command" value="add_review">
                    <div class="form-group mt-2">
                        <h5><fmt:message key="review.rate"/></h5>
                        <div class="rate">
                            <input type="radio" id="star5" name="rate" value="5"/>
                            <label for="star5">5 stars</label>
                            <input type="radio" id="star4" name="rate" value="4"/>
                            <label for="star4">4 stars</label>
                            <input type="radio" id="star3" name="rate" value="3"/>
                            <label for="star3">3 stars</label>
                            <input type="radio" id="star2" name="rate" value="2"/>
                            <label for="star2">2 stars</label>
                            <input type="radio" id="star1" name="rate" value="1"/>
                            <label for="star1">1 star</label>
                        </div>
                    </div>
                    <textarea class="form-control w-100 mb-2" name="feedback"
                              placeholder="<fmt:message key="review.placeholder"/>" rows="5"
                              maxlength="500" minlength="2" required></textarea>
                    <div class="invalid-feedback">
                        <fmt:message key="prescription.feedback"/>
                    </div>
                    <div class="d-flex justify-content-between">
                        <a href="<c:url value="/controller?command=to_login"/>" class="text-decoration-none">
                            <fmt:message key="review.link"/>
                        </a>
                        <c:if test="${isAuthorized}">
                            <button type="submit" class="btn btn-dark">
                                <fmt:message key="review.button.send"/>
                            </button>
                        </c:if>
                        <c:if test="${not isAuthorized}">
                            <button class="btn btn-dark" disabled>
                                <fmt:message key="review.button.send"/>
                            </button>
                        </c:if>
                    </div>
                </form>
                <hr>
                <ul>
                    <c:forEach items="${requestScope.reviews}" var="review">
                        <li>
                            <h5>${review.author.login}</h5>
                            <h6><fmt:message key="review.rate"/> ${review.rate}</h6>
                            <p>${review.feedback}</p>
                            <hr>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</div>
<style>
    .rate {
        float: left;
        height: 46px;
        padding: 0;
        margin: 0 0 25px;
    }

    .rate:not(:checked) > input {
        position: absolute;
        top: -9999px;
    }

    .rate:not(:checked) > label {
        float: right;
        width: 1em;
        overflow: hidden;
        white-space: nowrap;
        cursor: pointer;
        font-size: 30px;
        color: #ccc;
    }

    .rate:not(:checked) > label:before {
        content: '★ ';
    }

    .rate > input:checked ~ label {
        color: #ffc700;
    }

    .rate:not(:checked) > label:hover,
    .rate:not(:checked) > label:hover ~ label {
        color: #deb217;
    }

    .rate > input:checked + label:hover,
    .rate > input:checked + label:hover ~ label,
    .rate > input:checked ~ label:hover,
    .rate > input:checked ~ label:hover ~ label,
    .rate > label:hover ~ input:checked ~ label {
        color: #c59b08;
    }
</style>
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

        let cocktail_id = parse.cocktail_id
        let redirectCommand = parse.redirect_command;
        if (redirectCommand != null) {
            window.location.href = '<c:url value="/controller"/>' + "?command=" +
                redirectCommand + "&cocktail_id=" + cocktail_id;
        }
    }
</script>
<c:import url="/WEB-INF/pages/parts/footer.jsp"/>
</body>
</html>
