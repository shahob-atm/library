<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Home Page</title>
  <jsp:include page="/fragment/css.jsp"/>
</head>
<body>
<jsp:include page="/fragment/navbar.jsp"/>

<div class="container mt-5">
  <div class="row">
    <div class="col-md-6 offset-3">
      <h1>Logout</h1>
      <form action="/admin/book/delete?bookId=${book.getId()}" method="post">
        <div class="alert alert-danger">
          Are you sure you want to delete the book - ${book.getTitle()}?
        </div>
        <button type="submit" class="btn btn-danger">Yes</button>
        <a href="/book/list" class="btn btn-warning">Back</a>
      </form>
    </div>
  </div>
</div>

<jsp:include page="/fragment/js.jsp"/>
</body>
</html>
