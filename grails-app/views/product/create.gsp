<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}" />
        <title>新增产品</title>
    </head>

    <body class="fixed-navbar fixed-sidebar">
        <div class="small-header">
            <div class="hpanel">
                <div class="panel-body">
                    <div id="hbreadcrumb" class="pull-right">
                        <ol class="hbreadcrumb breadcrumb">
                            <li>中佳信LMS</li>
                            <li>
                                <g:link controller="product" action="index">产品管理</g:link>
                            </li>
                            <li class="active">
                                <span>新增产品</span>
                            </li>
                        </ol>
                    </div>

                    <h2 class="font-light m-b-xs">
                        产品管理
                    </h2>
                </div>
            </div>
        </div>
        <div class="content animate-panel">
            <div class="row">
                <div class="hpanel hblue">
                    <div class="panel-heading">
                        新增产品
                    </div>
                    <div class="panel-body">
                        <g:form action="save" class="form-horizontal">
                            <g:if test="${flash.message}">
                                <div class="message" role="status">${flash.message}</div>
                            </g:if>
                            <g:hasErrors bean="${this.product}">
                                <ul class="errors" role="alert">
                                    <g:eachError bean="${this.product}" var="error">
                                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                            <g:message error="${error}" />
                                        </li>
                                    </g:eachError>
                                </ul>
                            </g:hasErrors>
                            <div class="form-group">
                                <label class="col-md-3 control-label">产品名称</label>
                                <div class="col-md-3">
                                    <g:textField name="name" value="${this.product?.name}" class="form-control" />
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">有效性</label>

                                <div class="col-md-1">
                                    <g:checkBox name="active" value="${this.product?.active}" class="form-control"
                                                style="height:16px;margin-top: 10px"/>
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
    </body>
</html>
