<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<c:set var="page" value="/pages/registration.jsp" scope="request"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="property.pagecontent"/>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
              crossorigin="anonymous">
        <title><fmt:message key="registration"/></title>
    </head>
    <body>
    <nav class="navbar navbar-dark bg-primary">
        <c:import url="locale-form.jsp"/>
    </nav>
        <div class="card-body">
            <div class="panel panel-default">
                <div class="panel-body" style="width:18rem">
                    <form action="${pageContext.request.contextPath}/controller" method="post" class="needs-validation" novalidate>
                        <div>
                            <label for="inputLogin"><fmt:message key="username"/></label>
                            <input type="text" class="form-control" id="inputLogin"
                                   name="login"
                                   pattern="^[(\w)-]{4,20}" required="" placeholder="<fmt:message key="placeholder.username"/>"/>
                        </div>
                        <div>
                            <label for="inputPassword"><fmt:message key="password"/></label>
                            <input type="password" class="form-control" id="inputPassword" name="password"
                                   pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+={};:><.,/?`~±§-])(?=[^\r\n\t\f\v]+$).{8,20}$"
                                   required=""/>
                        </div>
                        <div>
                            <label for="inputBirthDate">Birthday</label>
                            <input type="date" name="date_of_birth" required="required" value="2000-01-01" min="1900-01-01"
                                   class="form-control"
                                   id="inputBirthDate"/>
                        </div>
                        <div>
                            <label for="inputGender">Gender</label>
                            <select name="gender" id="inputGender" class="form-control">
                                <option value="male">male</option>
                                <option value="female">female</option>
                            </select>
                        </div>
                        <div>
                            <label for="inputFirstname"><fmt:message key="firstname"/></label>
                            <input id="inputFirstname" class="form-control" type="text" name="first_name"
                                   pattern="[A-Za-zА-Яа-яЁё]{2,30}" required=""/>
                        </div>
                        <div>
                            <label for="inputLastname"><fmt:message key="lastname"/></label>
                            <input id="inputLastname" class="form-control" type="text" name="last_name"
                                   pattern="[A-Za-zА-Яа-яЁё]{2,30}" required=""/>
                        </div>
                        <div>
                            <label for="inputEmail"><fmt:message key="email"/></label>
                            <input id="inputEmail" class="form-control" type="email" name="email" required=""
                                   placeholder="name@example.com"/>
                        </div>
                        <div>
                            <button type="submit" class="btn btn-primary" name="command" value="registration"
                                    style="margin-top: 1rem"><fmt:message key="registration"/></button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="js/validator.js"></script>
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