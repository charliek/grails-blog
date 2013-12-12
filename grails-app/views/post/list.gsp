<%@ page import="org.markdown4j.Markdown4jProcessor" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Charlie Knudsen Blog</title>
    <link rel="stylesheet" href="/static/highlight/styles/default.css">
    <script src="/static/highlight/highlight.pack.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
    <g:each in="${posts}" var="post">
        <div class="row featurette">
            <div class="col-md-12">
                <article class="post">
                    <h2 class="featurette-heading"><g:link controller="post" action="show" params="['slug': post.slug, 'author': post.author.githubUser]">${post.title}</g:link></h2>
                    <div>
                        <div class="post_author">${post.author.name}</div>
                        <div class="post_date"><joda:format value="${post.datePublished}"/></div>
                    </div>
                    <p class="lead">${new Markdown4jProcessor().process(post.body)}</p>
                </article>
            </div>
        </div>
    </g:each>
</body>
</html>