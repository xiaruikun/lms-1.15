<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'message.label', default: 'Message')}"/>
    <title>交易流水</title>
</head>

<body class="fixed-navbar fixed-sidebar">

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>交易查询</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                交易查询
            </h2>
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
        <g:form name="form" method="POST" action="search">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <button class="btn btn-primary btn-xs" type="button" id="search"><i class="fa fa-search"></i>查询
                        </button>
                        <button class="btn btn-warning2 btn-xs" type="button" id="resetBtn"><i
                                class="fa fa-times"></i>重置</button>
                        %{--<button class="btn btn-primary2 btn-xs" type="button" id="download1"><i--}%
                                %{--class="fa fa-download"></i>导出中航信托</button>--}%
                        %{--<button class="btn btn-primary2 btn-xs" type="button" id="download2"><i--}%
                                %{--class="fa fa-download"></i>导出渤海一期</button>--}%
                        %{--<button class="btn btn-primary2 btn-xs" type="button" id="download3"><i--}%
                                %{--class="fa fa-download"></i>导出渤海二期</button>--}%
                        <button class="btn btn-primary2 btn-xs" type="button" id="download4"><i
                                class="fa fa-download"></i>导出交易记录费率拆分</button>
                    </div>
                    查询
                </div>

                <div class="panel-body">
                    <div id="reportStatus" style="display:none">
                        <input type="hidden" id="report" name="report">
                    </div>
                    <div class="col-sm-3">
                        <input title="订单号" type="text" name="serialNumber" id="serialNumber" placeholder="请填写订单号"
                               value="${params?.serialNumber}" class="form-control">
                    </div>

                    <div class="col-sm-3">
                        <input title="借款人姓名" type="text" name="fullName" id="fullName" placeholder="请填写借款人姓名"
                               value="${params?.fullName}" class="form-control">
                    </div>

                    <div class="col-sm-3">
                        <g:select class="form-control" name="type" id="type"
                                  from="${[ "广银联代扣", "富友代扣","自主还款"]}"
                                  valueMessagePrefix="type" value="${params?.type}"  noSelection="${['null': '请选择代扣方式']}"/>
                    </div>





                    <div class="col-sm-3">
                        <g:select class="form-control" name="status" id="status"
                                  from="${[ "未执行", "已成功","扣款失败","已失效","待确认"]}"
                                  valueMessagePrefix="type" value="${params?.status}"  noSelection="${['null': '请选择交易状态']}"/>
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

            </div>
            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '订单号')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '交易类型')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '金额（万元）')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '借方账户名')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '借方卡号')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '贷方账户名')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '贷方卡号')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '预计开始时间')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '交易完成时间')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '清分账号')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '交易状态')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '交易失败原因')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '创建时间')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '修改时间')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '付款方式')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '操作')}"/>

                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${list}">
                            <tr>

                                <td><g:link controller="opportunity"  action="show" id = "${it.opportunity?.id}">${it.opportunity?.serialNumber}</g:link></td>
                                <td>${it.type?.name}</td>
                                <td>${it.amount}</td>
                                <td>${it.debit?.name}</td>
                                <td>${it.debit?.numberOfAccount}</td>
                                <td>${it.credit?.name}</td>
                                <td>${it.credit?.numberOfAccount}</td>
                                <td>${it.plannedStartTime}</td>
                                <td>${it.endTime}</td>
                                <td>${it.debitAccount}</td>
                                <td>${it.status?.name}</td>
                                <td>${it.memo}</td>
                                <td>${it.createdDate}</td>
                                <td>${it.modifiedDate}</td>
                                <td>${it.repaymentMethod.name}</td>
                            <td>
                            <g:if test="${it.repaymentMethod.name == "富友代扣" && (it.status.name == "未执行" || it.status.name == "已失败" )}">
                                    <g:form resource="${it}" method="POST" action="fuiouPayment">
                                        <fieldset class="buttons">
                                            <input class="" type="submit" value="支付" onclick="return confirm('确认支付？');" />
                                        </fieldset>
                                        <input type="hidden" name="offset" value="${params.offset}">
                                    </g:form>
                                </g:if>
                            </td>
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
        $("#startDate").val("");
        $("#endDate").val("");
        $("#stage").val("");
        $("#reportStatus").css("display", "none")
        $("#report").val("");
    });
    $("#search").click(function () {
       /* if($("#startDate").val()=="" ){
            alert ("请选择开始时间")
            return
        }
        if($("#endDate").val()==""){
            alert ("请选择结束时间")
            return
        }*/
        /*if($("#stage").val()==""|| $("#stage").val()=="null" ){
            alert ("请选择订单阶段")
            return
        }*/
        $("#reportStatus").css("display", "none")
        $("#report").val("");
        $("#form").submit()
    });
    $("#download1").click(function () {
        $("#reportStatus").css("display", "block")
        $("#report").val("yes1");
        $("#form").submit()
    });
    $("#download2").click(function () {
        $("#reportStatus").css("display", "block")
        $("#report").val("yes2");
        $("#form").submit()
    });
    $("#download3").click(function () {
        $("#reportStatus").css("display", "block")
        $("#report").val("yes3");
        $("#form").submit()
    });
    $("#download4").click(function () {
        $("#reportStatus").css("display", "block")
        $("#report").val("yes4");
        $("#form").submit()
    });
</g:javascript>
</body>
</html>