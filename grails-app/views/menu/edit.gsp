<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'menu.label', default: 'Menu')}" />
        <title>编辑菜单</title>
    </head>
    <body>
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="menu" action="index">菜单管理</g:link>
                        </li>
                        <li class="active">
                            <span>编辑菜单</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    菜单管理
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    编辑菜单
                </div>
                <div class="panel-body">
                    <g:form resource="${this.menu}" method="PUT" class="form-horizontal">
                        <g:if test="${flash.message}">
                            <div class="message" menu="status">${flash.message}</div>
                        </g:if>
                        <g:hasErrors bean="${this.menu}">
                            <ul class="errors" menu="alert">
                                <g:eachError bean="${this.menu}" var="error">
                                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                        <g:message error="${error}" />
                                    </li>
                                </g:eachError>
                            </ul>
                        </g:hasErrors>
                        <div class="form-group">
                            <label class="col-md-4 control-label">菜单名称</label>
                            <div class="col-md-4">
                                <g:textField name="name" value="${this.menu?.name}" class="form-control" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-md-3 col-md-offset-5">
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
