<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}"/>
    <title>新增产品：${com.next.Product.findById(params['id'])}</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        产品-机构
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs" style="text-transform:none;">
                产品: ${com.next.Product.findById(params['id'])}
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                产品-机构
            </div>

            <div class="panel-body">
                <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                </g:if>
                <g:hasErrors bean="${this.productAccount}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${this.productAccount}" var="error">
                            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                    error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </g:hasErrors>
                <g:form action="save" class="form-horizontal">
                    <input type="hidden" name="product.id" value="${params['id']}">

                    <div class="form-group">
                        <label class="col-md-3 control-label">名称:</label>

                        <div class="col-md-3">
                            <g:textField type="text" name="name" id="name" class="form-control"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">描述</label>

                        <div class="col-md-3">
                            <g:textField type="text" name="description" id="description" class="form-control"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">最小值</label>

                        <div class="col-md-3">
                            <g:textField type="text" name="minimumAmount" id="minimumAmount" class="form-control"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">最大值</label>

                        <div class="col-md-3">
                            <g:textField type="text" id="maximumAmount" name="maximumAmount" class="form-control"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">机构</label>

                        <div class="col-md-3">
                            <g:select class="form-control m-b" name="account.id" id="account"
                                      from="${com.next.Account.list()}" noSelection="['': '-请选择-']"
                                      optionKey="id" optionValue="name"></g:select>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">有效性</label>

                        <div class="col-md-1">
                            <g:checkBox name="active" value="${true}" class="form-control" style="height:16px;margin-top: 10px"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">本金支付方式</label>

                        <div class="col-md-3">
                            <g:select class="form-control m-b" name="principalPaymentMethod.id"
                                      id="principalPaymentMethod" from="${com.next.PrincipalPaymentMethod.list()}"
                                      noSelection="['': '-请选择-']"
                                      optionKey="id" optionValue="name"></g:select>
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
