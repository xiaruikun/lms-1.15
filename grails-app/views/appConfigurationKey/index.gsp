<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'appConfigurationKey.label', default: 'AppConfigurationKey')}" />
        <title>APP邀请码</title>
    </head>
    <body  class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li class="active">
                            <span>App邀请码</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    App邀请码
                </h2>
                <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>所有邀请码</small>
            </div>
        </div>
    </div>

    <div class="content animate-panel">
        <g:if test="${flash.message}">
            <div class="alert alert-success alert-dismissible" role="alert">
                ${flash.message}
            </div>
        </g:if>

        <div class="row">
            <div class="hpanel hgreen">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <g:link action="create" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>新增</g:link>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </div>
                    全部邀请码
                </div>

                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <g:sortableColumn property="name" title="名称"/>
                                <g:sortableColumn property="删除" title="删除"/>
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${appConfigurationKeyList}">
                                <tr>
                                    <td><g:link action="show" id="${it?.id}" class="firstTd">${it?.name}</g:link></td>
                                    <td width="6%">
                                        <g:form resource="${it}" method="DELETE">
                                            <button class="deleteBtn btn btn-danger btn-xs" type="button"><i
                                                    class="fa fa-trash-o"></i> 删除</button>
                                        </g:form>
                                    </td>
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="panel-footer">
                    <div class="pagination">
                        <g:paginate total="${appConfigurationKeyCount ?: 0}" />
                    </div>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>