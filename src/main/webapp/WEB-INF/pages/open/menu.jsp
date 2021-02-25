<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<c:set var="page" value="/WEB-INF/pages/open/menu.jsp" scope="request"/>
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>
<html>
<head>
    <c:import url="/WEB-INF/pages/parts/header.jsp"/>
    <title><fmt:message key="menu.title"/></title>
</head>
<body>
<c:import url="/WEB-INF/pages/parts/navbar.jsp"/>
<div class="d-flex justify-content-center flex-wrap">
    <div class="card m-5" style="width: 20rem">
        <img class="card-img-top" src="<c:url value="/data/3fcbf3f5-f054-4152-addb-9c7185578fc9GI_hHwTS5xc.jpg"/>"
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
             src="<c:url value="/data/3fcbf3f5-f054-4152-addb-9c7185578fc9GI_hHwTS5xc.jpg"/>" alt="">
        <div class="card-body text-center">
            <h3 class="card-title"><fmt:message key="tequila"/></h3>
            <a class="btn btn-dark w-75" href="<c:url value="/controller?command=to_cocktails&alcohol=TEQUILA"/>">
                <fmt:message key="menu.button"/>
            </a>
        </div>
    </div>

    <div class="card m-5" style="width: 20rem">
        <img class="card-img-top"
             src="<c:url value="/data/3fcbf3f5-f054-4152-addb-9c7185578fc9GI_hHwTS5xc.jpg"/>" alt="">
        <div class="card-body text-center">
            <h3 class="card-title"><fmt:message key="whiskey"/></h3>
            <a class="btn btn-dark w-75" href="<c:url value="/controller?command=to_cocktails&alcohol=WHISKEY"/>">
                <fmt:message key="menu.button"/>
            </a>
        </div>
    </div>

    <div class="card m-5" style="width: 20rem">
        <img class="card-img-top"
             src="<c:url value="/data/3fcbf3f5-f054-4152-addb-9c7185578fc9GI_hHwTS5xc.jpg"/>" alt="">
        <div class="card-body text-center">
            <h3 class="card-title"><fmt:message key="gin"/></h3>
            <a class="btn btn-dark w-75" href="<c:url value="/controller?command=to_cocktails&alcohol=GIN"/>">
                <fmt:message key="menu.button"/>
            </a>
        </div>
    </div>

    <div class="card m-5" style="width: 20rem">
        <img class="card-img-top"
             src="<c:url value="/data/3fcbf3f5-f054-4152-addb-9c7185578fc9GI_hHwTS5xc.jpg"/>" alt="">
        <div class="card-body text-center">
            <h3 class="card-title"><fmt:message key="rum"/></h3>
            <a class="btn btn-dark w-75" href="<c:url value="/controller?command=to_cocktails&alcohol=RUM"/>">
                <fmt:message key="menu.button"/>
            </a>
        </div>
    </div>
</div>
<c:import url="/WEB-INF/pages/parts/footer.jsp"/>
</body>
</html>
