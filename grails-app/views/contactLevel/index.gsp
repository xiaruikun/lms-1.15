<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunityLayout.label', default: 'OpportunityLayout')}"/>
    <title>客户级别</title>
</head>

<body>

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>客户级别</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                客户级别
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" controller="contactLevel" action="create"><i
                            class="fa fa-plus"></i>新增</g:link>
                </div>
                客户级别
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="name" title="名称"/>
                            <g:sortableColumn property="description" title="描述"/>
                            <g:sortableColumn property="active" title="是否启用"/>

                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${contactLevelList}">
                            <tr>
                                <td><g:link controller="contactLevel" action="show"
                                            id="${it?.id}">${it?.name}</g:link></td>
                                <td>${it?.description}</td>
                                <td>${it?.active}</td>

                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${contactLevelCount ?: 0}" params="${params}"/>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

