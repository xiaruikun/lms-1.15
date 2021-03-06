<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'flexFieldValue.label', default: 'FlexFieldValue')}" />
        <title>编辑弹性域值</title>
    </head>
    <body>
        <div class="small-header">
            <div class="hpanel">
                <div class="panel-body">
                    <div id="hbreadcrumb" class="pull-right">
                        <ol class="hbreadcrumb breadcrumb">
                            <li>中佳信LMS</li>
                            <li><g:link controller="flexFieldCategory" action="index">弹性域模块</g:link></li>
                            <li class="active">
                                弹性域
                            </li>
                        </ol>
                    </div>
                    <h2 class="font-light m-b-xs" style="text-transform:none;">
                        flexField:${this.flexFieldValue?.field?.name}
                    </h2>
                </div>
            </div>
        </div>
        <div class="content animate-panel">
            <div class="row">
                <div class="hpanel hblue">
                    <div class="panel-heading">
                        弹性域值编辑
                    </div>
                    <div class="panel-body">
                        <div id="edit-flexFieldValue" class="content scaffold-edit" role="main">
                            <g:if test="${flash.message}">
                            <div class="message" role="status">${flash.message}</div>
                            </g:if>
                            <g:hasErrors bean="${this.flexFieldValue}">
                            <ul class="errors" role="alert">
                                <g:eachError bean="${this.flexFieldValue}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                                </g:eachError>
                            </ul>
                            </g:hasErrors>
                            <g:form resource="${this.flexFieldValue}" method="PUT" class="form-horizontal">
                                <input type="hidden" name="field" value="${this.flexFieldValue?.field?.id}">

                                <div class="form-group">
                                    <label class="col-md-3 control-label">值</label>

                                    <div class="col-md-3">
                                        <g:textField type="text" name="value" id="value" value="${this.flexFieldValue?.value}" class="form-control"/>
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">次序</label>

                                    <div class="col-md-3">
                                        <g:textField type="text" name="displayOrder" id="displayOrder" value="${this.flexFieldValue?.displayOrder}" class="form-control"/>
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">有效性</label>

                                    <div class="col-md-1">
                                        <g:checkBox name="active" value="${this.flexFieldValue?.active}" class="form-control" style="height:16px;margin-top: 10px"/>
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <div class="col-md-3 col-lg-offset-3">
                                        <g:submitButton name="update" class="btn btn-info" value="保存"/>
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
