<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'message.label', default: 'Message')}"/>
    <title>台账管理</title>
</head>

<body class="fixed-navbar fixed-sidebar">

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>台账管理</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                台账管理
            </h2>
            <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>订单阶段时间</small>
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
        <g:form name="form" method="POST" action="dailyReport1">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <button class="btn btn-primary btn-xs" type="button" id="search"><i class="fa fa-search"></i>查询
                        </button>
                        <button class="btn btn-warning2 btn-xs" type="button" id="resetBtn"><i
                                class="fa fa-times"></i>重置</button>
                        <button class="btn btn-primary2 btn-xs" type="button" id="download"><i
                                class="fa fa-download"></i>下载</button>
                    </div>
                    查询
                </div>

                <div class="panel-body">
                    <div id="reportStatus" style="display:none">
                        <input type="hidden" id="report" name="report">
                    </div>

                    <div class="col-sm-3">
                        <div class="input-group date form_datetime">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar"></span>
                            </span>
                            <input title="开始时间" type="text" name="startTime" id="startTime"
                                   placeholder="开始时间" value="${params?.startTime}" readonly
                                   class="form-control daily-b ">
                        </div>
                    </div>

                    <div class="col-sm-3">
                        <div class="input-group date form_datetime">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar"></span>
                            </span>
                            <input title="结束时间" type="text" name="endTime" id="endTime" placeholder="结束时间"
                                   value="${params?.endTime}" readonly class="form-control daily-b">
                        </div>
                    </div>
                    <sec:ifAnyGranted roles="ROLE_ADMINISTRATOR,ROLE_COO,ROLE_CEO,ROLE_CRO,ROLE_GENERAL_RISK_MANAGER">
                        <div class="col-sm-3">
                            <g:select class="form-control daily-b " name="city" id="city"
                                      from="${["--CITY--", "北京", "上海", "合肥", "南京", "成都", "青岛", "济南", "西安","郑州","石家庄","厦门","武汉"]}"
                                      valueMessagePrefix="stage" value="${params?.city}"/>
                        </div>
                    </sec:ifAnyGranted>
                </div>
            </div>
        </g:form>
    </div>

    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    %{-- <g:link action="create" class="btn btn-info btn-xs"><i class="fa fa-edit"></i>新建</g:link> --}%
                </div>
                订单阶段时间
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '订单号')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '客户姓名')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '订单创建时间')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '评房申请已提交')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '价格待确认')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '评房已完成')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '基础材料已提供')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '信息已完善')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '房产初审已完成')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '征信查询已完成')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '面谈已完成')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '复审已完成')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '贷款审批已完成（总部风控经理）')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '贷款审批已完成（风控总经理）')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '贷款审批已完成（业务总经理）')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '贷款审批已完成（CRO）')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '审批已完成')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '合同已签署')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '抵押公证手续已完成')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '复审已完成（放款）')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '放款审批已完成（总部风控经理）')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '放款审批已完成（风控总经理）')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '放款审批已完成（业务总经理）')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '放款审批已完成（CRO）')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '放款已完成')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '抵押品已入库')}"/>

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
                                <td>${it[7]}</td>
                                <td>${it[8]}</td>
                                <td>${it[9]}</td>
                                <td>${it[10]}</td>
                                <td>${it[11]}</td>
                                <td>${it[12]}</td>
                                <td>${it[13]}</td>
                                <td>${it[14]}</td>
                                <td>${it[15]}</td>
                                <td>${it[16]}</td>
                                <td>${it[17]}</td>
                                <td>${it[18]}</td>
                                <td>${it[19]}</td>
                                <td>${it[20]}</td>
                                <td>${it[21]}</td>
                                <td>${it[22]}</td>
                                <td>${it[23]}</td>
                                <td>${it[24]}</td>
                                <td>${it[25]}</td>
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
        $("#startTime").val("");
        $("#endTime").val("");
        $("#stage").val("--STATUS--");
        $("#city").val("--CITY--");
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
</g:javascript>
</body>
</html>