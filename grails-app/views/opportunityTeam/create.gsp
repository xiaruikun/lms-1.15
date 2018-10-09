<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunityTeam.label', default: 'OpportunityTeam')}"/>
    <title>订单分发</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>订单分发</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                订单:${this.opportunity?.serialNumber}
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">

    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                分发订单
            </div>

            <div class="panel-body">
                <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                </g:if>
                <g:hasErrors bean="${this.opportunityTeam}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${this.opportunityTeam}" var="error">
                            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                    error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </g:hasErrors>
                <g:form action="save" class="form-horizontal">
                    <div class="form-group">
                        <label class="col-md-3  control-label">订单</label>

                        <div class="col-md-3 ">
                            <g:textField name="opportunity.id" required="" id="opportunity" value="${this.opportunity?.id}"
                                         class="hide"/>
                            <g:textField class="form-control" name="opportunity.serialNumber" required=""
                                         id="opportunity.serialNumber"
                                         value="${this.opportunity?.serialNumber}"
                                         readonly="readonly"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">用户</label>

                        <div class="col-md-3">
                            <g:select class="form-control m-b" name="user.id" id="user" from="${userList}"
                                      optionKey="id"></g:select>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">布局</label>

                        <div class="col-md-3">
                            <g:select class="form-control m-b" name="opportunityLayout.id" id="opportunityLayout" from="${com.next.OpportunityLayout.findAllByActive(true)}"
                                      optionKey="id" optionValue="description" noSelection="[null: '---请选择---']"></g:select>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <div class="col-md-3 col-lg-offset-3">
                            <g:submitButton name="create" class="btn btn-info" value="保存"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>

</div>
</body>
</html>
