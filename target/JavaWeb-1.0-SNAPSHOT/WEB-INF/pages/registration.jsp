<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<fmt:setLocale value="en_US" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
              crossorigin="anonymous">
        <title><fmt:message key="loginTitle"/></title>
    </head>
    <body>
        <div class="card-body">
            <div class="panel panel-default">
                <div class="panel-body" style="width:18rem">
                    <form action="${pageContext.request.contextPath}/controller" method="post">
                        <div>
                            <label class="form-label">First name</label>
                            <input type="text" class="form-control" required>
                            <div class="valid-feedback">
                                Looks good!
                            </div>
                        </div>
                        <div>
                            <label class="form-label">Last name</label>
                            <input type="text" class="form-control" required>
                            <div class="valid-feedback">
                                Looks good!
                            </div>
                        </div>
                        <div>
                            <label class="form-label"><fmt:message key="email"/></label>
                            <div class="input-group has-validation">
                                <input type="text" class="form-control" required>
                                <div class="invalid-feedback">
                                    Please choose a username.
                                </div>
                            </div>
                        </div>
                        <div>
                            <label><fmt:message key="password"/></label>
                            <input type="password" name="password" class="form-control"
                                   required pattern="[a-zA-Z\d][a-zA-Z\d_]{3,16}">
                        </div>
                        <div>
                            <button class="btn btn-primary" type="submit"><fmt:message key="registration"/></button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>