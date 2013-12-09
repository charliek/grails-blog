<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Charlie Knudsen Blog</title>
</head>
<body>
    <g:each in="${posts}" var="post">
        <div class="row featurette">
            <div class="col-md-12">
                <h2 class="featurette-heading"><g:link controller="post" action="show" params="['slug': post.slug, 'author': post.author.githubUser]">${post.title}</g:link></h2>
                <div>
                    <span class="post_author">${post.author.name}</span>
                    <span class="post_date">${post.datePublished}</span>
                </div>
                <p class="lead">${post.body}</p>
            </div>
        </div>
    </g:each>
</body>
</html>