<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'territory.label', default: 'Territory')}"/>
    <title>新增区域</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="territory" action="index">区域管理</g:link>
                    </li>
                    <li class="active">
                        <span>新增区域</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                区域管理-新增区域
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                新增区域<span class="text-danger">（备注：新增区域注意绑定工作流）</span>
            </div>

            <div class="panel-body">
                <g:form action="save" class="form-horizontal">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.territory}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.territory}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                    <g:message error="${error}"/>
                                </li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <div class="form-group">
                        <label class="col-md-3 control-label">区域名称:</label>

                        <div class="col-md-3">
                            <g:textField name="name" value="${this.territory?.name}" class="form-control"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">上级区域</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="parent.id" required="required" id="parent"
                                      value="${this.territory?.parent}" from="${com.next.Territory.list()}"
                                      optionKey="id" optionValue="name" noSelection="${['null': '请选择']}"></g:select>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    %{--<div class="form-group">
                        <label class="col-md-3 control-label">流动性评分模板</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="liquidityRiskReportTemplate.id" required="required" id="liquidityRiskReportTemplate"
                                      value="${this.territory?.liquidityRiskReportTemplate}" from="${com.next.LiquidityRiskReportTemplate.list()}"
                                      optionKey="id" optionValue="name" noSelection="${['null': '请选择']}"></g:select>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>--}%

                    <div class="form-group">
                        <label class="col-md-3 control-label">继承团队</label>

                        <div class="col-md-3">
                            <div class="radio radio-info radio-inline">
                                <input type="radio" name="inheritTeam"  value="true" checked="">
                                <label for="radio1">true</label>
                            </div>

                            <div class="radio radio-info radio-inline">
                                <input type="radio" name="inheritTeam"  value="false">
                                <label for="radio2">false</label>
                            </div>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">继承权限</label>

                        <div class="col-md-3">
                            <div class="radio radio-info radio-inline">
                                <input type="radio" name="inheritRole"  value="true" checked="">
                                <label for="radio1">true</label>
                            </div>

                            <div class="radio radio-info radio-inline">
                                <input type="radio" name="inheritRole"  value="false">
                                <label for="radio2">false</label>
                            </div>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">继承消息</label>

                        <div class="col-md-3">
                            <div class="radio radio-info radio-inline">
                                <input type="radio" name="inheritNotification" value="true" checked="">
                                <label for="radio1">true</label>
                            </div>

                            <div class="radio radio-info radio-inline">
                                <input type="radio" name="inheritNotification" value="false">
                                <label for="radio2">false</label>
                            </div>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">继承工作流</label>

                        <div class="col-md-3">
                           <div class="radio radio-info radio-inline">
                                <input type="radio" name="inheritFlow"  value="true" checked="">
                                <label for="radio1">true</label>
                            </div>

                            <div class="radio radio-info radio-inline">
                                <input type="radio" name="inheritFlow" value="false">
                                <label for="radio2">false</label>
                            </div>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
                            <g:submitButton class="btn btn-info" name="update" value="保存"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
</body>

</html>
