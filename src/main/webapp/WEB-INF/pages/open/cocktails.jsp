<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="page" value="/WEB-INF/pages/open/cocktails.jsp" scope="request"/>
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>
<html>
<head>
    <c:import url="/WEB-INF/pages/parts/header.jsp"/>
    <title>Cocktails</title>
</head>
<body>
<c:import url="/WEB-INF/pages/parts/navbar.jsp"/>
<div class="d-flex justify-content-center flex-wrap">
    <c:forEach items="${requestScope.cocktails}" var="cocktail">
        <div class="card m-5" style=" height: 18rem; width: 18rem;">
            <img src="<c:url value='/data/${cocktail.imgName}'/>" class="card-img-top" alt="" style="max-height: 14rem">
            <div class="card-body text-center">
                <h3 class="card-title">${cocktail.name}</h3>
            </div>
        </div>
    </c:forEach>
</div>

<c:import url="/WEB-INF/pages/parts/footer.jsp"/>
</body>
</html>
