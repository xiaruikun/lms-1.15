<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'message.label', default: 'Message')}"/>
    <title>今日二批次放款列表</title>
</head>

<body class="fixed-navbar fixed-sidebar">

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>今日二批次放款列表</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                今日二批次放款列表
            </h2>
            <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>今日二批次放款列表</small>
        </div>
    </div>
</div>

<div class="content animate-panel">

    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:form name="form" method="POST" action="exportLoanInfo2">
                        <div id="reportStatus" style="display:none">
                            <input type="hidden" id="report" name="report">
                            <input type="hidden" id="flag" name="flag">
                        </div>
                        <button class="btn btn-primary2 btn-xs" type="button" id="download"><i class="fa fa-download"></i>下载</button>
                    </g:form>
                </div>
                今日二批次放款列表
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '订单号')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '放款账号确定时间')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '城市')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '放款账户')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '合同号')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '借款人姓名')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '放款金额(万元)')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '放款期限')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '月利率')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '月服务费比率')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '一次性服务费比率')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '月返费比率')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '一次性返费比率')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${list}">
                            <tr>
                                <td><g:link controller="opportunity" action="show" id="${com.next.Opportunity.findBySerialNumber(it[0])?.id}">${it[0]}</g:link></td>
                                <td>${it[1]}</td>
                                <td>${it[2]}</td>
                                <td>${it[3]}</td>
                                <td>${it[4]}</td>
                                <td>${it[5]}</td>
                                <td>${it[6]}</td>
                                <td>${it[7]}</td>
                                <td><g:formatNumber number="${it[8]}" minFractionDigits="2" maxFractionDigits="5"/></td>
                                <td><g:formatNumber number="${it[9]}" minFractionDigits="2" maxFractionDigits="5"/></td>
                                <td><g:formatNumber number="${it[10]}" minFractionDigits="2" maxFractionDigits="5"/></td>
                                <td><g:formatNumber number="${it[11]}" minFractionDigits="2" maxFractionDigits="5"/></td>
                                <td><g:formatNumber number="${it[12]}" minFractionDigits="2" maxFractionDigits="5"/></td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${count ?: 0}" params="${params}"/>
                </div>
            </div>
        </div>
    </div>

</div>
<g:javascript>
    $("#resetBtn").click(function () {
        $("#flag").val("PM");
        $("#reportStatus").css("display", "none")
        $("#report").val("");
    });
    $("#search").click(function () {
        $("#reportStatus").css("display", "none")
        $("#report").val("");
        $("#flag").val("PM");
        $("#form").submit()
    });
    $("#download").click(function () {
        $("#reportStatus").css("display", "block")
        $("#report").val("yes");
        $("#flag").val("PM");
        $("#form").submit()
    });
</g:javascript>
</body>
</html>