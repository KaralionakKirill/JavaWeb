<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<fmt:setLocale value="en_US" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
              crossorigin="anonymous">
        <title><fmt:message key="loginTitle"/></title>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light"></nav>
        <div class="card-body">
            <div class="panel panel-default">
                <div class="panel-body" style="width:18rem">
                    <form action="${pageContext.request.contextPath}/controller" method="post">
                        <div>
                            <label class="form-label" for="loginLabel"><fmt:message key="email"/></label>
                            <input type="email"  class="form-control" placeholder="name@example.com" name="uname"
                                   id="loginLabel" pattern="([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}"
                                    required>
                        </div>
                        <div>
                            <label class="form-label" for="passwordLabel"><fmt:message key="password"/> </label>
                            <input type="password" name="password" class="form-control" id="passwordLabel"
                                   pattern="[a-zA-Z\d][a-zA-Z\d_]{3,16}" required>
                        </div>
                        <button type="submit" class="btn btn-primary" name="action" value="login"
                                style="margin-top: 1rem"><fmt:message key="login"/></button>
                        <input type="hidden" name="action" value="registration">
                            <button type="submit" class="btn btn-primary"
                                    style="margin-top: 1rem"><fmt:message key="registration"/></button>
                    </form>
                </div>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
                integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
                crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
                integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
                crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
                integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
                crossorigin="anonymous"></script>
    </body>
</html>
