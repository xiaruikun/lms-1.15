<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'district.label', default: 'District')}" />
    <title>城区</title>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li class="active">
                            <span>城区</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    城区
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
            <div class="row">
                <g:form method="POST" action="searchDistrict">
                    <div class="hpanel hblue">
                        <div class="panel-heading">
                            <div class="panel-tools">
                                <button class="btn btn-primary btn-xs" type="submit"><i class="fa fa-search"></i>查询</button>
                                <button class="btn btn-warning2 btn-xs" type="button" id="resetBtn"><i class="fa fa-times"></i>重置</button>
                            </div>
                            查询
                        </div>
                        <div class="panel-body">
                            <div class="col-sm-6">
                                <input type="text" class="form-control" placeholder="城区名称" name="name" id="name" value="${params?.name}">
                            </div>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" placeholder="所属城市" name="city" id="city" value="${params?.city}">
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
                        </div>
                        所有城区
                    </div>
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                    <tr>
                                        <g:sortableColumn property="name" title="${message(code: 'district.name.label', default: '城区')}" />
                                        <g:sortableColumn property="city" title="${message(code: 'district.city.name.label', default: '所属城市')}" />
                                        <g:sortableColumn property="code" title="城区编码" />
                                        <g:sortableColumn property="daysOfOtherRights" title="出它项时间（天）" />
                                        <g:sortableColumn property="city" title="${message(code: 'district.createdDate', default: '创建时间')}" />
                                    </tr>
                                </thead>
                                <tbody>
                                    <g:each in="${districtList}">
                                        <tr>
                                            <td>
                                                <g:link action="show" id="${it.id}" class="firstTd">${it.name}</g:link>
                                            </td>
                                            <td>${it.city?.name}</td>
                                            <td>${it?.code}</td>
                                            <td>
                                              ${it?.daysOfOtherRights}
                                            </td>
                                            <td><g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it.createdDate}"/></td>
                                        </tr>
                                    </g:each>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="panel-footer">
                        <div class="pagination">
                            <g:paginate total="${districtCount ?: 0}" params="${params}" />
                        </div>
                    </div>
                </div>
            </div>
    </div>
<g:javascript>
    $(function(){
        $("#resetBtn").click(function () {
            $("#name").val("");
            $("#city").val("");
        })
    });

</g:javascript>
</body>
</html>
