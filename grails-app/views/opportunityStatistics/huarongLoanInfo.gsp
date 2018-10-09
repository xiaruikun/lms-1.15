<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'message.label', default: 'Message')}"/>
    <title>华融审批列表</title>
</head>

<body class="fixed-navbar fixed-sidebar">

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>华融审批列表</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                华融审批列表
            </h2>
            <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span> 华融审批列表</small>
        </div>
    </div>
</div>

<div class="content animate-panel">

    <g:if test="${flash.message}">
        <div class="alert alert-success alert-dismissible" message="alert">
            ${flash.message}
        </div>
    </g:if>
    <div class="row">
        <g:form name="form" method="POST" action="huarongLoanInfo">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    <div class="panel-tools">
                        %{--<button class="btn btn-primary btn-xs" type="button" id="search"><i class="fa fa-search"></i>查询
                        </button>--}%
                        <button class="btn btn-primary btn-xs" type="submit"><i class="fa fa-search"></i>查询
                        </button>
                        <button class="btn btn-warning2 btn-xs" type="button" id="resetBtn"><i
                                class="fa fa-times"></i>重置</button>
                        %{--<button class="btn btn-primary2 btn-xs" type="button" id="download"><i
                                class="fa fa-download"></i>下载</button>--}%
                    </div>
                    查询
                </div>

                <div class="panel-body">
                    <div id="reportStatus" style="display:none">
                        <input type="hidden" id="report" name="report">
                    </div>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" placeholder="订单编号" id="serialNumber" name="serialNumber"
                               value="${params?.serialNumber}">
                    </div>

                    <div class="col-sm-3">
                        <input type="text" class="form-control" placeholder="借款人姓名" id="fullName" name="fullName"
                               value="${params?.fullName}">
                    </div>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" placeholder="合同号" id="contract" name="contract"
                               value="${params?.contract}">
                    </div>
                    %{--<div class="col-sm-3">
                        <div class="input-group date form_datetime">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar"></span>
                            </span>
                            <input title="开始时间" type="text" name="startTime" id="startTime"
                                   placeholder="开始时间" value="${params?.startTime}" readonly
                                   class="form-control daily-b ">
                        </div>
                    </div>--}%

                    %{--<div class="col-sm-3">
                        <div class="input-group date form_datetime">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar"></span>
                            </span>
                            <input title="结束时间" type="text" name="endTime" id="endTime" placeholder="结束时间"
                                   value="${params?.endTime}" readonly class="form-control daily-b">
                        </div>

                    </div>--}%
                </div>
            </div>
        </g:form>
    </div>

    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                华融审批列表
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
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${list}">
                            <tr>
                                <td><g:link controller="opportunityStatistics" action="show28" id="${com.next.Opportunity.findBySerialNumber(it[0])?.id}">${it[0]}</g:link></td>
                                <td>${it[1]}</td>
                                <td>${it[2]}</td>
                                <td>${it[3]}</td>
                                <td>${it[4]}</td>
                                <td>${it[5]}</td>
                                <td>${it[6]}</td>
                                <td>${it[7]}</td>
                                <td><g:formatNumber number="${it[8]}" minFractionDigits="2" maxFractionDigits="5"/></td>
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
        $("#serialNumber").val("");
        $("#contract").val("");
        $("#fullName").val("");
        $("#reportStatus").css("display", "none")
        $("#report").val("");
    });
    $("#search").click(function () {
        $("#reportStatus").css("display", "none")
        $("#report").val("");
        $("#form").submit()
    });
    $("#download").click(function () {
        $("#reportStatus").css("display", "block")
        $("#report").val("yes");
        $("#form").submit()
    });
    %{--$("body").keydown(function() {
        if(event.keyCode==13){
            ${'#form'}.form.submit();
        }
    });--}%
</g:javascript>
</body>
</html>