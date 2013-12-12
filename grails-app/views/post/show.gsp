<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Charlie Knudsen Blog</title>
</head>
<body>
    <div class="row featurette">
        <div class="col-md-12">
            <h2 class="featurette-heading">${post.title}</h2>
            <div>
                <div class="post_author">${post.author.name}</div>
                <div class="post_date"><joda:format value="${post.datePublished}"/></div>
            </div>
            <p class="lead">${post.body}</p>
        </div>
    </div>
</body>
</html>