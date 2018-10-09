<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'opportunityStage.label', default: 'OpportunityStage')}" />
    <title>新增订单阶段</title>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="opportunityStage" action="index">订单阶段</g:link>
                        </li>
                        <li class="active">
                            <span>新增订单阶段</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    订单阶段
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    新增订单阶段
                </div>
                <div class="panel-body">
                    <g:form action="save" class="form-horizontal">
                        <g:if test="${flash.message}">
                            <div class="message" role="status">${flash.message}</div>
                        </g:if>
                        <g:hasErrors bean="${this.opportunityStage}">
                            <ul class="errors" role="alert">
                                <g:eachError bean="${this.opportunityStage}" var="error">
                                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                        <g:message error="${error}" />
                                    </li>
                                </g:eachError>
                            </ul>
                        </g:hasErrors>
                        <div class="form-group">
                            <label class="col-md-3 control-label">编号</label>
                            <div class="col-md-3">
                                <g:textField name="code" value="${this.opportunityStage?.code}" class="form-control" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">名称</label>
                            <div class="col-md-3">
                                <g:textField name="name" value="${this.opportunityStage?.name}" class="form-control" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">描述</label>
                            <div class="col-md-3">
                                <g:textField name="description" value="${this.opportunityStage?.description}" class="form-control" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">类型</label>
                            <div class="col-md-3">
                                  <g:select class="form-control" name="type" required id="opportunityType" value="${this.opportunityStage?.type}" from="${com.next.OpportunityType.list()}" optionKey="id" optionValue="name" noSelection="${['null':'请选择']}"></g:select>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">是否启用</label>

                            <div class="col-md-3">
                                <div class="radio radio-info radio-inline">
                                    <input type="radio" name="active"  value="true" checked="">
                                    <label for="radio1">true</label>
                                </div>

                                <div class="radio radio-info radio-inline">
                                    <input type="radio" name="active"  value="false">
                                    <label for="radio2">false</label>
                                </div>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">阶段分类</label>
                            <div class="col-md-3">
                                <g:select class="form-control" optionKey="id" optionValue="name"
                                          name="category" id = "category"
                                          value="${this.opportunityStage?.category?.id}"
                                          from="${com.next.OpportunityStageCategory.list()}"/>

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
