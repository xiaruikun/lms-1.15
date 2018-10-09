<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'opportunityStage.label', default: 'OpportunityStage')}" />
    <title>订单阶段：${opportunityStage.name}</title>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">

                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="causeOfFailure" action="index">订单阶段</g:link>
                        </li>
                        <li class="active">
                            <span>${this.opportunityStage?.name}</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    订单阶段: ${this.opportunityStage?.name}
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    订单阶段编辑
                </div>
                <div class="panel-body">
                    <g:form resource="${this.opportunityStage}" method="PUT" class="form-horizontal">
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
                                <g:textField name="type" value="${this.opportunityStage?.type}" class="form-control" disabled="" readonly="readonly" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group ">
                            <label class="col-md-3 control-label">是否启用</label>
                            <div class="col-md-3 checkbox-inline">
                                <g:radioGroup class="checkbox-inline" name="active" value="${this.opportunityStage?.active}" labels="['true','false']" values="[true,false]">
                                    ${it.radio} ${it.label}
                                </g:radioGroup>
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
