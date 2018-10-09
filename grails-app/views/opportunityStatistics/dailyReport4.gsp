<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'message.label', default: 'Message')}"/>
    <title>北京区审批台账导出</title>
</head>

<body class="fixed-navbar fixed-sidebar">

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>订单查询</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                订单查询
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
        <g:form name="form" method="POST" action="dailyReport4">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <button class="btn btn-primary btn-xs" type="button" id="search"><i class="fa fa-search"></i>查询
                        </button>
                        <button class="btn btn-warning2 btn-xs" type="button" id="resetBtn"><i
                                class="fa fa-times"></i>重置</button>
                        <button class="btn btn-primary2 btn-xs" type="button" id="download"><i
                                class="fa fa-download"></i>导出</button>
                    </div>
                    查询
                </div>
                <div class="panel-body">
                <div id="reportStatus" style="display:none">
                    <input type="hidden" id="report" name="report">
                </div>


                    <div class="col-sm-3">
                        <div class="input-group date form_datetime2">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar"></span>
                            </span>
                            <input title="开始时间" type="text" name="startDate" id="startDate"
                                   placeholder="开始时间" value="${params?.startDate}"
                                   class="form-control daily-b ">
                        </div>
                    </div>

                    <div class="col-sm-3">
                        <div class="input-group date form_endtime">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar"></span>
                            </span>
                            <input title="结束时间" type="text" name="endDate" id="endDate"
                                   placeholder="结束时间" value="${params?.endDate}"
                                   class="form-control daily-b ">
                        </div>
                    </div>
                    <sec:ifAnyGranted roles="ROLE_ADMINISTRATOR,ROLE_COO,ROLE_CEO,ROLE_CRO,ROLE_GENERAL_RISK_MANAGER">
                        <div class="col-sm-3">
                            <g:select class="form-control daily-b " name="city" id="city"
                                      from="${["--CITY--", "北京", "上海", "合肥", "南京", "成都", "青岛", "济南", "西安","郑州","石家庄","厦门","武汉"]}"
                                      valueMessagePrefix="stage" value="${params?.city}"/>
                        </div>
                    </sec:ifAnyGranted>

                <div class="col-sm-3">
                    <g:select class="form-control" name="stage" id="stage"
                              from="${[ "批贷已完成", "放款已完成"]}"
                              valueMessagePrefix="stage" value="${params?.stage}"  noSelection="${['null': '请选择订单阶段']}"/>
                </div>
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
<g:if test="${params.stageID =="8"}">
            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '订单号')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '签约日期')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '贷款金额（万元）')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '销售员')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '委托协议编号')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '贷款类型')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '客户评级')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '抵押类型')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '借款人姓名')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '区域')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '房屋地址')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '客户来源')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '渠道明细')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '销售组别')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '月息')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '借款期限（月）')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '渠道服务费')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '借款服务费')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '扣息方式')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '上扣息月数')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${list}">
                            <tr>
                                <td><g:link controller="opportunity" action="show" id="${com.next.Opportunity.findBySerialNumber(it[0])?.id}" class="firstTd">${it[0]}</g:link></td>
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
                                <td><g:formatNumber number="${it[14]}" minFractionDigits="2"
                                                    maxFractionDigits="4"/></td>
                                <td>${it[15]}</td>
                                <td><g:formatNumber number="${it[16]}" minFractionDigits="2"
                                                    maxFractionDigits="4"/></td>
                                <td><g:formatNumber number="${it[17]}" minFractionDigits="2"
                                                    maxFractionDigits="4"/></td>
                                <td>${it[18]}</td>
                                <td>${it[19]}</td>

                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
</g:if>
<g:if test="${params.stageID =="10"}">
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '订单号')}"/>
                    <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '协议编号')}"/>
                    <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '签约日期')}"/>
                    <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '产品品种')}"/>
                    <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '客户评级')}"/>
                    <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '抵押类型')}"/>
                    <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '业务来源')}"/>
                    <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '借款人姓名')}"/>
                    <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '区域')}"/>
                    <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '物业区域')}"/>
                    <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '物业位置')}"/>
                    <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '申请贷款金额')}"/>
                    <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '经办人')}"/>
                    <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '销售组别')}"/>
                    <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '销售员')}"/>
                    <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '批贷日期')}"/>
                    <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '批贷金额')}"/>
                    <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '放款日期/缴息费日期')}"/>
                    <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '放款金额（万）')}"/>
                    <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '客户来源')}"/>
                    <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '渠道明细')}"/>
                    <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '月息')}"/>
                    <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '借款期限(月)')}"/>
                    <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '渠道服务费')}"/>
                    <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '借款服务费')}"/>
                    <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '扣息方式')}"/>
                    <g:sortableColumn property="authority"
                                      title="${message(code: 'message.authority.label', default: '上扣息月数')}"/>

                </tr>
                </thead>
                <tbody>
                <g:each in="${list}">
                    <tr>
                        <td><g:link controller="opportunity" action="show" id="${com.next.Opportunity.findBySerialNumber(it[0])?.id}" class="firstTd">${it[0]}</g:link></td>
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
                        <td><g:formatNumber number="${it[21]}" minFractionDigits="2"
                                                              maxFractionDigits="4"/></td>
                        <td>${it[22]}</td>
                        <td><g:formatNumber number="${it[23]}" minFractionDigits="2"
                                            maxFractionDigits="4"/></td>
                        <td><g:formatNumber number="${it[24]}" minFractionDigits="2"
                                            maxFractionDigits="4"/></td>

                        <td>${it[25]}</td>
                        <td>${it[26]}</td>



                    </tr>
                </g:each>
                </tbody>
            </table>
        </div>
    </div>
    </g:if>

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
        $("#startDate").val("");
        $("#endDate").val("");
        $("#stage").val("");
        $("#reportStatus").css("display", "none")
        $("#report").val("");
    });
    $("#search").click(function () {
        if($("#startDate").val()=="" ){
            alert ("请选择开始时间")
            return
        }
        if($("#endDate").val()==""){
            alert ("请选择结束时间")
            return
        }
        if($("#stage").val()==""|| $("#stage").val()=="null" ){
            alert ("请选择订单阶段")
            return
        }
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