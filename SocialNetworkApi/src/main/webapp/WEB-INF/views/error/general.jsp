<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Error page</title>  
</head>
<body>
<%@ include file="../fragments/header.jsp" %>
<div class="container">
    Exception message: <c:out value="${requestScope['javax.servlet.error.message']}" /></br>
    Request URI: <c:out value="${requestScope['javax.servlet.error.request_uri']}" /></br>
</div>
</body>
</html>