<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'repayPlanTrial.label', default: 'RepayPlanTrial')}" />
    <title>还款计划试算</title>
</head>
<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>还款计划试算</span>
                    </li>
                </ol>            </div>

            <h2 class="font-light m-b-xs">
                还款计划试算
            </h2>
            <small><span class="glyphicon glyphicon-th-large" aria-hidden="true"></span> ${stage?.name} ${status}
            </small>

        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <g:form method="POST" action="index">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <button class="btn btn-primary btn-xs" type="submit" id="submitBtn"><i class="fa fa-search"></i>试算</button>
                        <button class="btn btn-warning2 btn-xs" type="button" id="resetBtn"><i class="fa fa-times"></i>重置</button>
                    </div>
                    还款计划试算
                </div>
                <input type="text" id="stage" name="stage" value="${this.stage?.code}" class="hide">
                <input type="text" id="status" name="status" value="${status}" class="hide">

                <div class="panel-body seach-group">
                   %{-- <div class="col-md-3">
                        <input type="text" class="form-control" placeholder="产品编号" id="productNo" name="productNo"
                               value="${params?.productNo}">
                    </div>--}%

                    <div class="col-md-3">
                        <input type="text" class="form-control" placeholder="申请金额(万元)" id="origPrcp" name="origPrcp"
                               value="${params?.origPrcp}">
                    </div>

                    <div class="col-md-3">
                        <g:select class="form-control" name="loanTyp" id="loanTyp"
                                  from="['速e贷','融e贷']"
                                  valueMessagePrefix="stage" optionKey="" optionValue="" value="${params?.loanTyp}"
                                  noSelection="${['null': '请选择贷款品种']}"/>
                    </div>

                    <div class="col-md-3">
                        <input type="text" class="form-control" placeholder="扣款日" id="dueDay" name="dueDay"
                               value="${params?.dueDay}">
                    </div>

                    <div class="col-md-3">
                        <input type="text" class="form-control" placeholder="执行利率(%)" id="loanIntRate" name="loanIntRate"
                               value="${params?.loanIntRate}">
                    </div>

                    <div class="col-md-3">
                        <div class="input-group date form_datetime">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar"></span>
                            </span>
                            <input title="发放日期" type="text" name="loanActvDt" id="loanActvDt" value="${params?.loanActvDt}" readonly required="required"
                                   class="form-control daily-b" placeholder="发放日期">
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="input-group date form_datetime">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar"></span>
                            </span>
                            <input title="起息日" type="text" name="intStartDt" id="intStartDt" value="${params?.intStartDt}" readonly required="required"
                                   class="form-control daily-b" placeholder="起息日">
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="input-group date form_datetime">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar"></span>
                            </span>
                            <input title="合同到期日" type="text" name="lastDueDt" id="lastDueDt" value="${params?.lastDueDt}" readonly required="required"
                                   class="form-control daily-b" placeholder="合同到期日">
                        </div>
                    </div>

                    <div class="col-md-3">
                        <g:select class="form-control" name="loanPaymMtd" id="loanPaymMtd"
                                  from="['等额本息','先息后本']"
                                  valueMessagePrefix="stage" optionKey="" optionValue="" value="${params?.loanPaymMtd}"
                                  noSelection="${['null': '请选择还款方式']}"></g:select>
                    </div>

                    <div class="col-md-3">
                        <table>
                            <tr>
                                <td>
                                     <input type="text" class="form-control" placeholder="还款间隔" id="paymFreqFreq" name="paymFreqFreq"
                                       value="${params?.paymFreqFreq}">
                                </td>
                                <td>
                                    <g:select class="form-control" name="paymFreqUnit" id="paymFreqUnit"
                                              from="['月','周','季','年']"
                                              valueMessagePrefix="stage" optionKey="" optionValue="" value="${params?.paymFreqUnit}"
                                              noSelection="${['null': '单位']}"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </g:form>
    </div>

    <g:if test="${flash.message}">
        <div class="row">
            <div class="hpanel">
                <div class="panel-body">
                    <div class="alert alert-info" role="alert">${flash.message}</div>
                </div>
            </div>
        </div>
    </g:if>
    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_COO">
                    </sec:ifNotGranted>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                还款计划
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                           %{-- <g:sortableColumn property="serialNumber" title="开始日期"></g:sortableColumn>--}%
                            <td>开始日期</td>
                            <td>起息日期</td>
                            <td>到期日期</td>
                            <td>期供金额(元)</td>
                            <td>本金(元)</td>
                            <td>正常利息(元)</td>
                            <td>期数</td>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${list}">
                            <tr>
                                <td>${it[0]}</td>
                                <td>${it[1]}</td>
                                <td>${it[2]}</td>
                                <td>${it[3]}</td>
                                <td>${it[4]}</td>
                                <td>${it[5]}</td>
                                <td>${it[6]}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<g:javascript>

    $("#submitBtn").click(function(){
        if( $("#origPrcp").val() == "" || $("#dueDay").val() == ""
                || $("#intStartDt").val() == ""|| $("#loanIntRate").val() == ""|| $("#paymFreqFreq").val() == ""
                || $("#loanActvDt").val() == ""|| $("#lastDueDt").val() == ""|| $("#loanTyp").val() == "null"
                || $("#paymFreqUnit").val() == "null" ){
            alert("有必填项为空，请填写")
            return false
        }
    })

    $("#resetBtn").click(function () {
        $("#origPrcp").val("")
        $("#dueDay").val("")
        $("#loanActvDt").val("")
        $("#lastDueDt").val("")
        $("#loanTyp").val("")
        $("#intStartDt").val("")
        $("#loanIntRate").val("")
        $("#paymFreqFreq").val("")
        $("#paymFreqUnit").val("")//下拉框好像不能重置
        $("#loanPaymMtd").val("")
    })
</g:javascript>
</body>
</html>
