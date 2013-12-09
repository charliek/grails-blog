<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title><g:layoutTitle default="Charlie Knudsen Blog"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css">
    <link href="/static/css/posts.css" rel="stylesheet" />
    <g:layoutHead/>
</head>

<body>

<header class="navbar navbar-inverse bs-docs-nav" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">Charlie Knudsen</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="active"><g:link controller="admin" action="index">Admin Home</g:link></li>
            </ul>
        </div><!--/.navbar-collapse -->
    </div>
</header>

<div class="container marketing">
    <g:layoutBody/>

    <hr class="featurette-divider" />
    <footer>
        <p class="pull-right"><a href="#">Back to top</a></p>
        <p>&copy;2013 Charlie Knudsen · <a href="#">Privacy</a> · <a href="#">Terms</a></p>
    </footer>
</div>

</body>
</html>
