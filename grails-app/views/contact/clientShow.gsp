<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>贷款人: ${this.contact?.fullName}</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">

    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li><g:link controller="contact" action="indexByClient">贷款人管理</g:link></li>
                    <li class="active">
                        <span>${this.contact?.fullName}</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                贷款人: ${this.contact?.fullName}
            </h2>
            <small><span class="glyphicon glyphicon-user" aria-hidden="true"></span>${this.contact?.fullName} 的资料
            </small>
        </div>
    </div>

</div>

<div class="content animate-panel">

    <div class="row">

        <div class="hpanel hred contact-panel">
            <div class="panel-heading">
                <div class="panel-tools">
                  <sec:ifNotGranted roles="ROLE_COO">
                    <g:link class="btn btn-info btn-xs" action="edit"
                            resource="${this.contact}"><i class="fa fa-edit"></i>编辑</g:link>
                          </sec:ifNotGranted>
                </div>
                贷款人基本信息
            </div>

            <div class="panel-body">
                <div class="text-muted font-bold m-b-xs ol-md-6">

                    <div class="col-md-12"><h4>贷款人：${this.contact?.fullName}</h4></div>


                    <div class="col-md-2"><span>贷款人手机号：</span><strong class="cellphoneFormat">${this.contact?.cellphone}</strong></div>

                    <div class="col-md-4"><span>贷款人身份证号：</span><strong>${this.contact?.idNumber}</strong></div>
                </div>

            </div>

        </div>

    </div>

    <div class="row">
        <div class="hpanel hyellow">
            <div class="panel-heading">
                <div class="panel-tools">
                  <sec:ifNotGranted roles="ROLE_COO">
                    <g:link class="btn btn-info btn-xs" controller="company" action="create" params="[contact: this.contact?.id]"><i class="fa fa-plus"></i> 新增</g:link>
                  </sec:ifNotGranted>
                </div>
                公司信息
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="company" title="公司"/>
                            <g:sortableColumn property="industry" title="行业"/>
                            <g:sortableColumn property="companyCode" title="工商&机构代码"/>
                            <g:sortableColumn property="operation" title="操作"/>
                        </thead>
                        <tbody>
                        <g:each in="${this.contact?.companies}">
                            <tr>
                                <td><g:link controller="company" action="show" id="${it?.id}">${it.company}</g:link></td>
                                <td>${it?.industry?.name}</td>
                                <td>${it?.companyCode}</td>
                                <td width="8%">
                                    <g:form controller="company" action="delete" id="${it?.id}" method="DELETE">
                                        <button class="deleteBtn btn btn-danger btn-xs" type="button">删除</button>
                                    </g:form>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>

                    </table>
                </div>

            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hyellow">
            <div class="panel-heading">
                业务信息
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="serialNumber"
                                              title="${message(code: 'opportunity.serialNumber.label', default: '关联订单')}"/>
                            <g:sortableColumn property="fullName"
                                              title="${message(code: 'opportunity.serialNumber.label', default: '借款人姓名')}"/>
                            <g:sortableColumn property="cellphone"
                                              title="${message(code: 'opportunity.fullName.label', default: '借款人手机号')}"/>
                            <g:sortableColumn property="actualAmountOfCredit"
                                              title="${message(code: 'opportunity.stage.label', default: '借款金额（万元）')}"/>
                            <g:sortableColumn property="status"
                                              title="${message(code: 'opportunity.status.label', default: '借款期限（月）')}"/>
                            <g:sortableColumn property="contact.fullName"
                                              title="${message(code: 'contact.opportunity.fullName.label', default: '经纪人姓名')}"/>
                            <g:sortableColumn property="stage.name"
                                              title="${message(code: 'stage.opportunity.name.label', default: '订单状态')}"/>
                            <g:sortableColumn property="createdDate"
                                              title="${message(code: 'opportunity.createdDate.label', default: '申请时间')}"/>
                        </thead>
                        <tbody>
                        <g:each in="${opportunityList}">
                            <tr>
                                <td><g:link controller="opportunity" action="show"
                                            id="${it.id}">${it.serialNumber}</g:link></td>
                                <td>${it.fullName}</td>
                                <td class="cellphoneFormat">${it.cellphone}</td>
                                <td>${it.actualAmountOfCredit}</td>
                                <td>${it.loanDuration}</td>
                                <td>${it.contact?.fullName}</td>
                                <td>${it.stage?.name}</td>
                                <td><g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it.createdDate}"/></td>
                            </tr>
                        </g:each>
                        </tbody>

                    </table>
                </div>

            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading ">
                <div class="panel-tools">
                </div>
                征信记录
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="executionSequence"
                                              title="${message(code: 'opportunityFlow.executionSequence.label', default: '征信人员姓名')}"/>
                            <g:sortableColumn property="stage"
                                              title="${message(code: 'opportunityFlow.score.label', default: '征信值')}"/>
                            <g:sortableColumn property="stage"
                                              title="${message(code: 'opportunityFlow.provider.label', default: '征信渠道')}"/>
                            <g:sortableColumn property="stage"
                                              title="${message(code: 'opportunityFlow.createdDate.label', default: '征信查询时间')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.creditReport}">
                            <tr>
                                <td><g:link controller="creditReport" action="show" id="${it.id}"
                                            class="firstTd">${it.contact?.fullName}</g:link></td>
                                <td>${it.score}</td>
                                <td>${it.provider?.name}</td>
                                <td><g:formatDate class="weui_input" date="${it.createdDate}"
                                                  format="yyyy-MM-dd HH:mm:ss"
                                                  name="createdDate" autocomplete="off"
                                                  readonly="true"></g:formatDate></td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

</div>

</body>
</html>
