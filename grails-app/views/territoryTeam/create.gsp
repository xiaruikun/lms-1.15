<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'territoryTeam.label', default: 'TerritoryTeam')}" />
    <title>新增区域团队</title>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="territoryTeam" action="index">区域-团队</g:link>
                        </li>
                        <li class="active">
                            <span>新增区域团队</span>
                        </li>
                    </ol>
                </div>

                <h2 class="font-light m-b-xs">
                    区域团队-新增区域团队
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    新增区域团队
                </div>
                <div class="panel-body">
                    <g:form action="save" class="form-horizontal">
                        <g:if test="${flash.message}">
                            <div class="message" role="status">${flash.message}</div>
                        </g:if>
                        <g:hasErrors bean="${this.territoryTeam}">
                            <ul class="errors" role="alert">
                                <g:eachError bean="${this.territoryTeam}" var="error">
                                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                        <g:message error="${error}" />
                                    </li>
                                </g:eachError>
                            </ul>
                        </g:hasErrors>
                        <div class="form-group">
                            <label class="col-md-3 control-label">区域名称:</label>
                            <div class="col-md-3">
                                <g:if test="${this.territoryTeam?.territory != null && this.territoryTeam?.territory != ''}">
                                    <g:textField name="territory.id" required id="territory" value="${this.territoryTeam?.territory?.id}" class="hide" />
                                    <g:textField name="territoryName" value="${this.territoryTeam?.territory?.name}" readonly="readonly" class="form-control" />
                                </g:if>
                                <g:else>
                                    <g:select name="territory.id" required="" id="territory" value="${this.territoryTeam?.territory}" from="${com.next.Territory.list()}" optionKey="id" optionValue="name" noSelection="${['null':'请选择']}"></g:select>
                                </g:else>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">用户名</label>
                            <div class="col-md-3">

                                <g:select class="form-control" name="user.id" id="user" value="${this.territoryTeam?.user}" from="${this.userList}" optionKey="id"></g:select>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">开始时间</label>
                            <div class="col-md-3 datePicker">
                                <g:datePicker name="startTime" value="${new Date()}" precision="day" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">结束时间</label>
                            <div class="col-md-3 datePicker">
                                <g:datePicker name="endTime" value="${new Date()}" precision="day" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">布局</label>
                            <div class="col-md-3">
                                <g:select class="form-control" name="opportunityLayout.id" value="${this.territoryTeam?.opportunityLayout?.id}" required="required" id="opportunityLayout" from="${com.next.OpportunityLayout.findAllByActive(true)}" optionKey="id" optionValue="description" noSelection="${['null':'请选择']}"></g:select>

                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-md-3 col-md-offset-3">
                                <g:submitButton class="btn btn-info" name="update" value="保存" />
                            </div>
                        </div>
                    </g:form>
                </div>
            </div>
        </div>
    </div>
</body>

</html>
