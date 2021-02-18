<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>
<c:import url="/WEB-INF/pages/parts/sessionVar.jsp"/>
<nav class="navbar navbar-dark bg-dark navbar-expand-lg">
    <div class="container-fluid">
        <a class="navbar-brand" href="<c:url value="/controller?command=to_main"/>">Navbar</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Link</a>
                </li>
                <c:if test="${isAuthorized}">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page"
                           href="<c:url value="/controller?command=to_profile"/>">
                            <fmt:message key="navbar.profile"/></a>
                    </li>
                </c:if>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page"
                       href="<c:url value="/controller?command=to_add_cocktail"/>">
                        Cocktails</a>
                </li>
            </ul>
            <div class="col-4 d-flex justify-content-end">
                <form action="<c:url value="/controller"/>" method="post">
                    <input type="hidden" name="command" value="locale">
                    <input type="hidden" name="page" value="${ requestScope.page }">
                    <select id="locale" name="locale" onchange="submit()" class="form-select">
                        <option value="ru_RU" <c:if test="${locale eq 'ru_RU'}">selected</c:if>>Русский</option>
                        <option value="en_US" <c:if test="${locale eq 'en_US'}">selected</c:if>>English</option>
                    </select>
                </form>
                <c:if test="${isAuthorized}">
                    <form action="<c:url value="/controller"/>" method="post" class="ms-2">
                        <button type="submit" class="btn btn-dark" name="command" value="to_logout">
                            <fmt:message key="navbar.logout"/>
                        </button>
                    </form>
                </c:if>
                <c:if test="${ not isAuthorized}">
                    <form action="<c:url value="/controller"/>" method="post" class="ms-2">
                        <button type="submit" class="btn btn-dark" name="command" value="to_login">
                            <fmt:message key="navbar.login"/>
                        </button>
                    </form>
                </c:if>
            </div>
        </div>
    </div>
</nav>

