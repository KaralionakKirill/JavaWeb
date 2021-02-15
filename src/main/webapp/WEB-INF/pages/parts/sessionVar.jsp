<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="isAuthorized" value="${not empty sessionScope.user}" scope="session"/>
<c:if test="${isAuthorized}">
    <c:set var="user" value="${sessionScope.user}" scope="session"/>
</c:if>

