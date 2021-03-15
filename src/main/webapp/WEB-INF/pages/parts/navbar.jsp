<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>
<c:import url="/WEB-INF/pages/parts/sessionVar.jsp"/>
<nav class="navbar navbar-dark bg-dark navbar-expand-lg">
    <div class="container-fluid d-flex justify-content-between">
        <a class="navbar-brand" href="<c:url value="/controller?command=to_main"/>">
            <img src="<c:url value="/images/logo.png"/>" style="width: 32px; height: 32px" alt="">
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <div class="navbar-nav me-auto mb-2 mb-lg-0">
                <c:if test="${isAuthorized}">
                    <div class="nav-item">
                        <a class="nav-link active" aria-current="page"
                           href="<c:url value="/controller?command=to_profile"/>">
                            <fmt:message key="navbar.profile"/></a>
                    </div>
                </c:if>
                <c:if test="${user.role eq 'ADMIN'}">
                    <div class="nav-item">
                        <a class="nav-link active" aria-current="page"
                           href="<c:url value="/controller?command=to_users&page=1"/>">
                            <fmt:message key="navbar.users"/></a>
                    </div>
                </c:if>
                <c:if test="${user.role eq 'BARMAN'}">
                    <div class="nav-item">
                        <a class="nav-link active" aria-current="page"
                           href="<c:url value="/controller?command=to_all_cocktails&page=1"/>">
                            <fmt:message key="navbar.allCocktails"/></a>
                    </div>
                </c:if>
                <div class="nav-item">
                    <a class="nav-link active" aria-current="page"
                       href="<c:url value="/controller?command=to_menu"/>">
                        <fmt:message key="navbar.menu"/></a>
                </div>
            </div>
            <div class="d-flex">
                <form action="<c:url value="/controller"/>" method="post" class="m-2">
                    <input type="hidden" name="command" value="locale">
                    <input type="hidden" name="currUrl" id="currUrl">
                    <select id="locale" name="locale" onchange="submit()" class="form-select">
                        <option value="ru_RU" <c:if test="${locale eq 'ru_RU'}">selected</c:if>>Русский</option>
                        <option value="en_US" <c:if test="${locale eq 'en_US'}">selected</c:if>>English</option>
                    </select>
                </form>
                <c:if test="${isAuthorized}">
                    <form action="<c:url value="/controller"/>" method="post" class="m-2">
                        <button type="submit" class="btn btn-dark" name="command" value="to_logout">
                            <fmt:message key="navbar.logout"/>
                        </button>
                    </form>
                </c:if>
                <c:if test="${ not isAuthorized}">
                    <form action="<c:url value="/controller"/>" method="post" class="m-2">
                        <button type="submit" class="btn btn-dark" name="command" value="to_login">
                            <fmt:message key="navbar.login"/>
                        </button>
                    </form>
                </c:if>
            </div>
        </div>
    </div>
</nav>

