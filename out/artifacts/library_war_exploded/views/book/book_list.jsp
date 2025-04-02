<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${books}" var="book">

                <tr>
                    <th>${book.getId()}</th>
                    <th>
                        <a href="/book/detail/${book.getId()}">${book.getTitle()}</a>
                    </th>
                    <th>${book.getFile().getExtension()}</th>
                    <th>${book.getFile().getSize() / 1024/1024} MB</th>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>
</div>

<jsp:include page="/fragment/js.jsp"/>
</body>
</html>
