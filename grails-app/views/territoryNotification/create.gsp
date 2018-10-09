<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'territoryNotification.label', default: 'TerritoryNotification')}" />
    <title>区域消息</title>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="territoryNotification" action="index">区域消息</g:link>
                        </li>
                        <li class="active">
                            <span>新增区域消息</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    新增区域消息
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    新增区域消息
                </div>
                <div class="panel-body">
                    <g:form action="save" class="form-horizontal">
                        <g:if test="${flash.message}">
                            <div class="message" role="status">${flash.message}</div>
                        </g:if>
                        <g:hasErrors bean="${this.territoryNotification}">
                            <ul class="errors" role="alert">
                                <g:eachError bean="${this.territoryNotification}" var="error">
                                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                        <g:message error="${error}" />
                                    </li>
                                </g:eachError>
                            </ul>
                        </g:hasErrors>
                        <div class="form-group">
                            <label class="col-md-3 control-label">区域名称:</label>
                            <div class="col-md-3">
                                <g:if test="${this.territoryNotification?.territory != null && this.territoryNotification?.territory != ''}">
                                    <g:textField name="territory.id" required="required" id="territory" value="${this.territoryNotification?.territory?.id}" class="hide" />
                                    <g:textField name="territoryName" value="${this.territoryNotification?.territory?.name}" readonly="readonly" class="form-control" />
                                </g:if>
                                <g:else>
                                    <g:select name="territory.id" required="required" id="territory" value="${this.territoryNotification?.territory}" from="${com.next.Territory.list()}" optionKey="id" optionValue="name" noSelection="${['null':'请选择']}"></g:select>
                                </g:else>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">用户名</label>
                            <div class="col-md-3">
                                <g:select class="form-control" name="user.id" required="required" id="user" value="${this.territoryNotification?.user}" from="${this.userList}" optionKey="id"></g:select>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">订单阶段</label>
                            <div class="col-md-3">
                                <g:select class="form-control" name="stage.id" required="required" id="stage" value="${this.territoryNotification?.stage}" from="${com.next.OpportunityStage.list()}" optionKey="id" optionValue="name"></g:select>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">消息模板</label>
                            <div class="col-md-3">
                                <g:select class="form-control" name="messageTemplate.id" required="required" id="messageTemplate" value="${this.territoryNotification?.messageTemplate}" from="${com.next.MessageTemplate.list()}" optionKey="id"></g:select>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>

                        <div class="form-group">
                            <label class="col-md-3 control-label">推送主管</label>

                            <div class="col-md-3">
                                <div class="radio radio-info radio-inline">
                                    <input type="radio" name="toManager"  value="true">
                                    <label for="radio1">true</label>
                                </div>

                                <div class="radio radio-info radio-inline">
                                    <input type="radio" name="toManager"  value="false"  checked="">
                                    <label for="radio2">false</label>
                                </div>
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
