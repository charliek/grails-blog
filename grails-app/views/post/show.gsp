<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Charlie Knudsen Blog</title>
</head>
<body>
    <article>
        <h3 class="post_title">${post.title}</h3>
        <span class="post_date">${post.datePublished}</span>
        <span class="post_author">${post.author.name}</span>
        <div class="post_body">
            ${post.body}
        </div>
    </article>
</body>
</html>