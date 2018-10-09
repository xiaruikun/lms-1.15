<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <asset:stylesheet src="homer/vendor/chartist/custom/chartist.css"/>
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
        </div>
    </div>


</div>

<g:if test="${flash.message}">
    <div class="row">
        <div class="hpanel">
            <div class="panel-body">
                <div class="alert alert-info" role="alert">${flash.message}</div>
            </div>
        </div>
    </div>
</g:if>
<div class="content animate-panel">
    <div class="row">
        <g:form method="POST" action="dailyReport">
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

                    <div class="col-sm-4">
                        <div class="col-sm-3">
                            <div class="input-group date form_datetime">
                                <span class="input-group-addon">
                                    <span class="fa fa-calendar"></span>
                                </span>
                                <input  title="开始时间" type="text" name="startTime" id="startTime" placeholder="开始时间" value="${params?.startTime}" readonly class="form-control daily-b">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="col-sm-3">
                            <div class="input-group date form_datetime">
                                <span class="input-group-addon">
                                    <span class="fa fa-calendar"></span>
                                </span>
                                <input  title="结束时间" type="text" name="endTime" id="endTime" placeholder="结束时间" value="${params?.endTime}" readonly class="form-control daily-b">
                            </div>
                        </div>
                    </div>
                    <sec:ifAnyGranted roles="ROLE_ADMINISTRATOR">
                        <div class="col-sm-4">
                            <g:select class="form-control" name="city" id="city"
                                      from="${["--CITY--","北京","上海","合肥","南京","成都","青岛","济南","西安"]}"
                                      valueMessagePrefix="stage" value="${params?.city}"/>
                        </div>
                    </sec:ifAnyGranted>
                </div>
            </div>
        </g:form>
    </div>
    <div class="row">
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>

        <div class="hpanel hgreen">

            <div class="row">
                <div class="col-lg-12">
                    <div class="hpanel">
                        <div class="panel-heading">
                            <div class="panel-tools">
                                <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                            </div>
                            资金业务数据分析
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <g:sortableColumn property="causeOfFailure"
                                                          title="${message(code: 'opportunity.causeOfFailure.label', default: '评估询值单数')}"></g:sortableColumn>
                                        <g:sortableColumn property="stage"
                                                          title="${message(code: 'opportunity.stage.label', default: '报单单数')}"></g:sortableColumn>
                                        <g:sortableColumn property="status"
                                                          title="${message(code: 'opportunity.status.label', default: '报单占比')}"></g:sortableColumn>
                                        <g:sortableColumn property="causeOfFailure"
                                                          title="${message(code: 'opportunity.causeOfFailure.label', default: '面谈单数')}"></g:sortableColumn>
                                        <g:sortableColumn property="stage"
                                                          title="${message(code: 'opportunity.stage.label', default: '面谈占比')}"></g:sortableColumn>
                                        <g:sortableColumn property="status"
                                                          title="${message(code: 'opportunity.status.label', default: '审批单数')}"></g:sortableColumn>
                                        <g:sortableColumn property="causeOfFailure"
                                                          title="${message(code: 'opportunity.causeOfFailure.label', default: '审批占比')}"></g:sortableColumn>
                                        <g:sortableColumn property="stage"
                                                          title="${message(code: 'opportunity.stage.label', default: '放款单数')}"></g:sortableColumn>
                                        <g:sortableColumn property="status"
                                                          title="${message(code: 'opportunity.status.label', default: '放款占比')}"></g:sortableColumn>
                                        <g:sortableColumn property="causeOfFailure"
                                                          title="${message(code: 'opportunity.causeOfFailure.label', default: '询值转成交通过率')}"></g:sortableColumn>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>${map?.countQuery[0]}</td>
                                            <td>${map?.countStep1[0]}</td>
                                            <td><g:formatNumber number="${map?.countStep1[0]*100/map?.countQuery[0]}" maxFractionDigits="2"/>%</td>
                                            <td>${map?.countStep2[0]}</td>
                                            <td><g:formatNumber number="${map?.countStep2[0]*100/map?.countQuery[0]}" maxFractionDigits="2"/>%</td>
                                            <td>${map?.countStep3[0]}</td>
                                            <td><g:formatNumber number="${map?.countStep3[0]*100/map?.countQuery[0]}" maxFractionDigits="2"/>%</td>
                                            <td>${map?.countStep4[0]}</td>
                                            <td><g:formatNumber number="${map?.countStep4[0]*100/map?.countQuery[0]}" maxFractionDigits="2"/>%</td>
                                            <td><g:formatNumber number="${map?.countStep4[0]*100/map?.countQuery[0]}" maxFractionDigits="2"/>%</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-4">
                    <div class="hpanel">
                        <div class="panel-heading">
                            <div class="panel-tools">
                                <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                            </div>
                            评估询值${map?.countQuery[0]}单，报单${map?.countStep1[0]}单，其中${map?.countQuery[0]-map?.countStep1[0]}单未报单原因
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <g:sortableColumn property="causeOfFailure"
                                                          title="${message(code: 'opportunity.causeOfFailure.label', default: '失败原因')}"></g:sortableColumn>
                                        <g:sortableColumn property="stage"
                                                          title="${message(code: 'opportunity.stage.label', default: '失败数量')}"></g:sortableColumn>
                                        <g:sortableColumn property="status"
                                                          title="${message(code: 'opportunity.status.label', default: '所占比重')}"></g:sortableColumn>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <g:each in="${map?.list}">
                                        <tr>
                                            <td>${it[1]?.name}</td>
                                            <td>${it[0]}</td>
                                            <td><g:formatNumber number="${it[0]/(map?.countFaile[0])*100}" maxFractionDigits="2"/>%</td>
                                        </tr>
                                    </g:each>
                                    </tbody>
                                </table>
                            </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4">
                    <div class="hpanel">
                        <div class="panel-heading">
                            <div class="panel-tools">
                                <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                            </div>
                            询值未转报单原因分析柱状图
                        </div>
                        <div class="panel-body">
                            <div>
                                <div id="ct-chart4" class="ct-perfect-fourth"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4">
                    <div class="hpanel">
                        <div class="panel-heading">
                            <div class="panel-tools">
                                <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                            </div>
                            询值未转报单原因分析饼状图
                        </div>
                        <div class="panel-body">
                            <div>
                                <div id="ct-chart5" class="ct-perfect-fourth"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-4">
                    <div class="hpanel">
                        <div class="panel-heading">
                            <div class="panel-tools">
                                <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                            </div>
                            报单${map?.countStep1[0]}单，成功${map?.countStep4[0]}单，其中${map?.countStep1[0]-map?.countStep4[0]}单未成交原因
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <g:sortableColumn property="causeOfFailure"
                                                          title="${message(code: 'opportunity.causeOfFailure.label', default: '失败原因')}"></g:sortableColumn>
                                        <g:sortableColumn property="stage"
                                                          title="${message(code: 'opportunity.stage.label', default: '失败数量')}"></g:sortableColumn>
                                        <g:sortableColumn property="status"
                                                          title="${message(code: 'opportunity.status.label', default: '所占比重')}"></g:sortableColumn>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <g:each in="${map?.list1}">
                                        <tr>
                                            <td>${it[1]?.name}</td>
                                            <td>${it[0]}</td>
                                            <td><g:formatNumber number="${it[0]/(map?.countFaile1[0])*100}" maxFractionDigits="2"/>%</td>
                                        </tr>
                                    </g:each>
                                    </tbody>
                                </table>
                            </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4">
                    <div class="hpanel">
                        <div class="panel-heading">
                            <div class="panel-tools">
                                <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                            </div>
                            报单未转成交原因分析柱状图
                        </div>
                        <div class="panel-body">
                            <div>
                                <div id="ct-chart6" class="ct-perfect-fourth"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4">
                    <div class="hpanel">
                        <div class="panel-heading">
                            <div class="panel-tools">
                                <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                                <a class="closebox"><i class="fa fa-times"></i></a>
                            </div>
                            报单未转成交原因分析饼状图
                        </div>
                        <div class="panel-body">
                            <div>
                                <div id="ct-chart7" class="ct-perfect-fourth"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-4">
                    <div class="hpanel">
                        <div class="panel-heading">
                            <div class="panel-tools">
                                <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                            </div>
                            每日寻值趋势
                        </div>
                        <div class="panel-body" style="max-height: 410px;overflow: auto">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <g:sortableColumn property="causeOfFailure"
                                                          title="${message(code: 'opportunity.causeOfFailure.label', default: '日期')}"></g:sortableColumn>
                                        <g:sortableColumn property="stage"
                                                          title="${message(code: 'opportunity.stage.label', default: '询值单数')}"></g:sortableColumn>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <g:each in="${map?.list2}">
                                        <tr>
                                            <td>${it[1]}</td>
                                            <td>${it[0]}</td>
                                        </tr>
                                    </g:each>
                                    </tbody>
                                </table>
                            </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-8">
                    <div class="hpanel">
                        <div class="panel-heading">
                            <div class="panel-tools">
                                <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                            </div>
                            每日寻值趋势图
                        </div>
                        <div class="panel-body">
                            <div>
                                <canvas id="sharpLineOptions" height="140"></canvas>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<asset:javascript src="homer/vendor/iCheck/icheck.min.js"/>
