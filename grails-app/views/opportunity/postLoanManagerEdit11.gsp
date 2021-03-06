<!DOCTYPE html>
<html lang="en">

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>逾期订单编辑</title>
    <style>
    .input-group[class*="col-"] {
        float: left;
        padding-right: 15px;
        padding-left: 15px;
    }

    </style>
</head>

<body class="fixed-navbar fixed-sidebar">
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
                        <span>逾期信息编辑</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light">
                订单号: ${this.opportunity.serialNumber}
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <g:form resource="${this.opportunity}" method="PUT" name="myForm" class="form-horizontal">
        <div class="row">
            <g:if test="${flash.message}">
                <div class="message" role="status">
                    ${flash.message}
                </div>
            </g:if>
            <g:hasErrors bean="${this.opportunity}">
                <ul class="message" role="alert">
                    <g:eachError bean="${this.opportunity}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                            <g:message error="${error}"/>
                        </li>
                    </g:eachError>
                </ul>
            </g:hasErrors>
        </div>
        <div class="row">
            <div class="hpanel hyellow">
                <div class="panel-heading">
                    逾期订单详情
                </div>

                <div class="panel-body form-horizontal">
                    <div class="form-group">
                        <label class="col-md-3 control-label">订单状态：</label>

                        <div class="col-md-2">
                            <span class="cont">${this.opportunity?.stage?.name}</span>
                        </div>
                        <label class="col-md-3 control-label">状态描述：</label>

                        <div class="col-md-2">
                            <span class="cont">
                                <g:if test="${this.opportunity?.status == 'Pending'}">进行中</g:if>
                                <g:if test="${this.opportunity?.status == 'Failed'}">失败</g:if>
                                <g:if test="${this.opportunity?.status == 'Completed'}">成功</g:if>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="hpanel horange">
                <div class="panel-heading">
                    实际还款日期
                </div>

                <div class="panel-body form-horizontal">
                    <div class="form-group m-b-none">
                        <label class="col-md-3 control-label">实际还款日期</label>

                        <div class="col-md-5">
                            <g:datePicker precision="day" default="none" noSelection="['': '-请选择-']"
                                          name="actuaRepaymentDate" years="[2016, 2017, 2018, 2019, 2020]"
                                          value="${this.opportunity?.actuaRepaymentDate}"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="hpanel horange">
                <div class="panel-heading">
                    解押方式
                </div>

                <div class="panel-body form-horizontal">
                    <div class="form-group m-b-none">
                        <label class="col-md-3 control-label">解押方式</label>

                        <div class="col-md-2">
                            <g:select class="form-control" optionKey="id" optionValue="name" noSelection="${['': '--请选择--']}"
                                      name="mortgageReleasingType"
                                      value="${this.opportunity?.mortgageReleasingType?.id}"
                                      from="${com.next.MortgageReleasingType.list()}"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-md-3 col-md-offset-4">
                <g:submitButton class="btn btn-info col-md-3" name="update" value="保存"/>
            </div>
        </div>
    </g:form>
</div>
</body>
</html>
