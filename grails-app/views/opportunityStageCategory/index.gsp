<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName"
           value="${message(code: 'opportunityStageCategory.label', default: 'OpportunityStageCategory')}"/>
    <title>订单阶段分类</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>订单阶段分类列表</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                订单阶段分类列表
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
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                所有阶段分类列表
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>阶段分类名称</th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${opportunityStageCategoryList}">
                            <tr>
                                <td>
                                    <g:link action="show" id="${it.id}">${it?.name}</g:link>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${opportunityStageCategoryCount ?: 0}" params="${params}"/>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>