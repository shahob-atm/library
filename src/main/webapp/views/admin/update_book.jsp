<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Book</title>
    <jsp:include page="/fragment/css.jsp"/>
</head>
<body>
<jsp:include page="/fragment/navbar.jsp"/>

<div class="container mt-5">
    <div class="row">
        <div class="col-md-6 offset-3">
            <h1>Update Book</h1>
            <form action="/admin/book/update?bookId=${book.getId()}" method="post">
                <div class="mb-3">
                    <label for="title" class="form-label">title</label>
                    <input type="text" class="form-control" id="title" name="title" value="${book.getTitle()}" required>
                </div>
                <div class="mb-3">
                    <label for="description" class="form-label">Description</label>
                    <textarea class="form-control" id="description" name="description" cols="30" rows="5">
                        <c:out value="${book.getDescription()}" />
                    </textarea>
                </div>
                <button type="submit" class="btn btn-primary">Save</button>
                <a href="/book/list" class="btn btn-warning">Back</a>
            </form>
        </div>
    </div>
</div>

<jsp:include page="/fragment/js.jsp"/>
</body>
</html>
