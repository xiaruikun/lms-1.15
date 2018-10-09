<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'message.label', default: 'Message')}"/>
    <title>资金部查询</title>
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
            <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span> 所有查询信息</small>
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
        <g:form name="form" method="POST" action="dailyReport5">
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

                <div class="panel-body seach-group">
                    <div id="reportStatus" style="display:none">
                        <input type="hidden" id="report" name="report">
                    </div>
                    <div class="col-md-3">
                            <input title="订单号" type="text" name="serialNumber" id="serialNumber" placeholder="请填写订单号"
                                   value="${params?.serialNumber}" class="form-control">
                    </div>
                    <div class="col-md-3">
                        <g:select class="form-control" name="city" id="city"
                                  from="${com.next.City.list()}"
                                  valueMessagePrefix="stage" optionKey="name" optionValue="name" value="${params?.city}"
                                  noSelection="${['null': '请选择城市']}"/>
                    </div>
                    <div class="col-md-3">
                        <g:select class="form-control" name="product" id="product"
                                  from="${com.next.Product.findAllByActive(true)}"
                                  valueMessagePrefix="stage" optionKey="name" optionValue="name" value="${params?.product}"
                                  noSelection="${['null': '请选择产品类型']}"/>
                    </div>
                    <div class="col-md-3">
                        <input title="借款人姓名" type="text" name="fullName" id="fullName" placeholder="请填写借款人姓名"
                               value="${params?.fullName}" class="form-control">
                    </div>
                    <div class="col-md-3">
                        <div class="input-group date form_datetime2">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar"></span>
                            </span>
                            <input title="预计放款日期" type="text" name="estimatedLendingDate" id="estimatedLendingDate"
                                   value="${params?.estimatedLendingDate}" readonly
                                   class="form-control daily-b " placeholder="预计放款日期">
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="input-group date form_datetime2">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar"></span>
                            </span>
                            <input title="实际放款日期" type="text" name="loanDate" id="loanDate" readonly
                                   value="${params?.loanDate}"
                                   class="form-control daily-b " placeholder="实际放款日期">
                        </div>
                    </div>
                    <div class="col-md-3">
                        <g:select class="form-control" name="loanDoc" id="loanDoc"
                                  from="${com.next.MortgageCertificateType.list()}"
                                  valueMessagePrefix="stage" optionKey="name" optionValue="name" value="${params?.loanDoc}"
                                  noSelection="${['null': '请选择抵押凭证类型']}"/>
                    </div>
                    <div class="col-md-3">
                        <div class="input-group date form_datetime2">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar"></span>
                            </span>
                            <input title="确认通道日期" type="text" name="passagewayDate" id="passagewayDate" readonly
                                   value="${params?.passagewayDate}"
                                   class="form-control daily-b " placeholder="确认通道日期">
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="input-group date form_datetime2">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar"></span>
                            </span>
                            <input title="确认路径日期" type="text" name="pathDate" id="pathDate" readonly
                                   value="${params?.pathDate}"
                                   class="form-control daily-b " placeholder="确认路径日期">
                        </div>
                    </div>
                    <div class="col-md-3">
                        <g:select class="form-control" name="flexField" id="flexField"
                                  from="${com.next.FlexFieldValue.findAllByField(com.next.FlexField.findByNameAndCategory("放款通道", com.next.FlexFieldCategory.findByName("资金渠道")))}"
                                  valueMessagePrefix="stage" optionKey="value" optionValue="value" value="${params?.flexField}"
                                  noSelection="${['null': '请选择放款通道']}"/>
                    </div>

                    <div class="col-md-3">
                        <g:select class="form-control" name="flexFieldBankAccount" id="flexFieldBankAccount"
                                  from="${com.next.FlexFieldValue.findAll("from FlexFieldValue where field.id = ? order by displayOrder asc",[com.next.FlexField.findByNameAndCategory('放款账号', com.next.FlexFieldCategory.findByName('资金渠道'))?.id])}"
                                  valueMessagePrefix="stage" optionKey="value" optionValue="value" value="${params?.flexFieldBankAccount}"
                                  noSelection="${['null': '请选择放款账号']}"/>
                    </div>
                    <!-- <div class="col-md-3">
                        <div class="input-group date form_datetime2">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar"></span>
                            </span>
                            <input title="放款完成时间" type="text" name="lastDate" id="lastDate" readonly
                                   value="${params?.lastDate}" 
                                   class="form-control daily-b " placeholder="放款完成时间">
                        </div>
                    </div> -->
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
                全部查询信息
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '订单号')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '城市')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '产品类型')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '放款通道')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '放款账户')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '共同借款人')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '批贷金额')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '放款金额(元)')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '借款期限（月）')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '预计放款日期')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '实际放款日期')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '订单阶段')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '放款凭证')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '确认通道时间')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '确认路径时间')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '路径失效时间')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '路径有效性(是/否)')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '销售主管')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '销售专员')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '风控主单员')}"/>
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
        $("#pathDate").val("");
        $("#loanDate").val("");
        $("#lastDate").val("");
        $("#serialNumber").val("");
        $("#fullName").val("");
        $("#reportStatus").css("display", "none")
        $("#report").val("");
        $("#passagewayDate").val("");
        $("#estimatedLendingDate").val("");
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
    $('.form_datetime3').datetimepicker({
        minView: "month", //选择日期后，不会再跳转去选择时分秒
        format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
        language: 'zh-CN', //汉化
        autoclose: true //选择日期后自动关闭
    });
</g:javascript>
</body>
</html>