<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'document.label', default: 'Document')}"/>
    <title>帮助文档</title>
</head>

<body>

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>帮助文档</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                帮助文档
            </h2>
        </div>
    </div>
</div>
<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                帮助文档
            </div>
            <input type="hidden" value="${this.document?.document}" id="document">
            <div class="panel-body" id="panel-body">
            </div>

        </div>

    </div>
</div>

<script>
    $(function(){
        var document = $("#document").val();
        $("#panel-body").html(document);
    })

</script>
</body>

</html>
