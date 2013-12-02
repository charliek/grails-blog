<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title><g:layoutTitle default="Charlie Knudsen Blog"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/static/bootstrap/css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
    body {
        padding-bottom: 40px;
    }

    .navbar-fixed-top {
        position: inherit;
    }
    </style>
    <g:layoutHead/>
</head>

<body>

<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container-fluid">
            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="brand" href="/">Charlie Knudsen</a>
            <div class="nav-collapse collapse">
                <ul class="nav">
                    <li class="active"><g:link controller="admin" action="index">Admin Home</g:link></li>
                </ul>
            </div>
        </div>
    </div>
</div>

<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <g:layoutBody/>
        </div><!--/span-->
    </div><!--/row-->

    <hr>

    <footer>
        <p>&copy;2013 Charlie Knudsen</p>
    </footer>

</div><!--/.fluid-container-->

<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="/static/bootstrap/js/bootstrap.min.js"></script>

</body>
</html>
