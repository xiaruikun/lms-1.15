<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'message.label', default: 'Message')}"/>
    <title>贷后-放款台账</title>
</head>

<body class="fixed-navbar fixed-sidebar">

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>放款订单查询</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                贷后台账
            </h2>
            <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span> 放款台账</small>
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
        <g:form name="form" method="POST" action="loanReport">
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
                    <sec:ifAnyGranted roles="ROLE_ADMINISTRATOR,ROLE_COO">
                        <div class="col-md-3">
                            <g:select class="form-control" name="city" id="city"
                                      from="${com.next.City.list()}"
                                      valueMessagePrefix="stage" optionKey="name" optionValue="name" value="${params?.city}"
                                      noSelection="${['null': '请选择城市']}"/>
                        </div>
                    </sec:ifAnyGranted>

                    <div class="col-md-3">
                        <g:select class="form-control" name="product" id="product"
                                  from="${com.next.Product.list()}"
                                  valueMessagePrefix="stage" optionKey="name" optionValue="name" value="${params?.product}"
                                  noSelection="${['null': '请选择产品']}"/>
                    </div>
                    <div class="col-md-3">
                        <input title="借款人姓名" type="text" name="fullName" id="fullName" placeholder="请填写主借款人姓名"
                               value="${params?.fullName}" class="form-control">
                    </div>

                    <div class="col-md-3">
                        <input title="借款合同编号" type="text" name="contract" id="contract" placeholder="请填写借款合同编号"
                               value="${params?.contract}" class="form-control">
                    </div>

                    <div class="col-md-3">
                        <div class="input-group date form_datetime3">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar"></span>
                            </span>
                            <input title="放款日期" type="text" name="loanDate" id="loanDate"
                                   placeholder="放款日期" value="${params?.loanDate}"
                                   class="form-control daily-b ">
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="input-group date form_datetime3">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar"></span>
                            </span>
                            <input title="到期日期" type="text" name="endDate" id="endDate"
                                   placeholder="到期日期" value="${params?.endDate}"
                                   class="form-control daily-b ">
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="input-group date form_datetime3">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar"></span>
                            </span>
                            <input title="结清日期" type="text" name="jqDate" id="jqDate"
                                   placeholder="结清日期" value="${params?.jqDate}"
                                   class="form-control daily-b ">
                        </div>
                    </div>
                    <div class="col-md-3">
                        <g:select class="form-control" name="jqStatus" id="jqStatus"
                                  from="${["手动结清","自动结清"]}"
                                   value="${params?.jqStatus}"
                                  noSelection="${['null': '请选择结清状态']}"/>
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
                放款台账
            </div>
            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:each in="${listTitle}">
                                <g:sortableColumn property="authority"
                                                  title="${it}"/>
                            </g:each>

                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${list}">
                            <tr>
                                <td><g:link controller="opportunity"  action="show" id = "${com.next.Opportunity.findBySerialNumber(it[0])?.id}">${it[0]}</g:link></td>
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
        $("#loanDate").val("");
        $("#endDate").val("");
        $("#fullName").val("");
        $("#contract").val("");
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