<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'assetPool.label', default: 'assetPool')}" />
        <title>资产池</title>
    </head>
    <body>
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="assetPool" action="index">资产池管理</g:link>
                        </li>
                        <li class="active">
                            <span>新增资产池</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    资产池管理
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            %{--  <g:form action="save">
                    <fieldset class="form">
                        <f:all bean="assetPool"/>
                    </fieldset>
                    <fieldset class="buttons">
                        <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                    </fieldset>
                </g:form>--}%
            <div class="hpanel hblue">
                <div class="panel-heading">
                    新增资产池
                </div>
                <div class="panel-body">
                    <g:form action="save" class="form-horizontal">
                        <g:if test="${flash.message}">
                            <div class="message" assetPool="status">${flash.message}</div>
                        </g:if>
                        <g:hasErrors bean="${this.assetPool}">
                            <ul class="errors" assetPool="alert">
                                <g:eachError bean="${this.assetPool}" var="error">
                                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                        <g:message error="${error}" />
                                    </li>
                                </g:eachError>
                            </ul>
                        </g:hasErrors>
                        <div class="form-group">
                            <label class="col-md-3 control-label">资产池名称</label>
                            <div class="col-md-4">
                                <g:textField name="name" class="form-control" />
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
4