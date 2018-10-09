<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'territoryProduct.label', default: 'TerritoryProduct')}" />
        <title>区域产品-产品详情</title>
    </head>
    <body>
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="territoryProduct" action="index">区域-产品列表</g:link>
                        </li>
                        <li class="active">
                            <span>区域-产品</span>
                        </li>
                    </ol>
                </div>

                <h2 class="font-light m-b-xs">
                    区域产品-产品详情
                </h2>
            </div>
        </div>
    </div>
    %{--<f:display bean="territoryProduct" />--}%
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <g:form resource="${this.territoryProduct}" method="DELETE">
                            <fieldset class="buttons">
                                <g:link class="btn btn-info btn-xs" action="edit" resource="${this.territoryProduct}"><i class="fa fa-edit"></i>编辑</g:link>
                                <input class="btn btn-info btn-xs" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                            </fieldset>
                        </g:form>
                    </div>
                    区域-产品
                </div>
                <div class="panel-body">
                    <g:form action="save" class="form-horizontal">
                        <g:if test="${flash.message}">
                            <div class="message" role="status">${flash.message}</div>
                        </g:if>
                        <div class="form-group">
                            <label class="col-md-3 control-label">新增产品</label>
                            <div class="col-md-3 ">
                                <g:textField name="product.id" class="form-control" required="required" id="product" disabled="" value="${this.territoryProduct?.product?.name}" />
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
