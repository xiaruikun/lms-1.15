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
            <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>询值信息统计</small>
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

                    <div class="col-sm-3">
                        <g:select class="form-control" name="stage" id="stage"
                                  from="${["--STATUS--", "评房"]}"
                                  valueMessagePrefix="stage" value="${params?.stage}"/>
                    </div>
                    <sec:ifAnyGranted roles="ROLE_ADMINISTRATOR">
                        <div class="col-sm-3">
                            <g:select class="form-control" name="city" id="city"
                                      from="${["--CITY--", "北京", "上海", "合肥", "南京", "成都", "青岛", "济南", "西安"]}"
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
                询值基础信息统计
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="authority" rowspan="2"
                                              title="${message(code: 'message.authority.label', default: '销售人员')}"/>
                            <g:sortableColumn property="authority" colspan="5"
                                              title="${message(code: 'message.authority.label', default: '批贷规模(万)')}"/>
                            <g:sortableColumn property="authority" colspan="3"
                                              title="${message(code: 'message.authority.label', default: '放款规模(万)')}"/>
                            <g:sortableColumn property="authority" rowspan="2"
                                              title="${message(code: 'message.authority.label', default: '面谈规模(万)')}"/>
                            <g:sortableColumn property="authority" rowspan="2"
                                              title="${message(code: 'message.authority.label', default: '报单规模(万)')}"/>
                            <g:sortableColumn property="authority" rowspan="2"
                                              title="${message(code: 'message.authority.label', default: '客户要求规模(万)')}"/>
                            <g:sortableColumn property="authority" colspan="3"
                                              title="${message(code: 'message.authority.label', default: '放款笔数')}"/>
                            <g:sortableColumn property="authority" colspan="5"
                                              title="${message(code: 'message.authority.label', default: '批贷笔数')}"/>
                            <g:sortableColumn property="authority" rowspan="2"
                                              title="${message(code: 'message.authority.label', default: '面谈笔数')}"/>
                            <g:sortableColumn property="authority" rowspan="2"
                                              title="${message(code: 'message.authority.label', default: '报单笔数')}"/>
                            <g:sortableColumn property="authority" rowspan="2"
                                              title="${message(code: 'message.authority.label', default: '询值笔数')}"/>
                        </tr>
                        <tr>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '速e贷')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '融e贷')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '楼e贷&房e贷')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '正常展期')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '不良展期')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '速e贷')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '融e贷')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '楼e贷&房e贷')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '速e贷')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '融e贷')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '楼e贷&房e贷')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '速e贷')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '融e贷')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '楼e贷&房e贷')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '正常展期')}"/>
                            <g:sortableColumn property="authority"
                                              title="${message(code: 'message.authority.label', default: '不良展期')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${map.list}">
                            <tr>
                                <sec:ifAnyGranted roles="ROLE_BRANCH_GENERAL_MANAGER">
                                    <td><g:link action="show" id="${it.userId}" controller="user">${it.fullName}</g:link></td>
                                </sec:ifAnyGranted>
                                <sec:ifNotGranted roles="ROLE_BRANCH_GENERAL_MANAGER">
                                    <td>${it.fullName}</td>
                                </sec:ifNotGranted>
                                <td><g:formatNumber number="${it.sumActualAmountOfCredit2}" maxFractionDigits="2"/></td>
                                <td><g:formatNumber number="${it.sumActualAmountOfCredit1}" maxFractionDigits="2"/></td>
                                <td><g:formatNumber number="0" maxFractionDigits="2"/></td>
                                <td><g:formatNumber number="0" maxFractionDigits="2"/></td>
                                <td><g:formatNumber number="0" maxFractionDigits="2"/></td>
                                <td><g:formatNumber number="${it.sumActualAmountOfCredit2}" maxFractionDigits="2"/></td>
                                <td><g:formatNumber number="${it.sumActualAmountOfCredit1}" maxFractionDigits="2"/></td>
                                <td><g:formatNumber number="0" maxFractionDigits="2"/></td>
                                <td><g:formatNumber number="${it.sumActualAmountOfCredit}" maxFractionDigits="2"/></td>
                                <td><g:formatNumber number="${it.sumAppliedTotalPrice}" maxFractionDigits="2"/></td>
                                <td><g:formatNumber number="${it.sumRequestedAmount}" maxFractionDigits="2"/></td>
                                <td>${it.countActualAmountOfCredit2}</td>
                                <td>${it.countActualAmountOfCredit1}</td>
                                <td>0</td>
                                <td>${it.countActualAmountOfCredit2}</td>
                                <td>${it.countActualAmountOfCredit1}</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>${it.countActualAmountOfCredit}</td>
                                <td>${it.countAppliedTotalPrice}</td>
                                <td>${it.countRequestedAmount}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${map.listSize}" params="${params}"/>
                </div>
            </div>
        </div>
    </div>

    %{--<div class="row">
        <div class="hpanel hgreen">
        <div class="panel-heading">
            询值基础信息统计
        </div>

        <div class="panel-body">
            <div class="table-responsive">
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <g:sortableColumn property="authority" rowspan="2" title="${message(code: 'message.authority.label', default: '销售人员')}"/>
                        <g:sortableColumn property="authority" rowspan="2" title="${message(code: 'message.authority.label', default: '面谈规模(万)')}"/>
                        <g:sortableColumn property="authority" rowspan="2" title="${message(code: 'message.authority.label', default: '报单规模(万)')}"/>
                        <g:sortableColumn property="authority" rowspan="2" title="${message(code: 'message.authority.label', default: '客户要求规模(万)')}"/>
                        <g:sortableColumn property="authority" rowspan="2" title="${message(code: 'message.authority.label', default: '面谈笔数')}"/>
                        <g:sortableColumn property="authority" rowspan="2" title="${message(code: 'message.authority.label', default: '报单笔数')}"/>
                        <g:sortableColumn property="authority" rowspan="2" title="${message(code: 'message.authority.label', default: '询值笔数')}"/>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${map.list}">
                        <tr>
                            <td>${it[0]}</td>
                            <td><g:formatNumber number="${it[1]}" maxFractionDigits="2"/></td>
                            <td><g:formatNumber number="${it[2]}" maxFractionDigits="2"/></td>
                            <td><g:formatNumber number="${it[3]}" maxFractionDigits="2"/></td>
                            <td><g:formatNumber number="${it[4]}" maxFractionDigits="0"/></td>
                            <td><g:formatNumber number="${it[5]}" maxFractionDigits="0"/></td>
                            <td><g:formatNumber number="${it[6]}" maxFractionDigits="0"/></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="panel-footer">
            <div class="pagination">
                <g:paginate total="${map.count}" params="${params}"/>
            </div>
        </div>
    </div>
        <div class="hpanel hgreen">
            <div class="panel-heading">
                批贷信息统计
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="authority" rowspan="2" title="${message(code: 'message.authority.label', default: '销售人员')}"/>
                            <g:sortableColumn property="authority" colspan="2" title="${message(code: 'message.authority.label', default: '融e贷')}"/>
                            <g:sortableColumn property="authority" colspan="2" title="${message(code: 'message.authority.label', default: '融e贷')}"/>
                        </tr>
                        <tr>
                            <g:sortableColumn property="authority" title="${message(code: 'message.authority.label', default: '批贷规模(万)')}"/>
                            <g:sortableColumn property="authority" title="${message(code: 'message.authority.label', default: '放款规模(万)')}"/>
                            <g:sortableColumn property="authority" title="${message(code: 'message.authority.label', default: '批贷笔数')}"/>
                            <g:sortableColumn property="authority" title="${message(code: 'message.authority.label', default: '放款笔数')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${map.list1}">
                            <tr>
                                <td>${it[0]}</td>
                                <td>${it[1]}</td>
                                <td>${it[2]}</td>
                                <td>${it[3]}</td>
                                <td>${it[4]}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${map.count1}" params="${params}"/>
                </div>
            </div>
        </div>
        <div class="hpanel hgreen">
            <div class="panel-heading">
                放款信息统计
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="authority" rowspan="2" title="${message(code: 'message.authority.label', default: '销售人员')}"/>
                            <g:sortableColumn property="authority" colspan="2" title="${message(code: 'message.authority.label', default: '速e贷')}"/>
                            <g:sortableColumn property="authority" colspan="2" title="${message(code: 'message.authority.label', default: '速e贷')}"/>
                        </tr>
                        <tr>
                            <g:sortableColumn property="authority" title="${message(code: 'message.authority.label', default: '批贷规模(万)')}"/>
                            <g:sortableColumn property="authority" title="${message(code: 'message.authority.label', default: '放款规模(万)')}"/>
                            <g:sortableColumn property="authority" title="${message(code: 'message.authority.label', default: '批贷笔数')}"/>
                            <g:sortableColumn property="authority" title="${message(code: 'message.authority.label', default: '放款笔数')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${map.list2}">
                            <tr>
                                <td>${it[0]}</td>
                                <td>${it[1]}</td>
                                <td>${it[2]}</td>
                                <td>${it[3]}</td>
                                <td>${it[4]}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${map.count2}" params="${params}"/>
                </div>
            </div>
        </div>
    </div>--}%

</div>
<g:javascript>
    $("#resetBtn").click(function () {
        $("#startTime").val("");
        $("#endTime").val("");
        $("#stage").val("评房");
        $("#city").val("");
    });
</g:javascript>
</body>
</html>
