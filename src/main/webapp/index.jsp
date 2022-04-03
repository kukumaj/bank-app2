<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<ul>
    <c:forEach var = "i" begin = "1" end = "5">
    Item <c:out value = "${i}"/><p>
    </c:forEach>
</ul>
<a href="hello-servlet">Hello Servlet</a>
</body>
</html>