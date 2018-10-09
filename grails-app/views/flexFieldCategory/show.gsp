<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'flexFieldCategory.label', default: 'FlexFieldCategory')}"/>
    <title>弹性域模块：${this.flexFieldCategory?.name}</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li><g:link controller="flexFieldCategory" action="index">弹性域模块</g:link></li>
                    <li class="active">
                        <span>${this.flexFieldCategory?.name}</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs" style="text-transform:none;">
                弹性域模块：${this.flexFieldCategory?.name}
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">

    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                <div class="panel-tools">
                  <sec:ifNotGranted roles="ROLE_COO">
                    <g:link class="btn btn-info btn-xs" action="edit" resource="${this.flexFieldCategory}"
                            id="${it?.flexFieldCategory?.id}"><i class="fa fa-edit"></i>编辑</g:link>
                          </sec:ifNotGranted>
                </div>
                弹性域模块
            </div>

            <div class="panel-body">
                <div class="text-muted font-bold m-b-xs ol-md-6">
                    <div class="col-md-12"><h4><a href=>${this.flexFieldCategory.name}</a></h4></div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="hpanel hyellow">
            <div class="panel-heading">
                <div class="panel-tools">
                  <sec:ifNotGranted roles="ROLE_COO">
                    <g:link class="btn btn-xs btn-info" controller="flexField" action="create"
                            id="${this.flexFieldCategory?.id}"><i class="fa fa-plus"></i>新增</g:link>
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
                            <g:sortableColumn property="displayOrder" title="次序"/>
                            <g:sortableColumn property="dataType" title="数据类型"/>
                            <g:sortableColumn property="defaultValue" title="默认值"/>
                            <g:sortableColumn property="valueConstraints" title="约束"/>
                            <g:sortableColumn property="active" title="有效性"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.flexFieldCategory.fields}">
                            <div class="col-md-4">
                                <tr>
                                    <td><g:link action="show" controller="flexField" id="${it?.id}"
                                                class="firstTd">${it?.name}</g:link></td>
                                    <td>${it?.description}</td>
                                    <td>${it?.displayOrder}</td>
                                    <td>${it?.dataType}</td>
                                    <td>${it?.defaultValue}</td>
                                    <td>${it?.valueConstraints}</td>
                                    <td>${it?.active}</td>
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
