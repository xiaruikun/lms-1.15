<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'assetPool.label', default: 'assetPool')}"/>
    <title>资产池列表</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>资产池列表</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                资产池列表
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <g:form method="POST" action="searchAssetPool">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <button class="btn btn-primary btn-xs" type="submit"><i class="fa fa-search"></i>查询</button>
                        <button class="btn btn-warning2 btn-xs" type="button" id="resetBtn"><i
                                class="fa fa-times"></i>重置</button>
                    </div>
                    查询
                </div>

                <div class="panel-body">
                    <div class="col-sm-3">
                        <input type="text" class="form-control" placeholder="名称" name="name" id="name"
                               value="${params?.name}">
                    </div>
                </div>
            </div>
        </g:form>
    </div>

    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                  <sec:ifNotGranted roles="ROLE_INVESTOR">
                      <g:link action="create" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>新建</g:link>
                  </sec:ifNotGranted>
                </div>
                资产池列表
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="name" title="名称"/>
                            %{--<th width="15%" class="text-center" colspan="2">操作</th>--}%
                        </thead>
                        <tbody>
                        <g:each in="${assetPoolList}">
                            <tr>
                                <td>
                                    <g:link controller="assetPool" action="show" id="${it?.id}">${it?.name}
                                    </g:link></td>
                               %{-- <td class="text-center">
                                    <g:link class="btn btn-primary btn-xs btn-outline"
                                            controller="assetPool" action="edit"
                                            id="${it?.id}">
                                        <i class="fa fa-edit"></i>
                                        编辑
                                    </g:link>

                                </td>
                                <td class="text-center">
                                    <g:form controller="assetPool" action="delete"
                                            style="display: inline-block"
                                            id="${it.id}"
                                            method="DELETE">
                                        <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button">
                                            <i class="fa fa-trash-o"></i>
                                            删除
                                        </button>
                                    </g:form>
                                </td>--}%
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${assetPoolCount ?: 0}"/>
                </div>
            </div>
        </div>
    </div>

</div>
<g:javascript>
    $(function () {
        $("#resetBtn").click(function () {
            $("#name").val("");
            $("#type").val("");
        })
    });

</g:javascript>
</body>
</html>
