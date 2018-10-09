<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'menu.label', default: 'Menu')}"/>
    <title>菜单详情</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="menu" action="index">菜单管理</g:link>
                    </li>
                    <li class="active">
                        <span>菜单详情</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                菜单详情
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
                    <g:link class="btn btn-info btn-xs" action="edit" resource="${this.menu}"><i
                            class="fa fa-edit"></i>编辑</g:link>
                    <g:form resource="${this.menu}" method="DELETE" style="display: inline-block">
                        <button class="deleteBtn btn btn-danger btn-xs" type="button">
                            <i class="fa fa-trash-o"></i>删除
                        </button>
                    </g:form>
                </div>
                菜单详情
            </div>

            <div class="panel-body">
                <div class="col-md-12">
                    <h4>
                        ${this.menu?.name}
                    </h4>
                </div>
            </div>

        </div>
    </div>
    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link action="create" controller="menuItem" params="[menu: this.menu.id]" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>新建</g:link>
                </div>
                菜单列表
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn width="10%" property="name" title="名称"/>
                            <g:sortableColumn width="5%" property="displayOrder" title="显示顺序"/>
                            <g:sortableColumn width="10%" property="type" title="类型"/>
                            <g:sortableColumn width="10%" property="parent" title="父级"/>
                            <g:sortableColumn width="45%" property="linkUrl" title="链接"/>
                            <g:sortableColumn width="10%" property="icon" title="图标class"/>
                            <th width="10%" class="text-center" colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.menu?.items}">
                            <tr>
                                <td>${it.name}</td>
                                <td>${it?.displayOrder}</td>
                                <td>${it?.type}</td>
                                <td>${it?.parent?.name}</td>
                                <td>${it.linkUrl}</td>
                                <td>${it.icon}</td>
                                <td>
                                    <g:link class="btn btn-primary btn-xs btn-outline"
                                            controller="menuItem" action="edit"
                                            id="${it?.id}">
                                        <i class="fa fa-edit"></i>
                                        编辑
                                    </g:link>

                                </td>
                                <td>
                                    <g:form controller="menuItem" action="delete"
                                            style="display: inline-block"
                                            id="${it.id}"
                                            method="DELETE">
                                        <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button">
                                            <i class="fa fa-trash-o"></i>
                                            删除
                                        </button>
                                    </g:form>
                                </td>

                            </tr>
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
