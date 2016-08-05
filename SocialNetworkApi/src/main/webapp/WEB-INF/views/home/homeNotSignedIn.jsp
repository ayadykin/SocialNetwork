<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Welcome to Social Network</title>
    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/core.css" />" rel="stylesheet">
</head>
<body>
<%@ include file="../fragments/header.jsp" %>
<div class="container">
    <div class="text-center">
        <h1>Social Network</h1>
        <p class="lead">
            Welcome 
            Get started quickly by signing up.
        </p>
        <p>
            <a href="signup" class="btn btn-success btn-lg">Sign up</a>
        </p>
    </div>
 
</div>
</body>
</html>