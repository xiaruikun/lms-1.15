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
            <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span> 签约台账</small>
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
        <g:form name="form" method="POST" action="dailyReport7">
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
                </div>
                签约台账
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '合同编号')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '订单编号')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '产品品种')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '客户评级')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '抵押类型')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '借款人姓名')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '借款及抵押人年龄/周岁')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '抵押物区域')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '房屋地址')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '房屋立项')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '房屋建筑面积')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '抵押物房龄')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '合计抵押率')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '风控员')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '销售组别')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '销售员')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '批贷日期')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '批贷金额（万元）')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '渠道姓名/公司名称')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '基本费率%')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '服务费费率%')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '渠道返费费率%')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '二抵加收费率%')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '信用调整%')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '综合息费%')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '借款期限(月)')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '返费说明')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '收息方式')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '是否退单')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '退单原因')}"/>
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
                                <td><g:formatNumber number="${it[12]}" maxFractionDigits="2"/></td>
                                <td>${it[13]}</td>
                                <td>${it[14]}</td>
                                <td>${it[15]}</td>
                                <td>${it[16]}</td>
                                <td>${it[17]}</td>
                                <td>${it[18]}</td>
                                <td><g:formatNumber number="${it[19]}" maxFractionDigits="2"/></td>
                                <td><g:formatNumber number="${it[20]}" maxFractionDigits="2"/></td>
                                <td><g:formatNumber number="${it[21]}" maxFractionDigits="2"/></td>
                                <td><g:formatNumber number="${it[22]}" maxFractionDigits="2"/></td>
                                <td><g:formatNumber number="${it[23]}" maxFractionDigits="2"/></td>
                                <td><g:formatNumber number="${it[24]}" maxFractionDigits="2"/></td>
                                <td>${it[25]}</td>
                                <td>${it[26]}</td>
                                <td>${it[27]}</td>
                                <td>${it[28]}</td>
                                <td>${it[29]}</td>
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