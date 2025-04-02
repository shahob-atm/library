<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home Page</title>
    <jsp:include page="/fragment/css.jsp"/>
</head>
<body>
<jsp:include page="/fragment/navbar.jsp"/>
<div class="row p-2">
    <div class="col-md-4">
        <p><b>CODE :</b> ${book.getId()}</p>
        <h3>Title : <i>${book.getTitle()}</i></h3>
        <h3>Type : ${book.getFile().getExtension()}</h3>
        <p><b>Size:</b> <fmt:formatNumber value="${book.file.size / 1024 / 1024}" type="number" maxFractionDigits="2" /> MB</p>
        <p><b>Description :</b> ${book.getDescription()}</p>
        <a href="/file/download?fileID=${book.getFile().getId()}" class="btn btn-success"> Download </a>
    </div>
    <div class="col-md-8">
        <iframe src="/storage/show?filename=${book.getFile().getGeneratedName()}" width="100%" height="800px"></iframe>
    </div>

</div>

<jsp:include page="/fragment/js.jsp"/>
</body>
</html>
