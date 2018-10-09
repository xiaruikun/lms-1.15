<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'territoryRole.label', default: 'TerritoryRole')}" />
    <title>区域权限</title>
    <style>
        .alert.alert-danger{
            margin-bottom: 20px;
            border: 1px solid transparent;
            border-radius: 4px;
            width: 16%;
        }
    </style>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="territoryRole" action="index">区域权限</g:link>
                        </li>
                        <li class="active">
                            <span>新增区域权限</span>
                        </li>
                    </ol>
                </div>

                <h2 class="font-light m-b-xs">
                    区域权限-新增区域权限
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    新增区域权限
                </div>
                <div class="panel-body">
                    <g:form action="save" class="form-horizontal">
                        <g:if test="${flash.message}">
                            <div class="message alert alert-info"role="status" style="margin-bottom: 20px">${flash.message}
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                                        aria-hidden="true">×</span></button>
                            </div>

                        </g:if>
                        <g:hasErrors bean="${this.territoryRole}">
                            <ul class="errors" role="alert">
                                <g:eachError bean="${this.territoryRole}" var="error">
                                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                        <g:message error="${error}" />
                                    </li>
                                </g:eachError>
                            </ul>
                        </g:hasErrors>
                        <div class="form-group">
                            <label class="col-md-3 control-label">区域名称:</label>
                            <div class="col-md-3">
                                <g:if test="${this.territoryRole?.territory != null && this.territoryRole?.territory != ''}">
                                    <g:textField name="territory.id" required id="territory" value="${this.territoryRole?.territory?.id}" class="hide" />
                                    <g:textField name="territoryName" value="${this.territoryRole?.territory?.name}" readonly="readonly" class="form-control" />
                                </g:if>
                                <g:else>
                                    <g:select name="territory.id" required id="territory" value="${this.territoryRole?.territory}" from="${com.next.Territory.list()}" optionKey="id" optionValue="name" noSelection="${['null':'请选择']}"></g:select>
                                </g:else>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">用户名</label>
                            <div class="col-md-3">
                                <g:select class="form-control" name="user.id" required id="user" value="${this.territoryRole?.user}" from="${this.userList}" optionKey="id"></g:select>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">订单阶段</label>
                            <div class="col-md-3">
                                <g:select class="form-control" name="stage.id" required id="stage" value="${this.territoryRole?.stage}" from="${com.next.OpportunityStage.list()}" optionKey="id" optionValue="name"></g:select>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">权限</label>
                            <div class="col-md-3">
                                <g:select class="form-control" name="teamRole.id" required id="teamRole" value="${this.territoryRole?.teamRole}" from="${com.next.TeamRole.list()}" optionKey="id" optionValue="name"></g:select>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-md-3 col-md-offset-3">
                                <g:submitButton class="btn btn-info" name="update" value="添加" />
                            </div>
                        </div>
                    </g:form>
                </div>
            </div>
        </div>
    </div>
</body>

</html>
