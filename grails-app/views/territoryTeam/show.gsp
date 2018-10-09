<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'territoryTeam.label', default: 'TerritoryTeam')}" />
    <title>${this.territoryTeam?.territory?.name}详情</title>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="territoryTeam" action="index"> 区域详情</g:link>
                        </li>
                        <li class="active">
                            <span>${this.territoryTeam?.territory?.name}</span>
                        </li>
                    </ol>
                </div>

                <h2 class="font-light m-b-xs">
                    区域详情
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <g:link class="btn btn-info btn-xs" action="edit" resource="${this.territoryTeam}">
                            <i class="fa fa-edit"></i>编辑
                        </g:link>
                    </div>
                    ${this.territoryTeam?.territory?.name}详情
                </div>
                <div class="panel-body form-horizontal">
                    <div class="form-group" >
                        <label class="col-md-3 control-label">区域名称：</label>
                        <div class="col-md-4">
                            <g:textField class="form-control" disabled="" name="territory.id" value="${this.territoryTeam?.territory?.name}"></g:textField>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group" >
                        <label class="col-md-3 control-label">用户名称：</label>
                        <div class="col-md-4">
                            <g:textField class="form-control" disabled="" name="user.id" value="${this.territoryTeam?.user}"></g:textField>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group" >
                        <label class="col-md-3 control-label">开始时间：</label>
                        <div class="col-md-4">
                            <input class="form-control" disabled="disabled" name="startTime" type="text" value="<g:formatDate date="${this.territoryTeam?.startTime}" format="yyyy-MM-dd mm:HH:ss"></g:formatDate>">
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group" >
                        <label class="col-md-3 control-label">结束时间：</label>
                        <div class="col-md-4">
                            <input class="form-control" disabled="disabled" type="text" value="<g:formatDate date="${this.territoryTeam?.endTime}" format="yyyy-MM-dd mm:HH:ss"></g:formatDate>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">布局：</label>
                        <div class="col-md-3">
                            <input class="form-control" disabled="disabled" type="text" value="${this.territoryTeam?.opportunityLayout?.description}">
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>
