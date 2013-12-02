<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Charlie Knudsen Blog</title>
</head>
<body>
    <g:each in="${posts}" var="post">
        <article>
            <h3 class="post_title"><g:link controller="post" action="show" params="['slug': post.slug, 'author': post.author.githubUser]">${post.title}</g:link></h3>
            <span class="post_date">${post.datePublished}</span>
            <span class="post_author">${post.author.name}</span>
            <div class="post_body">
                ${post.body}
            </div>
        </article>
    </g:each>
</body>
</html>