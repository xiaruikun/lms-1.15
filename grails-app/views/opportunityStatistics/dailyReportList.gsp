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
            <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span> 所有询值信息</small>
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
        <g:form name="form" method="POST" action="dailyReportList">
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

                    <div class="col-sm-3">
                        <select class="form-control" name="stage" id="stage" style="cursor: pointer" title="订单阶段选择">
                            <g:if test="${params?.stage}">
                                <option>${params?.stage}</option>
                            </g:if>
                            <option>--STATUS--</option>
                            <option>询值记录</option>
                            <option>报单记录</option>
                            <g:each in="${stageList}">
                                <option>${it.name}</option>
                            </g:each>
                        </select>
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
                全部询值信息
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '订单号')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '销售员')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '客户姓名')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '经纪人姓名')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '组别')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '抵押类别')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '房屋信息')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '申请金额')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '客户要求')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '报单规模')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '产品类型')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '订单状态')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '失败原因')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '失败详情分析')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '成交规模')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '订单日期')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${list}">
                            <tr>
                                <td><g:link action="edit" id="${it[18]}" controller="opportunityStatistics"
                                            class="btn btn-info btn-xs"><i
                                            class="fa fa-edit"></i>&nbsp;${it[0]}</g:link></td>
                                <td>${it[1]}</td>
                                <td>${it[2]}</td>
                                <td>${it[3]}</td>
                                <td>${it[4]}</td>
                                <td>${it[16]}</td>
                                <td>${it[5] + it[6] + ";" + it[7]}</td>
                                <td><g:formatNumber number="${it[8]}" maxFractionDigits="2"/></td>
                                <td><g:formatNumber number="${it[9]}" maxFractionDigits="2"/></td>
                                <td><g:formatNumber number="${it[10]}" maxFractionDigits="2"/></td>
                                <td>${it[11]}</td>
                                <td>${it[12]}</td>
                                <td>${it[13]}</td>
                                <td>${it[17]}</td>
                                <td><g:formatNumber number="${it[14]}" maxFractionDigits="2"/></td>
                                <td>${it[15]}</td>
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