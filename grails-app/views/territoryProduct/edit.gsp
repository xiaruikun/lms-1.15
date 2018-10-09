
<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'territoryProduct.label', default: 'TerritoryProduct')}" />
    <title>区域-产品编辑</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="territory" action="index">区域管理</g:link>
                    </li>
                    <li class="active">
                        <span>区域-产品</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                区域管理-编辑产品
            </h2>
        </div>
    </div>
</div>
<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                区域管理-编辑产品
            </div>
            <div class="panel-body">
                <g:form resource="${this.territoryProduct}" method="PUT"  class="form-horizontal">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.territoryProduct}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.territoryProduct}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                    <g:message error="${error}" />
                                </li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <div class="form-group">
                        <label class="col-md-3 control-label">新增产品</label>
                        <div class="col-md-3 ">
                            <g:select class="form-control" name="product.id" required="required" id="product" value="${this.territoryProduct?.product?.id}" from="${com.next.Product.findAllByActive(true)}" optionKey="id" optionValue="name" noSelection="${['null':'请选择']}"></g:select>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">区域名称:</label>
                        <div class="col-md-3">
                            <!-- <g:select name="territory.id" required="required" id="territory" value="${this.territoryProduct?.territory?.id}" from="${com.next.Territory.list()}" optionKey="id" optionValue="name" noSelection="${['null':'Select One...']}"></g:select> -->
                            <g:textField name="territory.id" required="required" id="territory" value="${this.territoryProduct?.territory?.id}" class="hide" />
                            <g:textField name="territoryName" value="${this.territoryProduct?.territory?.name}" readonly="readonly" class="form-control" />
                            <!-- <g:textField name="territoryName" value="${this.territoryProduct?.territory?.name}" disabled="" class="form-control" /> -->
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">费率</label>
                        <div class="col-md-3">
                            <g:textField name="rate" value="${this.territoryProduct?.rate}" class="form-control" />
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label" for="startTime">上线时间</label>
                        <div class="col-md-3 datePicker">
                            <g:datePicker name="startTime" value="${new Date()}" precision="day" />
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label" for="endTime">下架时间</label>
                        <div class="col-md-3 datePicker">
                            <g:datePicker name="endTime" value="${new Date()}" precision="day" />
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
                            <g:submitButton class="btn btn-info" name="update" value="保存" />
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>

</body>

</html>

