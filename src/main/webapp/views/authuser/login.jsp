<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>login-Page</title>
    <jsp:include page="/fragment/css.jsp"/>
</head>
<body>

<div class="container mt-5">
    <div class="row">
        <div class="col-md-6">
            <h1>Login</h1>
            <c:if test="${error_message != null}">
                <div class="alert alert-danger">
                        ${error_message }
                </div>
            </c:if>
            <form action="/auth/login?next=${next}" method="post">
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="text" class="form-control" id="email" name="email" required>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Password</label>
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>
                <div class="mb-3 form-check">
                    <input type="checkbox" class="form-check-input" id="rememberMe" name="rememberMe">
                    <label class="form-check-label" for="rememberMe">Remember me ?</label>
                </div>
                <button type="submit" class="btn btn-success">Login</button>
            </form>
        </div>
        <div class="col-md-6">
            <h1>Register</h1>
            <c:if test="${not empty validationErrors}">
                <ul style="color: red;">
                    <c:forEach var="error" items="${validationErrors}">
                        <li>${error.value}</li> <%-- YOKI ${error} --%>
                    </c:forEach>
                </ul>
            </c:if>
            <form action="/auth/register" method="post">
                <div class="mb-3">
                    <label for="p_email" class="form-label">Email address</label>
                    <input type="text" class="form-control" id="p_email" name="email">
                    <c:if test="${email_error != null}">
                        <snap class="text-danger">${email_error}</snap>
                    </c:if>
                </div>
                <div class="mb-3">
                    <label for="p_password" class="form-label">Password</label>
                    <input type="password" class="form-control" id="p_password" name="password">
                    <c:if test="${password_error != null}">
                        <snap class="text-danger">${password_error}</snap>
                    </c:if>
                </div>
                <div class="mb-3">
                    <label for="p2_password" class="form-label">Confirm Password</label>
                    <input type="password" class="form-control" id="p2_password" name="confirm_password">
                    <c:if test="${password_error != null}">
                        <snap class="text-danger">${password_error}</snap>
                    </c:if>
                </div>
                <button type="submit" class="btn btn-primary">Register</button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="/fragment/js.jsp"/>
</body>
</html>
