<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'component.label', default: 'Component')}"/>
    <title>组件列表</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>组件列表</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                组件
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
        <g:form method="POST" action="searchComponent">
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

                    <div class="col-md-3">
                        <g:select class="form-control" name="type" id="type"
                                  from="${com.next.ComponentType.list()}"
                                  valueMessagePrefix="stage" optionKey="id" optionValue="name" value="${params?.type}"
                                  noSelection="${['null': '请选择类型']}"/>
                    </div>
                </div>
            </div>
        </g:form>
    </div>

    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" controller="component" action="create"><i
                            class="fa fa-plus"></i>新增</g:link>
                </div>
                组件
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="name"
                                              title="${message(code: 'component.name.label', default: '名称')}"/>
                            <g:sortableColumn property="type"
                                              title="${message(code: 'component.type.name.label', default: '类型')}"/>
                            <g:sortableColumn property="active"
                                              title="${message(code: 'component.active.label', default: '是否启用')}"/>
                            <g:sortableColumn property="createdDate"
                                              title="${message(code: 'component.createdDate.label', default: '创建时间')}"/>
                            <g:sortableColumn property="modifiedDate"
                                              title="${message(code: 'component.modifiedDate.label', default: '修改时间')}"/>
                            <g:sortableColumn property="createBy"
                                              title="${message(code: 'component.createBy.label', default: '创建人')}"/>
                            <g:sortableColumn property="modifyBy"
                                              title="${message(code: 'component.modifyBy.label', default: '修改人')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${componentList}">
                            <tr>
                                <td><g:link controller="component" action="show"
                                            id="${it?.id}">${it?.name}</g:link></td>
                                <td>${it?.type?.name}</td>
                                <td>
                                    ${it?.active}
                                </td>
                                <td>
                                    <g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it?.createdDate}"/>
                                </td>
                                <td>
                                    <g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it?.modifiedDate}"/>
                                </td>
                                <td>
                                    ${it?.createBy}
                                </td>
                                <td>
                                    ${it?.modifyBy}
                                </td>

                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${componentCount ?: 0}" params="${params}"/>
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
