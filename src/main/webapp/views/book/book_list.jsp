<%@ page import="uz.pdp.online.library.entity.AuthUser" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Home Page</title>
    <jsp:include page="/fragment/css.jsp"/>
</head>
<body>
<jsp:include page="/fragment/navbar.jsp"/>

<div class="row">
    <div class="col-md-10 offset-1">
        <table class="table">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Title</th>
                <th scope="col">Type</th>
                <th scope="col">Size</th>
                <% if (session.getAttribute("id") != null && session.getAttribute("role").equals(AuthUser.Role.ADMIN)) {%>
                <th scope="col">Action</th>
                <% } %>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${books}" var="book">
                <tr>
                    <td>${book.getId()}</td>
                    <td>
                        <a href="/book/detail/${book.getId()}">${book.getTitle()}</a>
                    </td>
                    <td>${book.getFile().getExtension()}</td>
                    <td>
                        <fmt:formatNumber value="${book.file.size / 1024 / 1024}" type="number" maxFractionDigits="2" /> MB
                    </td>
                    <td>
                        <% if (session.getAttribute("id") != null && session.getAttribute("role").equals(AuthUser.Role.ADMIN)) {%>
                        <a href="/admin/book/delete?bookId=${book.getId()}" class="btn btn-danger">🗑️ Delete</a>
                        <a href="/admin/book/update?bookId=${book.getId()}" class="btn btn-primary mx-2">✏️ Edit</a>
                        <% } %>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="/fragment/js.jsp"/>
</body>
</html>
