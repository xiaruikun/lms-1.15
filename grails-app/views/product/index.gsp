<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}"/>
    <title>产品管理</title>
</head>
<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>产品</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                产品
            </h2>
        </div>
    </div>
</div>
<div class="content animate-panel">
    <div class="row">
        <g:form method="POST" action="searchProduct">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <button class="btn btn-primary btn-xs" type="submit"><i class="fa fa-search"></i>查询</button>
                        <button class="btn btn-warning2 btn-xs" type="button" id="resetBtn"><i class="fa fa-times"></i>重置</button>
                    </div>
                    查询
                </div>
                <div class="panel-body">
                    <div class="col-sm-4">
                        <input type="text" class="form-control" placeholder="产品名称" name="name" id="name" value="${params?.name}">
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
                  <sec:ifNotGranted roles="ROLE_COO">
                    <g:link action="create" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>新建</g:link>
                  </sec:ifNotGranted>
                </div>
                所有产品
            </div>
            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="name" title="产品名称"/>
                            <g:sortableColumn property="active" title="有效性"/>
                            <g:sortableColumn property="createdDate" title="创建时间"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${productList}">
                            <tr>
                                <td><g:link action="show" id="${it?.id}" class="firstTd">${it?.name}</g:link></td>
                                <td>${it?.active}</td>
                                <td><g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it?.createdDate}"/></td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${productCount ?: 0}" params="${params}"/>
                </div>
            </div>
        </div>
    </div>
</div>
<g:javascript>
    $(function(){
        $("#resetBtn").click(function () {
            $("#name").val("");
        })
    });

</g:javascript>
</body>
</html>
