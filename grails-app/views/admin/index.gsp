<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="admin"/>
    <title>Blog Admin</title>
</head>
<body>

    <table class="table table-striped table-bordered table-hover">
        <thead>
            <tr>
                <th>Title</th>
                <th>Date Published</th>
            </tr>
        </thead>
        <tbody>
            <g:each in="${posts}" var="post">
                <tr>
                    <td><g:link controller="admin" action="edit" id="${post.slug}">${post.title}</g:link></td>
                    <td class="post_status_${(post.draft)? 'draft' : 'published'}">${post.datePublished}</td>
                </tr>
            </g:each>
        </tbody>
    </table>

<br/>
<br/>

<g:link action="edit">Create New Post</g:link>
</body>
</html>