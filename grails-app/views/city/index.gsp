<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'city.label', default: 'City')}"/>
    <title>域管理 | 城市</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header transition animated fadeIn">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>城市</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                城市
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <g:form method="POST" action="searchCity">
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
                        <input type="text" class="form-control" placeholder="城市" name="name" id="name" value="${params?.name}">
                    </div>

                    <div class="col-sm-4">
                        <input type="text" class="form-control" placeholder="热线" name="telephone" id="telephone" value="${params?.telephone}">
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
                所有城市
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="name"
                                              title="${message(code: 'city.name.label', default: '城市')}"/>
                            <g:sortableColumn property="telephone"
                                              title="${message(code: 'city.telephone.label', default: '热线')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${cityList}">
                            <tr>
                                <td><g:link action="show" id="${it.id}" class="firstTd">${it.name}</g:link></td>
                                <td>${it.telephone}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${cityCount ?: 0}" params="${params}"/>
                </div>
            </div>
        </div>
    </div>
</div>
<g:javascript>
    $(function(){
        $("#resetBtn").click(function () {
            $("#name").val("");
            $("#telephone").val("");
        })
    });

</g:javascript>
</body>
</html>