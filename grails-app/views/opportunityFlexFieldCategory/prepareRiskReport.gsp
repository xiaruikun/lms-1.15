<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName"
           value="${message(code: 'opportunityFlexFieldCategory.label', default: 'OpportunityFlexFieldCategory')}"/>
    <title>风险调查报告查看和编辑</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="opportunity" action="show" id="${this.opportunity?.id}">订单详情</g:link>
                    </li>
                    <li class="active">
                        <span>风险调查报告编辑</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                风险调查报告
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <g:form controller="opportunityFlexFieldCategory" action="riskReport">
            <input type="hidden" name="opportunity" id="opportunity" value="${this.opportunity?.id}">
            <g:each in="${this.opportunityFlexFieldCategorys}" var="category">
                <g:if test="${category?.flexFieldCategory?.name in ['抵押物情况', '借款人资质小结', '征信小结', '大数据小结', '借款用途', '还款来源', '风险结论', '放款前要求', '罚息约定']}">
                    <div class="hpanel hblue">
                        <div class="panel-heading">
                            <div class="panel-tools">
                                <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                            </div>
                            ${category?.flexFieldCategory?.name}
                        </div>


                        <div class="panel-body form-horizontal">

                            <g:each in="${category.fields}" var="field">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">${field?.name}</label>

                                    <div class="col-md-5">
                                        <g:if test="${field.values}">
                                            <g:select class="form-control" name="${field?.name}" id="${field?.name}"
                                                      value="${field?.value}" optionKey="value" optionValue="value"
                                                      from="${field?.values}" noSelection="['': '-请选择-']"></g:select>
                                        </g:if>
                                        <g:else>
                                            <g:textArea name="${field?.name}" id="${field?.name}"
                                                        value="${field?.value}" class="form-control" rows="5"/>
                                        </g:else>
                                    </div>
                                </div>

                                <div class="hr-line-dashed"></div>
                            </g:each>

                        </div>
                    </div>
                </g:if>
            </g:each>
            <div class="form-group" style="margin-top: 20px;">
                <div class="col-md-3 col-md-offset-4">
                    <g:submitButton class="btn btn-info" name="update" value="保存"/>
                </div>

            </div>

        </g:form>
    </div>
</div>

%{--<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                风险调查报告修改
            </div>

            <div class="panel-body">
                <g:form controller="opportunityFlexFieldCategory" action="riskReport" class="form-horizontal">
                    <input type="hidden" name="opportunity" id="opportunity" value="${this.opportunity?.id}">
                    <g:each in="${this.opportunityFlexFieldCategorys}" var="category">
                        <g:if test="${category?.flexFieldCategory?.name in ['抵押物情况', '借款人资质小结', '征信小结', '大数据小结', '借款用途', '还款来源', '风险结论', '放款前要求']}">

                            <label class="control-label">${category?.flexFieldCategory?.name}:</label>
                            </br>
                            <g:each in="${category.fields}" var="field">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">${field?.name}</label>

                                    <div class="col-md-5">
                                        <g:if test="${field.values}">
                                            <g:select class="form-control" name="${field?.name}" id="${field?.name}"
                                                      value="${field?.value}" optionKey="value" optionValue="value"
                                                      from="${field?.values}" noSelection="['': '-请选择-']"></g:select>
                                        </g:if>
                                        <g:else>
                                            <g:textArea name="${field?.name}" id="${field?.name}"
                                                        value="${field?.value}" class="form-control" rows="3"/>
                                        </g:else>
                                    </div>
                                </div>

                                <div class="hr-line-dashed"></div>
                            </g:each>

                        </g:if>
                    </g:each>
                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
                            <g:submitButton class="btn btn-info" name="update" value="保存"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                风险调查报告修改
            </div>

            <div class="panel-body">
                <g:form controller="opportunityFlexFieldCategory" action="riskReport" class="form-horizontal">
                    <input type="hidden" name="opportunity" id="opportunity" value="${this.opportunity?.id}">
                    <g:each in="${this.opportunityFlexFieldCategorys}" var="category">
                        <g:if test="${category?.flexFieldCategory?.name in ['抵押物情况', '借款人资质小结', '征信小结', '大数据小结', '借款用途', '还款来源', '风险结论', '放款前要求']}">
                            <div class="form-group">
                                <label class="control-label">${category?.flexFieldCategory?.name}:</label>
                            </br>
                            <g:each in="${category.fields}" var="field">
                                <label class="col-md-3 control-label">${field?.name}</label>

                                <div class="col-md-8">
                                    <g:if test="${field.values}">
                                        <g:select class="form-control" name="${field?.name}" id="${field?.name}"
                                                  value="${field?.value}" optionKey="value" optionValue="value"
                                                  from="${field?.values}" noSelection="['': '-请选择-']"></g:select>
                                    </g:if>
                                    <g:else>
                                        <g:textArea name="${field?.name}" id="${field?.name}" value="${field?.value}"
                                                    class="form-control" rows="5" cols="15"/>
                                    </g:else>
                                </div>
                            </g:each>

                            </div>
                            <div class="hr-line-dashed"></div>
                        </g:if>
                    </g:each>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
                            <g:submitButton class="btn btn-info" name="update" value="保存"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>--}%

</body>
</html>
