<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'assetPool.label', default: 'assetPool')}"/>
    <title>新增资产池订单</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="assetPool" action="index">资产池订单管理</g:link>
                    </li>
                    <li class="active">
                        <span>新增资产池订单</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                新增资产池订单
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        %{--<g:form action="save">
            <fieldset class="form">
                <f:all bean="assetPoolOpportunity"/>
            </fieldset>
            <fieldset class="buttons">
                <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
            </fieldset>
        </g:form>--}%
        <div class="hpanel hblue">
            <div class="panel-heading">
                新增资产池订单
            </div>

            <div class="panel-body">
                <g:form action="save" class="form-horizontal">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.assetPoolOpportunity}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.assetPoolOpportunity}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <div class="form-group">
                        <label class="col-md-3 control-label">资产池名称</label>

                        <div class="col-md-4">
                            <input type="text" class="form-control" name="assetPoolName" value="${this.assetPoolOpportunity?.assetPool?.name}" readonly>

                            %{--<g:textField name="assetPoolName" class="form-control" value="${this.assetPoolOpportunity?.assetPoolName}"/>--}%
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <input type="hidden" name="assetPool.id" value="${this.assetPoolOpportunity?.assetPool?.id}">

                        <label class="col-md-3 control-label">资产池订单号</label>

                        <div class="col-md-4">
                            <g:textField name="opportunitySerialNumber" class="form-control"
                                         value="${this.assetPoolOpportunity?.opportunitySerialNumber}"/>
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


