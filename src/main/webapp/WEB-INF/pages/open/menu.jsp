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
    <title><fmt:message key="menu.title"/></title>
</head>
<body style="background-image: url(<c:url value="/images/menu.jpg"/>); background-size: cover">
<c:import url="/WEB-INF/pages/parts/navbar.jsp"/>
<div class="d-flex justify-content-center flex-wrap">
    <c:if test="${isAuthorized}">
        <a class="m-5" href="<c:url value="/controller?command=to_add_cocktail"/>">
            <img src="<c:url value='/images/add.png'/>" class="mt-3" style="height: 18rem; width: 18rem" alt="">
        </a>
    </c:if>

    <div class="card m-5" style="width: 20rem">
        <img class="card-img-top" src="<c:url value="/images/vodka.jpg"/>"
             alt="">
        <div class="card-body text-center">
            <h3 class="card-title"><fmt:message key="vodka"/></h3>
            <a class="btn btn-dark w-75" href="<c:url value="/controller?command=to_cocktails&alcohol=VODKA"/>">
                <fmt:message key="menu.button"/>
            </a>
        </div>
    </div>

    <div class="card m-5" style="width: 20rem">
        <img class="card-img-top"
             src="<c:url value="/images/tequila.jpg"/>" alt="">
        <div class="card-body text-center">
            <h3 class="card-title"><fmt:message key="tequila"/></h3>
            <a class="btn btn-dark w-75" href="<c:url value="/controller?command=to_cocktails&alcohol=TEQUILA"/>">
                <fmt:message key="menu.button"/>
            </a>
        </div>
    </div>

    <div class="card m-5" style="width: 20rem">
        <img class="card-img-top"
             src="<c:url value="/images/whiskey.jpg"/>" alt="">
        <div class="card-body text-center">
            <h3 class="card-title"><fmt:message key="whiskey"/></h3>
            <a class="btn btn-dark w-75" href="<c:url value="/controller?command=to_cocktails&alcohol=WHISKEY"/>">
                <fmt:message key="menu.button"/>
            </a>
        </div>
    </div>

    <div class="card m-5" style="width: 20rem">
        <img class="card-img-top"
             src="<c:url value="/images/gin.jpg"/>" alt="">
        <div class="card-body text-center">
            <h3 class="card-title"><fmt:message key="gin"/></h3>
            <a class="btn btn-dark w-75" href="<c:url value="/controller?command=to_cocktails&alcohol=GIN"/>">
                <fmt:message key="menu.button"/>
            </a>
        </div>
    </div>

    <div class="card m-5" style="width: 20rem">
        <img class="card-img-top"
             src="<c:url value="/images/rum.jpg"/>" alt="">
        <div class="card-body text-center">
            <h3 class="card-title"><fmt:message key="rum"/></h3>
            <a class="btn btn-dark w-75" href="<c:url value="/controller?command=to_cocktails&alcohol=RUM"/>">
                <fmt:message key="menu.button"/>
            </a>
        </div>
    </div>

    <div class="card m-5" style="width: 20rem">
        <img class="card-img-top" src="<c:url value="/images/champagne.jpg"/>"
             alt="">
        <div class="card-body text-center">
            <h3 class="card-title"><fmt:message key="champagne"/></h3>
            <a class="btn btn-dark w-75" href="<c:url value="/controller?command=to_cocktails&alcohol=CHAMPAGNE"/>">
                <fmt:message key="menu.button"/>
            </a>
        </div>
    </div>
</div>
<c:import url="/WEB-INF/pages/parts/footer.jsp"/>
</body>
</html>
