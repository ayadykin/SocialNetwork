<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>Profile</title>
    </head>
    <body>
        <%@ include file="../fragments/header.jsp"%>
        <div class="container">
            <div class="text-center">
                <h2>User Profile</h2>
                <table class="table">
                    <thead>
                        <tr>
                            <th>User id</th>
                            <th>First name</th>
                            <th>Last name</th>
                            <th>City</th>
                            <th>Country</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tr>
                        <td>
                            <c:out value="${user.userId}" />
                        </td>
                        <td>
                            <c:out value="${user.firstName}" />
                        </td>
                        <td>
                            <c:out value="${user.lastName}" />
                        </td>
                        <td>
                            <c:out value="${user.city}" />
                        </td>
                        <td>
                            <c:out value="${user.country}" />
                        </td>
                        <c:choose>
                            <c:when test="${user.friendStatus != null}">
                                <td>${user.friendStatus}</td>
                            </c:when>
                            <c:when test="${user.yourProfile}">
                                <td><a href="${pageContext.request.contextPath}/profile">
                                    Your Profile
                                    </a>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td id="friendStatus_${user.userId}">
                                    <button type="submit" class="btn btn-default"
                                        onclick="inviteFriend(${user.userId})">Invite
                                    ${user.firstName}</button>
                                </td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </table>
                <button class="btn btn-default" onclick="history.back()">Back</button>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <%@ include file="../fragments/footer.jsp"%>
        </div>
    </body>
</html>