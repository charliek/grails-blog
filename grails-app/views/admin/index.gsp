<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="admin"/>
    <title>Blog Admin</title>
</head>
<body>
<g:each in="${posts}" var="post">
    <table class="post_status_${(post.draft)? 'draft' : 'published'}">
        <tr>
            <td><g:link controller="admin" action="edit" id="${post.slug}">${post.title}</g:link></td>
            <td>${post.datePublished}</td>
        </tr>
    </table>
</g:each>
<br/>
<br/>

<g:link action="edit">Create New Post</g:link>
</body>
</html>