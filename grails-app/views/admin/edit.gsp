<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="admin"/>
    <title>Blog Admin</title>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <script src="/static/epiceditor/js/epiceditor.min.js"></script>
</head>
<body>

<h3>Edit Post</h3>

<g:each in="${errors}" var="error">
    <div class="alert alert-danger">${error}</div>
</g:each>
<g:if test="${success}">
    <div class="alert alert-success">Post was saved! <g:link controller="admin" action="index">Back to post list.</g:link></div>
</g:if>

<div class="form-container">
    <g:form method="post" controller="admin" action="edit" role="form">
        <g:hiddenField name="id" value="${post.id}"/>
        <div class="form-group">
            <label for="slug">Slug</label>
            <f:input bean="post" property="slug" id="slug" class="form-control"/>
        </div>
        <div class="form-group">
            <label for="title">Title</label>
            <f:input bean="post" property="title" id="title" class="form-control"/>
        </div>
        <div class="form-group">
            <label for="body">Body</label>
            <div id="epiceditor"></div>
            <g:textArea name="body" value="${post.body}" id="bodytext" class="form-control"/>
        </div>
        <div class="form-group">
            <label for="draft">Draft</label>
            <f:input bean="post" property="draft" id="draft" class="form-control"/>
        </div>

        <button type="submit" class="btn btn-default">Submit</button>
    </g:form>
</div>

<script type="text/javascript">
    $(function(){
        var opts = {
            basePath: '//' + window.location.host,
            textarea: 'bodytext',
            clientSideStorage: false,
            theme: {
                base: '/static/epiceditor/themes/base/epiceditor.css',
                preview: '/static/epiceditor/themes/preview/github.css',
                editor: '/static/epiceditor/themes/editor/epic-dark.css'
            },
            autogrow: {
                minHeight: 300,
                maxHeight: false,
                scroll: true
            }
        };
        $('#bodytext').hide();
        var editor = new EpicEditor(opts).load();
    });
</script>

</body>
</html>
