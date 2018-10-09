<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'bills.label', default: 'Bills')}"/>
    <title>还款计划列表</title>
</head>

<body class="fixed-navbar fixed-sidebar">
%{--<f:table collection="${billsList}" />--}%
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>还款计划</span>
                    </li>
                    <li class="active">
                        <span>还款计划列表</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                还款计划列表
            </h2>
            <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>所有还款</small>
        </div>
    </div>
</div>

<div class="content animate-panel">
<!--     <div class="row">

<g:form method="POST" action="searchBills">
    <div class="hpanel hblue">
        <div class="panel-heading">
            <div class="panel-tools">
                <button class="btn btn-primary btn-xs" type="submit"><i class="fa fa-search"></i>查询</button>
                <button class="btn btn-warning2 btn-xs" type="button" id="resetBtn"><i
                        class="fa fa-times"></i>重置</button>
            </div>
            查询
        </div>
        <input type="text" id="stage" name="stage" value="${this.stage?.code}" class="hide">
        <input type="text" id="status" name="status" value="${status}" class="hide">

        <div class="panel-body">
            <div class="col-sm-3">
                <input type="text" class="form-control" placeholder="订单号" id="Status" name="Status"
                       value="${params?.Status}">
            </div>
        </div>
    </div>
</g:form>
</div> -->

    <g:if test="${flash.message}">
        <div class="alert alert-success alert-dismissible" role="alert">
            ${flash.message}
        </div>
    </g:if>
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
<!--                 <div class="panel-tools">
    <g:link action="create" class="btn btn-info btn-xs"><i class="fa fa-edit"></i>新建</g:link>
    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
</div> -->
                客户还款计划列表
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                            <tr>
                                <g:sortableColumn property="status" title="订单号"/>
                                <g:sortableColumn property="status" title="借款人"/>
                                <g:sortableColumn property="status" title="借款日期" />
                                <g:sortableColumn property="status" title="还款日期"/>
                                <g:sortableColumn property="status" title="贷款利率" />
                                <g:sortableColumn property="status" title="贷款金额"/>
                            </tr>
                            </thead>
                        <tbody>
                        <g:each in="${billsList}">
                            <tr>
                                <td>
                                    <b><g:link action="show" id="${it?.id}">${it?.opportunity.serialNumber}</g:link></b>
                                </td>
                                <td>${it?.opportunity.fullName}</td>
                                <td><g:formatDate format="yyyy-MM-dd" date="${it?.opportunity.startTime}"/></td>
                                <td><g:formatDate format="yyyy-MM-dd" date="${it?.opportunity.endTime}"/></td>
                                <td>${it?.opportunity.monthlyInterest}</td>
                                <td>${it?.opportunity.loanAmount}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>
    </div>
    <g:javascript>
    $(function(){
        $("#resetBtn").click(function () {
            $("#name").val("");
        })
    });
</g:javascript>
</body>
</html>