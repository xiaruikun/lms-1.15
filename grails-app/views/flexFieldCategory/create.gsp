<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'flexFieldCategory.label', default: 'FlexFieldCategory')}" />
        <title>新增弹性域模块</title>
    </head>
    <body class="fixed-navbar fixed-sidebar">
        <div class="small-header">
            <div class="hpanel">
                <div class="panel-body">
                    <div id="hbreadcrumb" class="pull-right">
                        <ol class="hbreadcrumb breadcrumb">
                            <li>中佳信LMS</li>
                            <li>
                                <g:link controller="flexFieldCategory" action="index">弹性域模块</g:link>
                            </li>
                            <li class="active">
                                <span>新增弹性域模块</span>
                            </li>
                        </ol>
                    </div>
                    <h2 class="font-light m-b-xs">
                        弹性域模块
                    </h2>
                </div>
            </div>
        </div>
        <div class="content animate-panel">
            <div class="row">
                <div class="hpanel hblue">
                    <div class="panel-heading">
                        新增弹性域模块
                    </div>
                    <div class="panel-body">
                        <div id="create-flexFieldCategory" class="content scaffold-create" role="main">
                            <g:if test="${flash.message}">
                                <div class="message" role="status">${flash.message}</div>
                            </g:if>
                            <g:hasErrors bean="${this.flexFieldCategory}">
                                <ul class="errors" role="alert">
                                    <g:eachError bean="${this.flexFieldCategory}" var="error">
                                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                        <g:message error="${error}" />
                                        </li>
                                    </g:eachError>
                                </ul>
                            </g:hasErrors>
                            <g:form action="save" class="form-horizontal">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">名称</label>
                                    <div class="col-md-3">
                                        <g:textField name="name" value="${this.flexFieldCategory?.name}" class="form-control" />
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <div class="col-md-3 col-md-offset-3">
                                        <g:submitButton class="btn btn-info" name="create" value="保存" />
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
