<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="admin"/>
    <title>Blog Admin</title>
</head>
<body>

<h3>Edit Post</h3>

<g:each in="${errors}" var="error">
    <div class="error-msg">${error}</div>
</g:each>

<g:form method="post" controller="admin" action="edit">
    <g:hiddenField name="id" value="${post.id}"/>
    <f:field bean="post" property="slug"/>
    <f:field bean="post" property="title"/>
    <f:field bean="post" property="body"/>
    <f:field bean="post" property="draft"/>
    <br />
    <g:submitButton name="submit"/>
</g:form>

</body>
</html>
