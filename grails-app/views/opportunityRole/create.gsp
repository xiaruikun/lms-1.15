<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunityRole.label', default: 'OpportunityRole')}"/>
    <title>订单权限</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">

            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="opportunityRole" action="index">订单权限</g:link>
                    </li>
                    <li class="active">
                        <span>新增订单权限</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                订单权限-新增订单权限
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                新增订单权限
            </div>

            <div class="panel-body">
                <g:form action="save" class="form-horizontal">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.opportunityRole}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.opportunityRole}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                    <g:message error="${error}"/>
                                </li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <div class="form-group">
                        <label class="col-md-3 control-label">订单编号</label>

                        <div class="col-md-3">
                            <g:if test="${this.opportunityRole?.opportunity != null && this.opportunityRole?.opportunity != ''}">
                                <g:textField name="opportunity.id" required id="opportunity"
                                             value="${this.opportunityRole?.opportunity?.id}" class="hide"/>
                                <g:textField name="opportunitySerialNumber"
                                             value="${this.opportunityRole?.opportunity?.serialNumber}"
                                             readonly="readonly" class="form-control"/>
                            </g:if>
                            <g:else>
                                <g:select name="opportunity.id" required id="opportunity"
                                          value="${this.opportunityRole?.opportunity}"
                                          from="${com.next.Opportunity.list()}" optionKey="id"
                                          optionValue="serialNumber" noSelection="${['null': '请选择']}"></g:select>
                            </g:else>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">用户名</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="user.id" required id="user"
                                      value="${this.opportunityRole?.user}" from="${this.userList}" optionKey="id"
                                       noSelection="${['null': '请选择']}"></g:select>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">订单阶段</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="stage.id" required id="stage"
                                      value="${this.opportunityRole?.stage}" from="${this.opportunityStages}"
                                      optionKey="id" optionValue="name" noSelection="${['null': '请选择']}"></g:select>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">权限</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="teamRole.id" required id="teamRole"
                                      value="${this.opportunityRole?.teamRole}" from="${com.next.TeamRole.list()}"
                                      optionKey="id" optionValue="name" noSelection="${['null': '请选择']}"></g:select>
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
