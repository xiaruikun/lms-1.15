<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'account.label', default: 'Account')}"/>
    <title>区域管理 | 机构</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>机构</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                机构
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">

        <g:form method="POST" action="searchAccount">

            <div class="hpanel hblue">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <button class="btn btn-primary btn-xs" type="submit"><i class="fa fa-search"></i>查询</button>
                        <button class="btn btn-warning2 btn-xs" type="button" id="resetBtn"><i class="fa fa-times"></i>重置</button>
                    </div>
                    查询
                </div>

                <div class="panel-body">
                    <div class="col-md-3">
                        <input type="text" class="form-control" placeholder="机构名称" name="name" id="name" value="${account?.name}">
                    </div>
                    <div class="col-sm-3">
                        <g:select class="weui_select form-control" noSelection="${['': 'All']}" name="type" id="type" optionKey="name" optionValue="name" from="${com.next.AccountType.list()}" value="${this.account?.type?.name}"/>
                    </div>
                </div>
            </div>
        </g:form>
        <g:if test="${flash.message}">
            <div class="row">
                <div class="hpanel">
                    <div class="panel-body">
                        <div class="alert alert-info" role="alert">${flash.message}</div>
                    </div>
                </div>
            </div>
        </g:if>

    </div>

    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link action="create" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>新建</g:link>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                所有机构
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="name"
                                              title="${message(code: 'account.name.label', default: '机构名称')}"/>
                            <g:sortableColumn property="externalId"
                                              title="${message(code: 'account.externalId.label', default: '门店编号')}"/>
                            <g:sortableColumn property="type"
                                              title="${message(code: 'account.type.label', default: '合作方式')}"/>
                            <g:sortableColumn property="parent"
                                              title="${message(code: 'account.parent.label', default: '上级机构名称')}"/>

                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${accountList}">
                            <tr>
                                <td><g:link action="show" id="${it.id}" class="firstTd">${it.name}</g:link></td>
                                <td>${it.externalId}</td>
                                <td>${it.type?.name}</td>
                                <td>${it.parent?.name}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${accountCount ?: 0}" params="${params}"/>
                </div>
            </div>
        </div>

    </div>
</div>
<g:javascript>
    $(function(){
        $("#resetBtn").click(function () {
            $("#name").val("");
            $("#type").val(null);
        })
    });

</g:javascript>
</body>
</html>