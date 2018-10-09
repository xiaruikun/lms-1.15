<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'message.label', default: 'Message')}"/>
    <title>消息管理</title>
</head>

<body class="fixed-navbar fixed-sidebar">

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>消息管理</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                消息管理
            </h2>
            <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span> 所有消息</small>
        </div>
    </div>
</div>

<div class="content animate-panel">

    <g:if test="${flash.message}">
        <div class="alert alert-success alert-dismissible" message="alert">
            ${flash.message}
        </div>
    </g:if>
    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    %{-- <g:link action="create" class="btn btn-info btn-xs"><i class="fa fa-edit"></i>新建</g:link> --}%
                </div>
                全部消息记录
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="authority" title="${message(code: 'message.authority.label', default: '手机号')}"/>
                            <g:sortableColumn property="authority" title="${message(code: 'message.authority.label', default: '消息状态')}"/>
                            <g:sortableColumn property="authority" title="${message(code: 'message.authority.label', default: '消息内容')}"/>
                            <g:sortableColumn property="authority" title="${message(code: 'message.authority.label', default: '发送平台')}"/>
                            <g:sortableColumn property="authority" title="${message(code: 'message.authority.label', default: '发送时间')}"/>
                            <g:sortableColumn property="authority" title="${message(code: 'message.authority.label', default: '类型')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${messageList}">
                            <tr>
                            <td class="cellphoneFormat">${it.cellphone}</td>
                            <td>${it.status}</td>
                            <td>${it.text}</td>
                            <td>${it.platform}</td>
                            <td><g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it.createdDate}"/></td>
                            <td>${it.type}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${messageCount ?: 0}" params="${params}"/>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>