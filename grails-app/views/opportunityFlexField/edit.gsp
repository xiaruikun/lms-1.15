<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'opportunityFlexField.label', default: 'OpportunityFlexField')}" />
        <title>编辑弹性域模块：${this.opportunityFlexField?.name}</title>
    </head>
    <body>
        <div class="small-header">
            <div class="hpanel">
                <div class="panel-body">
                    <div id="hbreadcrumb" class="pull-right">
                        <ol class="hbreadcrumb breadcrumb">
                            <li>中佳信LMS</li>
                            <li><g:link controller="creditReportProvider" action="index">推送模板</g:link>弹性域</li>
                            <li class="active">弹性域编辑</li>
                        </ol>
                    </div>
                    <h2 class="font-light m-b-xs">
                        编辑弹性域信息
                    </h2>
                </div>
            </div>
        </div>
        <div class="content animate-panel">
            <div class="row">
                <div class="hpanel hyellow">
                    <div class="panel-heading">
                        <div class="panel-tools">
                        </div>
                        弹性域信息
                    </div>

                    <div class="panel-body">
                        <div class="form-horizontal">
                            <g:if test="${flash.message}">
                                <div class="message" role="status">${flash.message}</div>
                            </g:if>
                            <g:hasErrors bean="${this.opportunityFlexField}">
                                <ul class="errors" role="alert">
                                    <g:eachError bean="${this.opportunityFlexField}" var="error">
                                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                                    </g:eachError>
                                </ul>
                            </g:hasErrors>
                            <g:form resource="${this.opportunityFlexField}" method="PUT">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">订单编号</label>
                                    <div class="col-md-3">
                                        <g:textField class="form-control" name="serialNumber" readOnly="true" value="${this.opportunityFlexField?.category?.opportunity?.serialNumber}"></g:textField>
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">名称</label>
                                    <div class="col-md-3">
                                        <g:textField class="form-control" name="name" readOnly="true" value="${this.opportunityFlexField?.name}"></g:textField>
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">描述</label>
                                    <div class="col-md-3">
                                        <g:textField class="form-control" name="description" readOnly="true" value="${this.opportunityFlexField?.description}"></g:textField>
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">数据类型</label>
                                    <div class="col-md-3">
                                        <g:textField class="form-control" name="dataType" readOnly="true" value="${this.opportunityFlexField?.dataType}"></g:textField>
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">默认值</label>
                                    <div class="col-md-3">
                                        <g:textField class="form-control" name="defaultValue" readOnly="true" value="${this.opportunityFlexField?.defaultValue}"></g:textField>
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">约束</label>

                                    <div class="col-md-3">
                                        <g:textField class="form-control" name="valueConstraints" readOnly="true" value="${this.opportunityFlexField?.valueConstraints}"></g:textField>
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">值</label>

                                    <div class="col-md-3">
                                        <g:if test="${values}">
                                            <g:select class="form-control" name="value" value="${this.opportunityFlexField?.value}" optionKey="value" optionValue="value" from="${values}" noSelection="['':'-请选择-']"></g:select>
                                        </g:if>
                                        <g:else>
                                            <g:textField class="form-control" name="value" value="${this.opportunityFlexField?.value}"></g:textField>
                                        </g:else>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-3 col-lg-offset-3">
                                        <g:submitButton class="btn btn-info col-md-3" name="update" value="确定"/>
                                    </div>
                                </div>
                            </g:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
