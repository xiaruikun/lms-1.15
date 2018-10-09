<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'flexField.label', default: 'FlexField')}" />
        <title>编辑弹性域</title>
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
                        弹性域模块: ${this.flexField?.category?.name}
                    </h2>
                </div>
            </div>
        </div>
        <div class="content animate-panel">
            <div class="row">
                <div class="hpanel hblue">
                    <div class="panel-heading">
                        弹性域模块-弹性域信息编辑
                    </div>

                    <div class="panel-body">
                        <div id="edit-flexField" class="content scaffold-edit" role="main">
                            <g:if test="${flash.message}">
                            <div class="message" role="status">${flash.message}</div>
                            </g:if>
                            <g:hasErrors bean="${this.flexField}">
                            <ul class="errors" role="alert">
                                <g:eachError bean="${this.flexField}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                                </g:eachError>
                            </ul>
                            </g:hasErrors>
                            <g:form resource="${this.flexField}" method="PUT" class="form-horizontal">
                                <input type="hidden" name="category" value="${this.flexField?.category?.id}">

                                <div class="form-group">
                                    <label class="col-md-3 control-label">名称:</label>

                                    <div class="col-md-3">
                                        <g:textField type="text" name="name" id="name" value="${this.flexField?.name}" class="form-control"/>
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">描述</label>

                                    <div class="col-md-3">
                                        <g:textField type="text" name="description" id="description" value="${this.flexField?.description}" class="form-control"/>
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">次序</label>

                                    <div class="col-md-3">
                                        <g:textField type="text" name="displayOrder" id="displayOrder" value="${this.flexField?.displayOrder}" class="form-control"/>
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">数据类型</label>

                                    <div class="col-md-3">
                                        <g:textField type="text" name="dataType" id="dataType" value="${this.flexField?.dataType}" class="form-control"/>
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">默认值</label>

                                    <div class="col-md-3">
                                        <g:textField type="text" id="defaultValue" name="defaultValue"  value="${this.flexField?.defaultValue}" class="form-control"/>
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">约束</label>

                                    <div class="col-md-3">
                                        <g:textField type="text" id="valueConstraints" name="valueConstraints" value="${this.flexField?.valueConstraints}" class="form-control"/>
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">有效性</label>

                                    <div class="col-md-1 checkbox-inline">
                                        <g:checkBox class="i-checks" name="active" value="${this.flexField?.active}"/>
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
