<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>
<c:set value="${requestScope.cocktail}" var="cockatil"/>
<html>
<head>
    <c:import url="/WEB-INF/pages/parts/header.jsp"/>
    <title>${cocktail.name}</title>
</head>
<body>
<c:import url="/WEB-INF/pages/parts/navbar.jsp"/>
<div class="container">
    <div class="d-flex justify-content-center mt-4">
        <div style="width: 20rem; height: 25rem">
            <img src="<c:url value='/data/${cocktail.imgName}'/>" style="object-fit: cover; max-width:100%; max-height:100%; " alt="">
        </div>
        <div class="card" style="width: 30rem">
            <div class="card-title mt-2 text-center">
                <h3>${cockatil.name}</h3>
            </div>
            <div class="card-body">
                <pre class="card-text">
                    ${cockatil.composition}
                </pre>
                <div class="position-absolute" style="bottom: 1rem">
                    <a class="btn btn-dark"
                       href="<c:url value="/controller?command=to_cocktails&alcohol=${cockatil.alcohol}"/>">
                        <fmt:message key="button.back"/>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
<c:import url="/WEB-INF/pages/parts/footer.jsp"/>
</body>
</html>
