<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunityFlow.label', default: 'OpportunityFlow')}"/>
    <title>阶段备注</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">阶段备注</li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                订单编号: ${this.opportunityFlow?.opportunity?.serialNumber}
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                编辑
            </div>

            <div class="panel-body">
                <g:form resource="${this.opportunityFlow}" method="PUT" class="form-horizontal">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.opportunityFlow}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.opportunityFlow}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>

                    <div class="form-group">
                        <label class="col-md-2 control-label">
                            <g:if test="${this.opportunityFlow?.stage.code == '26'}">主审意见</g:if>
                            <g:elseif test="${this.opportunityFlow?.stage.code == '06'}">面谈结果</g:elseif>
                            <g:else>备注</g:else>
                        </label>

                        <div class="col-md-6">
                            <g:textArea name="comments" value="${this.opportunityFlow?.comments}" required="required"
                                        class="form-control" rows="10" cols="15"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-4">
                            <button class="btn btn-info">保存</button>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
