<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName"
           value="${message(code: 'opportunityFlexFieldCategory.label', default: 'opportunityFlexFieldCategory')}"/>
    <title>弹性域批量修改</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="opportunity" action="show"
                                id="${this.opportunityFlexFieldCategory?.opportunity?.id}">订单详情</g:link>
                    </li>
                    <li class="active">
                        <span>弹性域批量修改</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                弹性域
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                弹性域修改
            </div>

            <div class="panel-body">
                <g:form controller="opportunityFlexField" action="batchUpdate" class="form-horizontal">
                    <input type="hidden" name="opportunityFlexFieldCategory" id="opportunityFlexFieldCategory"
                           value="${this.opportunityFlexFieldCategory?.id}">
                    <input type="hidden" name="outReport" id="outReport" value="${params['outReport']}">

                    <g:each in="${this.opportunityFlexFieldCategory?.fields}">
                        <div class="form-group">
                            <g:if test="${it?.name in ['预计出他项时间', '预计公证时间']}">
                                <g:if test="${com.next.Collateral.findByOpportunity(opportunityFlexFieldCategory?.opportunity)?.city?.name in ['上海', '石家庄', '厦门', '成都', '武汉']}">
                                    <g:if test="${it?.name == '预计公证时间'}">
                                        <label class="col-md-3 control-label">预计公证时间</label>
                                    </g:if>
                                </g:if>
                                <g:else>
                                    <g:if test="${opportunityFlexFieldCategory?.opportunity?.mortgageCertificateType?.name == '他项证明' && it?.name == '预计出他项时间'}">
                                        <label class="col-md-3 control-label">预计出他项时间</label>
                                    </g:if>
                                </g:else>
                            </g:if>
                            <g:else>
                                <label class="col-md-3 control-label">${it?.name}</label>
                            </g:else>
                            <div class="col-md-3">
                                <g:if test="${it.values||it.name in ['放款通道','放款账号','抵押权人']}">
                                    <g:select class="form-control" name="${it?.name}" id="${it?.name}"
                                              value="${it?.value}" optionKey="value" optionValue="value"
                                              from="${it?.values}" noSelection="['': '-请选择-']"></g:select>
                                </g:if>
                                <g:else>
                                    <g:if test="${it?.name in ['预计出他项时间', '预计公证时间']}">
                                        <g:if test="${com.next.Collateral.findByOpportunity(opportunityFlexFieldCategory?.opportunity)?.city?.name in ['上海', '石家庄', '厦门', '成都', '武汉']}">
                                            <g:if test="${it?.name == '预计公证时间'}">
                                                <div class="input-group date form_datetime2">
                                                    <span class="input-group-addon">
                                                        <span class="fa fa-calendar"></span>
                                                    </span>
                                                    <input title="${it?.name}" type="text" name="${it?.name}"
                                                           id="${it?.name}" value="${it?.value}" readonly
                                                           class="form-control daily-b" placeholder="2018-12-12">
                                                </div>
                                            </g:if>
                                        </g:if>
                                        <g:else>
                                            <g:if test="${opportunityFlexFieldCategory?.opportunity?.mortgageCertificateType?.name == '他项证明' }">
                                                <g:if test="${it?.name == '预计出他项时间'}">
                                                    <div class="input-group date form_datetime2">
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                        <input title="${it?.name}" type="text" name="${it?.name}"
                                                               id="${it?.name}" value="${it?.value}" readonly
                                                               class="form-control daily-b" placeholder="2018-12-12">
                                                    </div>
                                                </g:if>
                                            </g:if>
                                        </g:else>
                                    </g:if>
                                    <g:else>
                                        <g:if test="${it?.name == '预计抵押登记时间'}">
                                            <div class="input-group date form_datetime2">
                                                <span class="input-group-addon">
                                                    <span class="fa fa-calendar"></span>
                                                </span>
                                                <input title="${it?.name}" type="text" name="${it?.name}"
                                                       id="${it?.name}" value="${it?.value}" readonly
                                                       class="form-control daily-b" placeholder="2018-12-12">
                                            </div>
                                        </g:if>
                                        <g:else>
                                            <g:textArea name="${it?.name}" id="${it?.name}" value="${it?.value}"
                                                        class="form-control" rows="3" cols="15"/>
                                        </g:else>
                                    </g:else>
                                </g:else>
                            </div>
                        </div>
                    </g:each>
                    <g:if test="${opportunityFlexFieldCategory?.flexFieldCategory?.name == "放款路径发放时间依据"}">
                        <div class="form-group">
                            <label class="col-md-3 control-label">预计放款时间</label>

                            <div class="col-md-3">
                                <div class="input-group date form_datetime2">
                                    <span class="input-group-addon">
                                        <span class="fa fa-calendar"></span>
                                    </span>
                                    <input title="estimatedLendingDate" type="text" name="estimatedLendingDate"
                                           id="estimatedLendingDate"
                                           value="<g:formatDate format="yyyy-MM-dd" date="${opportunityFlexFieldCategory?.opportunity?.estimatedLendingDate}"/>"
                                           readonly class="form-control daily-b" placeholder="2018-12-12">
                                </div>
                            </div>
                        </div>
                    </g:if>
                    <div class="hr-line-dashed"></div>
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
<script>
    $(function () {
        $('.form_datetime2').datetimepicker({
            minView: "month", //选择日期后，不会再跳转去选择时分秒
            format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
            language: 'zh-CN', //汉化
            autoclose: true //选择日期后自动关闭
        });

    })
</script>
</body>
</html>

