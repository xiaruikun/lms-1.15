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
            <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>订单信息统计</small>
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
        <g:form method="POST" action="dailyReportCount">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <button class="btn btn-primary btn-xs" type="submit"><i class="fa fa-search"></i>查询</button>
                        <button class="btn btn-warning2 btn-xs" type="button" id="resetBtn"><i
                                class="fa fa-times"></i>重置</button>
                    </div>
                    查询
                </div>

                <div class="panel-body">
                    <div class="col-sm-3">
                        <div class="input-group date form_datetime">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar"></span>
                            </span>
                            <input title="开始时间" type="text" name="startTime" id="startTime"
                                   placeholder="2016-08-01 00:00:00" value="${params?.startTime}" readonly
                                   class="form-control daily-b">
                        </div>
                    </div>

                    <div class="col-sm-3">
                        <div class="input-group date form_datetime">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar"></span>
                            </span>
                            <input title="结束时间" type="text" name="endTime" id="endTime"
                                   placeholder="2016-10-20 23:59:59" value="${params?.endTime}" readonly
                                   class="form-control daily-b">
                        </div>
                    </div>
                </div>
            </div>
        </g:form>
    </div>

    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                询值基础信息统计
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '合同编号')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '报单日期')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '批贷日期')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '签合同日期')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '公证日期')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '抵押登记日期')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '回他项日期')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '放款日期')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '业务来源(北京区/渠道拓展/机构)')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '渠道名称')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '组别')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '销售员')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '家访日期')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '客服专员')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '风控主单员')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '家访人员')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '客户资质分类')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '借款人')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '借款人联系方式')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '批贷金额')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '放款金额')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '贷款期限(月)')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '放款路径')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '抵押(抵押信托/抵押凯盛)')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '产品类别(一押/二押)')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '月基础利率')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '月服务费率')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '综合月利率')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '代收代返费率')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '房产地址')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${map.list}">
                            <tr>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${map.size}" params="${params}"/>
                </div>
            </div>
        </div>
    </div>

</div>
<g:javascript>
    $("#resetBtn").click(function () {
        $("#startTime").val("");
        $("#endTime").val("");
    });
</g:javascript>
</body>
</html>