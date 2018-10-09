<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'address.label', default: 'Address')}" />
    <title>地址</title>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li class="active">
                            <span>地址</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    地址
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
            <div class="row">
                <g:form method="POST" action="searchAddress">
                    <div class="hpanel hblue">
                        <div class="panel-heading">
                            <div class="panel-tools">
                                <button class="btn btn-primary btn-xs" type="submit"><i class="fa fa-search"></i>查询</button>
                                <button class="btn btn-warning2 btn-xs" type="button" id="resetBtn"><i class="fa fa-times"></i>重置</button>
                            </div>
                            查询
                        </div>
                        <div class="panel-body">
                            <div class="col-sm-3">
                                <input type="text" class="form-control" placeholder="单位名称" name="name" id="name" value="${params?.name}">
                            </div>
                            <div class="col-sm-3">
                                <input type="text" class="form-control" placeholder="单位地址" name="address" id="address" value="${params?.address}">
                            </div>
                            <div class="col-sm-3">
                                <input type="text" class="form-control" placeholder="单位类型" name="type" id="type" value="${params?.type}">
                            </div>
                            <div class="col-sm-3">
                                <input type="text" class="form-control" placeholder="所属城区" name="district" id="district" value="${params?.district}">
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
                            <g:link action="create" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>新增</g:link>
                        </div>
                        所有地址
                    </div>
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                    <tr>
                                        <g:sortableColumn property="name" title="${message(code: 'address.name.label', default: '单位名称')}" />
                                        <g:sortableColumn property="address" title="${message(code: 'address.address.label', default: '单位地址')}" />
                                        <g:sortableColumn property="type" title="${message(code: 'address.type.label', default: '单位类型')}" />
                                        <g:sortableColumn property="district" title="${message(code: 'address.district.label', default: '所属城区')}" />
                                        <g:sortableColumn property="trainStations" title="${message(code: 'address.trainStations.label', default: '火车站')}" />
                                        <g:sortableColumn property="busStations" title="${message(code: 'address.busStations.label', default: '公交站')}" />
                                        <g:sortableColumn property="tellphone" title="${message(code: 'address.tellphone.label', default: '热线电话')}" />
                                        <g:sortableColumn property="openingHours" title="${message(code: 'address.openingHours.label', default: '工作时间')}" />
                                    </tr>
                                </thead>
                                <tbody>
                                    <g:each in="${addressList}">
                                        <tr>
                                            <td>
                                                <g:link action="show" id="${it.id}" class="firstTd">${it.name}</g:link>
                                            </td>
                                            <td>${it.address}</td>
                                            <td>${it.type?.name}</td>
                                            <td>${it.district?.name}</td>
                                            <td>${it.trainStations}</td>
                                            <td>${it.busStations}</td>
                                            <td>${it.tellphone}</td>
                                            <td>${it.openingHours}</td>
                                        </tr>
                                    </g:each>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="panel-footer">
                        <div class="pagination">
                            <g:paginate total="${addressCount ?: 0}" params="${params}" />
                        </div>
                    </div>
                </div>
            </div>
    </div>
<g:javascript>
    $(function(){
        $("#resetBtn").click(function () {
            $("#name").val("");
            $("#address").val("");
            $("#type").val("");
            $("#district").val("");
        });
    });

</g:javascript>
</body>
</html>