<asset:javascript src="homer/vendor/chartjs/Chart.min.js"/>
<asset:javascript src="homer/vendor/chartist/dist/chartist.min.js"/>
<asset:javascript src="homer/vendor/jquery.PrintArea.js"/>
<script>
    $(function () {
        // Stocked horizontal bar

        new Chartist.Bar('#ct-chart4', {
            labels: [
                <g:each in="${map?.list}" >
                '${it[1]?.name}',
                </g:each>
            ],
            series: [
                [
                    <g:each in="${map?.list}" >
                    ${it[0]},
                    </g:each>
                ]
            ]
        }, {
            seriesBarDistance: 10,
            reverseData: true,
            horizontalBars: true,
            axisY: {
                offset: 70
            }
        });

        // Simple pie chart

        var data = {
            series: [
                <g:each in="${map?.list}" >
                ${it[0]},
                </g:each>
            ]
        };

        var sum = function(a, b) { return a + b };

        new Chartist.Pie('#ct-chart5', data, {
            labelInterpolationFnc: function(value) {
                return Math.round(value / data.series.reduce(sum) * 100) + '%';
            }
        });

        // Stocked horizontal bar

        new Chartist.Bar('#ct-chart6', {
            labels: [
                <g:each in="${map?.list1}" >
                '${it[1]?.name}',
                </g:each>
            ],
            series: [
                [
                    <g:each in="${map?.list1}" >
                    ${it[0]},
                    </g:each>
                ]
            ]
        }, {
            seriesBarDistance: 10,
            reverseData: true,
            horizontalBars: true,
            axisY: {
                offset: 70
            }
        });

        // Simple pie chart

        var data1 = {
            series: [
                <g:each in="${map?.list1}" >
                ${it[0]},
                </g:each>
            ]
        };

        var sum1 = function(a, b) { return a + b };

        new Chartist.Pie('#ct-chart7', data1, {
            labelInterpolationFnc: function(value) {
                return Math.round(value / data1.series.reduce(sum1) * 100) + '%';
            }
        });

        /**
         * Options for Sharp Line chart
         */
        var sharpLineData = {
            labels: [
                <g:each in="${map?.list2}" >
                "${it[1]}",
                </g:each>
            ],
            datasets: [
                {
                    label: "Example dataset",
                    fillColor: "rgba(98,203,49,0.5)",
                    strokeColor: "rgba(98,203,49,0.7)",
                    pointColor: "rgba(98,203,49,1)",
                    pointStrokeColor: "#fff",
                    pointHighlightFill: "#fff",
                    pointHighlightStroke: "rgba(98,203,49,1)",
                    data: [
                        <g:each in="${map?.list2}" >
                        ${it[0]},
                        </g:each>
                    ]
                }
            ]
        };

        var sharpLineOptions = {
            scaleShowGridLines : true,
            scaleGridLineColor : "rgba(0,0,0,.05)",
            scaleGridLineWidth : 1,
            bezierCurve : false,
            pointDot : true,
            pointDotRadius : 4,
            pointDotStrokeWidth : 1,
            pointHitDetectionRadius : 20,
            datasetStroke : true,
            datasetStrokeWidth : 1,
            datasetFill : true,
            responsive: true
        };

        var ctx = document.getElementById("sharpLineOptions").getContext("2d");
        var myNewChart = new Chart(ctx).Line(sharpLineData, sharpLineOptions);

        /*
         /!*打印部分一*!/
         $("#sidebar").click(function(){
         $("#wrapper").printArea();
         });

         /!*打印部分二*!/
         $("#print").on("click",function(){
         preview();
         });

         function preview() {
         var bdhtml=window.document.body.innerHTML;//获取当前页的html代码   
         var startStr="<!--startprint-->";//设置打印开始区域   
         var endStr="<!--endprint-->";//设置打印结束区域   
         var printHtml=bdhtml.substring(bdhtml.indexOf(startStr)+startStr.length,bdhtml.indexOf(endStr));
         window.document.body.innerHTML=printHtml;//需要打印的页面   
         window.print();
         window.document.body.innerHTML=bdhtml;//还原界面   
         location.reload();
         }*/
    });
    $("#resetBtn").click(function () {
        $("#startTime").val("");
        $("#endTime").val("");
        $("#stage").val("评房");
        $("#city").val("");
    })
</script>
</body>
</html>