<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
    <head>
        <title>Profile</title>
    </head>
    <body>
        <%@ include file="../fragments/header.jsp"%>
        <div class="container">
            <form:form class="form-narrow form-horizontal" action="changepassword"
                method="post" commandName="passwordDto">
                <fieldset>
                    <legend>Change Password</legend>
                        <input class="form-control" type="password" name="oldPassword" placeholder="Password">
                            <c:if test="${status eq false}">
                                <div class="error">Wrong Password!</div>
                            </c:if>
                            <c:if test="${status}">
                                <div class="msg">Password Successfully Changed!</div>
                            </c:if>
                        <br/>
                        <input type="password" class="form-control"
                            id="password" name="password" placeholder="New Password"/>
                        <input class="form-control" type="password" placeholder="Confirm Password" id="confirm_password" />
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        <form:errors path="password" cssClass="alert alert-danger" element="div" />
                        <br/>
                        <div class="text-center">
                            <button type="submit" class="btn btn-default">Save</button>
                            <a href="${pageContext.request.contextPath}/profile" class="btn btn-default">Back</a>
                        </div>
                </fieldset>
            </form:form>
            <script>
                var password = document.getElementById('password'), confirm_password = document.getElementById('confirm_password');
                function validatePassword() {
                    if (password.value != confirm_password.value) {
                        confirm_password.setCustomValidity('Passwords Don\'t Match');
                    } else {
                        confirm_password.setCustomValidity('');
                    }
                }
                password.onchange = validatePassword;
                confirm_password.onkeyup = validatePassword;
            </script>
            <%@ include file="../fragments/footer.jsp"%>
        </div>
    </body>
</html>