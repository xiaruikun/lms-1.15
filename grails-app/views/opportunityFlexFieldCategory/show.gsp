<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName"
           value="${message(code: 'opportunityFlexFieldCategory.label', default: 'OpportunityFlexFieldCategory')}"/>
    <title>弹性域模块</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>${this.opportunityFlexFieldCategory?.flexFieldCategory?.name}</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs" style="text-transform:none;">
                弹性域模块：${this.opportunityFlexFieldCategory?.flexFieldCategory?.name}
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">

    <div class="row">
        <g:if test="${flash.message}">
            <div class="message alert alert-info" role="status">${flash.message}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
            </div>
        </g:if>
        <div class="hpanel hblue">
            <div class="panel-heading">
                <div class="panel-tools">

                </div>
                弹性域模块
            </div>

            <div class="panel-body">
                <div class="text-muted font-bold m-b-xs ol-md-6">
                    <div class="col-md-12"><h4><a
                            href=>${this.opportunityFlexFieldCategory?.flexFieldCategory?.name}</a></h4></div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hyellow">
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_COO">
                        <g:link class="btn btn-info btn-xs" controller="opportunityFlexField" action="create"
                                params="[category: this.opportunityFlexFieldCategory?.id]"><i
                                class="fa fa-plus"></i>新增</g:link>
                    </sec:ifNotGranted>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                弹性域
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="name" title="名称"/>
                            <g:sortableColumn property="description" title="描述"/>
                            <g:sortableColumn property="dataType" title="数据类型"/>
                            <g:sortableColumn property="displayOrder" title="数据类型"/>
                            <g:sortableColumn property="defaultValue" title="默认值"/>
                            <g:sortableColumn property="valueConstraints" title="约束"/>
                            <sec:ifNotGranted roles="ROLE_COO" width="10%" class="text-center">
                                <g:sortableColumn property="operation" title="操作"></g:sortableColumn>
                            </sec:ifNotGranted>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunityFlexFieldCategory?.fields}">
                            <div class="col-md-4">
                                <tr>
                                    <td><g:link controller="opportunityFlexField" action="show" id="${it?.id}"
                                                class="firstTd">${it?.name}</g:link></td>
                                    <td>${it?.description}</td>
                                    <td>${it?.dataType}</td>
                                    <td>
                                        ${it?.displayOrder}
                                    </td>
                                    <td>${it?.defaultValue}</td>
                                    <td>${it?.valueConstraints}</td>
                                    <sec:ifNotGranted roles="ROLE_COO">
                                        <td width="10%" class="text-center">
                                            <g:form resource="${it}" method="DELETE">
                                                <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i
                                                        class="fa fa-trash-o"></i> 删除</button>
                                            </g:form>
                                        </td>
                                    </sec:ifNotGranted>
                                </tr>
                            </div>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
