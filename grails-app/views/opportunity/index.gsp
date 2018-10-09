<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>订单</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="normalheader">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right m-t-lg">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>订单管理</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                订单管理
            </h2>
            <small><span class="glyphicon glyphicon-th-large" aria-hidden="true"></span> 所有订单</small>
        </div>
    </div>
</div>
<div class="row">
<div class="container">
    <g:form method="POST" action="searchOpportunity">
        <div class="hpanel hblue">
            <div class="panel-heading">
                <div class="panel-tools">
                    <button class="btn btn-default btn-xs" type="submit">开始</button>
                    <button class="btn btn-default btn-xs" type="button" id="resetBtn">重置</button>
                </div>
                查询
            </div>

            <div class="panel-body">
                <div class="col-sm-3">
                    <input type="text" class="form-control" placeholder="订单编号" id="serialNumber" name="serialNumber"
                           value="${opportunity?.serialNumber}">
                </div>

                <div class="col-sm-3">
                    <input type="text" class="form-control" placeholder="借款人姓名" id="fullName" name="fullName"
                           value="${opportunity?.fullName}">
                </div>

                <div class="col-sm-3">
                    <g:select class="form-control" name="stage" id="stage"
                              from="${["订单状态", "评房申请已提交", "评房已完成", "报单申请已提交", "房产初审已完成", "审批已完成", "抵押公正手续已完成", "放款已完成", "返点已完成"]}"
                              valueMessagePrefix="stage" value="${this.opportunity?.stage?.name}"/>
                </div>

                <div class="col-sm-3">
                    <g:select class="form-control" name="causeOfFailure" id="causeOfFailure"
                              from="${["未成交归类", "评估值不够", "二抵剩余残值不够", "考虑中", "风控降成", "我司不受理", "超过公司单笔贷款金额", "息费高", "贷款手续麻烦", "资金问题已解决（非同行）", "在同行已成交", "房本被占用", "只办理0.6%的业务", "客户需求不明确", "客户要求市值的七成", "正在补资料", "家人不同意", "客户资质不够"]}"
                              valueMessagePrefix="causeOfFailure" value="${this.opportunity?.causeOfFailure?.name}"/>
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
    </div>
</g:if>
<div class="row">
    <div class="container">
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link action="create" class="btn btn-default btn-xs">新建</g:link>
                </div>
                全部订单
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="serialNumber"
                                              title="${message(code: 'opportunity.name.label', default: '订单编号')}"></g:sortableColumn>
                            <g:sortableColumn property="stage"
                                              title="${message(code: 'opportunity.stage.label', default: '订单状态')}"></g:sortableColumn>
                            <g:sortableColumn property="status"
                                              title="${message(code: 'opportunity.status.label', default: '状态描述')}"></g:sortableColumn>
                            <g:sortableColumn property="fullName"
                                              title="${message(code: 'opportunity.fullName.label', default: '借款人姓名')}"></g:sortableColumn>
                            <g:sortableColumn property="fullName"
                                              title="${message(code: 'opportunity.contact.fullName.label', default: '经纪人姓名')}"></g:sortableColumn>
                            <g:sortableColumn property="fullName"
                                              title="${message(code: 'opportunity.user.fullName.label', default: '支持经理姓名')}"></g:sortableColumn>
                            <g:sortableColumn property="user" title="客户来源"></g:sortableColumn>
                            <g:sortableColumn property="city"
                                              title="${message(code: 'opportunity.city.label', default: '房屋信息')}"></g:sortableColumn>
                            <g:sortableColumn property="loanAmount"
                                              title="${message(code: 'opportunity.loanAmount.label', default: '询值总价')}"></g:sortableColumn>
                            <g:sortableColumn property="requestedAmount"
                                              title="${message(code: 'opportunity.requestedAmount.label', default: '申请总价')}"></g:sortableColumn>
                            <g:sortableColumn property="loanDuration"
                                              title="${message(code: 'opportunity.loanDuration.label', default: '申请时间（月）')}"></g:sortableColumn>
                            <g:sortableColumn property="memo"
                                              title="${message(code: 'opportunity.memo.label', default: '未报单原因')}"></g:sortableColumn>
                            <g:sortableColumn property="causeOfFailure"
                                              title="${message(code: 'opportunity.causeOfFailure.label', default: '未成交归类')}"></g:sortableColumn>
                            <g:sortableColumn property="createdDate"
                                              title="${message(code: 'opportunity.createdDate.label', default: '申请时间')}"></g:sortableColumn>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${opportunityList}">
                            <tr>
                                <td><g:link action="show" id="${it.id}" class="firstTd">${it.serialNumber}</g:link></td>
                                <td>${it.stage?.name}</td>
                                <td>${it.status}</td>
                                <td>${it.fullName}</td>
                                <td>${it.contact?.fullName}</td>
                                <td>${it.user?.fullName}</td>
                                <td>
                                    <g:if test="${it.user == '' || it.user == null}">直客</g:if><g:else>渠道</g:else>
                                </td>
                                <td>
                                    <g:if test="${it.city}">${it.city}</g:if>
                                    <g:if test="${it.building}">;${it.building}</g:if>
                                    <g:if test="${it.floor}">;${it.floor}/</g:if><g:if
                                            test="${it.totalFloor}">${it.totalFloor}</g:if>
                                    <g:if test="${it.roomNumber}">;${it.roomNumber}</g:if>
                                    <g:if test="${it.orientation}">;${it.orientation}</g:if>
                                    <g:if test="${it.area}">;${it.area}m²</g:if>
                                    <g:if test="${it.houseType}">;${it.houseType}</g:if>
                                    <g:if test="${it.specialFactors}">;${it.specialFactors}</g:if>
                                </td>
                                <td>${it.loanAmount}</td>
                                <td>${it.requestedAmount}</td>
                                <td>${it.loanDuration}</td>
                                <td style="word-wrap: break-word"><a title="${it.memo}">${it.memo}</a></td>
                                <td>${it.causeOfFailure?.name}</td>
                                <td><g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it.createdDate}"/></td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${opportunityCount ?: 0}"/>
                </div>
            </div>

        </div>

    </div>
</div>

<g:javascript>
    $("#resetBtn").click(function () {
        $("#serialNumber").val("");
        $("#stage").val("订单状态");
        $("#causeOfFailure").val("未成交归类");
        $("#fullName").val("");
    })
</g:javascript>
</body>
</html>