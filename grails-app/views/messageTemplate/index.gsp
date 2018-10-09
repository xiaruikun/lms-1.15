<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'messageTemplate.label', default: 'MessageTemplate')}" />
    <title>推送信息模板</title>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li class="active">
                            <span>消息推送模板</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    消息推送模板
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hgreen">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <g:link action="create" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>新建</g:link>
                    </div>
                    所有消息模板
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                                <tr>
                                    <g:sortableColumn property="name" title="${message(code: 'messageTemplate.name.label', default: '消息名称')}" />
                                    <g:sortableColumn property="text" title="${message(code: 'messageTemplate.name.label', default: '消息内容')}" />

                                </tr>
                            </thead>
                            <tbody>
                                <g:each in="${messageTemplateList}">
                                    <tr>
                                        <td><g:link action="show" id="${it.id}">${it?.name}</g:link></td>
                                        <td>
                                            <g:link action="show" id="${it.id}" class="firstTd">${it?.text}</g:link>
                                        </td>

                                    </tr>
                                </g:each>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="panel-footer">
                    <div class="pagination">
                        <g:paginate total="${messageTemplateCount ?: 0}" params="${params}" />
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>
