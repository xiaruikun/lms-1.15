<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>报表图形</title>
</head>

<body class="fixed-navbar fixed-sidebar">
%{--<f:table collection="${billsList}" />--}%
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>图形</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                报表图形
            </h2>
            <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span> 所有图形</small>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <g:if test="${flash.message}">
        <div class="alert alert-success alert-dismissible" role="alert">
            ${flash.message}
        </div>
    </g:if>

    <div class="row">
        <div class="hpanel hgreen">
            <div class="row">
                <div class="col-lg-4">
                    <div class="hpanel">
                        <div class="panel-heading">
                            <div class="panel-tools">
                                <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                                <a class="closebox"><i class="fa fa-times"></i></a>
                            </div>
                            雷达类型
                        </div>
                        <div class="panel-body">
                            <div>
                                <canvas id="radarChart"></canvas>
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
                            甜甜圈类型
                        </div>
                        <div class="panel-body">
                            <div>
                                <canvas id="doughnutChart" height="140"></canvas>
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
                            极坐标类型
                        </div>
                        <div class="panel-body">
                            <div>
                                <canvas id="polarOptions" height="140"></canvas>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <div class="row">
                <div class="col-lg-6">
                    <div class="hpanel">
                        <div class="panel-heading">
                            <div class="panel-tools">
                                <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                                <a class="closebox"><i class="fa fa-times"></i></a>
                            </div>
                            曲线类型
                        </div>
                        <div class="panel-body">
                            <div>
                                <canvas id="lineOptions" height="140"></canvas>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-6">
                    <div class="hpanel">
                        <div class="panel-heading">
                            <div class="panel-tools">
                                <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                                <a class="closebox"><i class="fa fa-times"></i></a>
                            </div>
                            对比柱状图类型
                        </div>
                        <div class="panel-body">
                            <div>
                                <canvas id="barOptions" height="140"></canvas>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-6">
                    <div class="hpanel">
                        <div class="panel-heading">
                            <div class="panel-tools">
                                <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                                <a class="closebox"><i class="fa fa-times"></i></a>
                            </div>
                            单柱状图类型
                        </div>
                        <div class="panel-body">
                            <div>
                                <canvas id="singleBarOptions" height="140"></canvas>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-6">
                    <div class="hpanel">
                        <div class="panel-heading">
                            <div class="panel-tools">
                                <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                                <a class="closebox"><i class="fa fa-times"></i></a>
                            </div>
                            折线图类型
                        </div>
                        <div class="panel-body">
                            <div>
                                <canvas id="sharpLineOptions" height="140"></canvas>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <input type="text" id="datas" value="${contact}">
        </div>
        </div>
    </div>
<!-- Vendor scripts -->
<asset:javascript src="homer/vendor/iCheck/icheck.min.js"></asset:javascript>
<asset:javascript src="homer/vendor/chartjs/Chart.min.js"></asset:javascript>
<asset:javascript src="jquery.PrintArea.js"></asset:javascript>
<script>
    $(function () {
        /**
         * Options for Line chart
         */
        var lineData = {
            labels: ["January", "February", "March", "April", "May", "June", "July"],
            /*labels:[<g:each in="${contact}">"${it?.name}", </g:each>],*/
            datasets: [
                {
                    label: "Example dataset",
                    fillColor: "rgba(220,220,220,0.5)",
                    strokeColor: "rgba(220,220,220,1)",
                    pointColor: "rgba(220,220,220,1)",
                    pointStrokeColor: "#fff",
                    pointHighlightFill: "#fff",
                    pointHighlightStroke: "rgba(220,220,220,1)",
                    data: [22, 44, 66, 70, 66, 44, 22]
                    /*data:[<g:each in="${contact}">${it?.opportunity},</g:each>]*/
                },
                {
                    label: "Example dataset",
                    fillColor: "rgba(98,203,49,0.5)",
                    strokeColor: "rgba(98,203,49,0.7)",
                    pointColor: "rgba(98,203,49,1)",
                    pointStrokeColor: "#fff",
                    pointHighlightFill: "#fff",
                    pointHighlightStroke: "rgba(26,179,148,1)",
                    data: [70, 66, 44, 22, 44, 66, 70]
                }
            ]
        };

        var lineOptions = {
            scaleShowGridLines : true,
            scaleGridLineColor : "rgba(0,0,0,.05)",
            scaleGridLineWidth : 1,
            bezierCurve : true,
            bezierCurveTension : 0.4,
            pointDot : true,
            pointDotRadius : 4,
            pointDotStrokeWidth : 1,
            pointHitDetectionRadius : 20,
            datasetStroke : true,
            datasetStrokeWidth : 1,
            datasetFill : true,
            responsive: true
        };


        var ctx = document.getElementById("lineOptions").getContext("2d");
        var myNewChart = new Chart(ctx).Line(lineData, lineOptions);

        /**
         * Options for Sharp Line chart
         */
        var sharpLineData = {
            labels: ["January", "February", "March", "April", "May", "June", "July"],
            /*labels:[<g:each in="${contact}">"${it?.name}", </g:each>],*/
            datasets: [
                {
                    label: "Example dataset",
                    fillColor: "rgba(98,203,49,0.5)",
                    strokeColor: "rgba(98,203,49,0.7)",
                    pointColor: "rgba(98,203,49,1)",
                    pointStrokeColor: "#fff",
                    pointHighlightFill: "#fff",
                    pointHighlightStroke: "rgba(98,203,49,1)",
                    data: [22, 33, 44, 55, 44, 33, 22]
                   /* data:[<g:each in="${contact}">${it?.opportunity},</g:each>]*/
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


        /**
         * Options for Bar chart
         */
        var barOptions = {
            scaleBeginAtZero : true,
            scaleShowGridLines : true,
            scaleGridLineColor : "rgba(0,0,0,.05)",
            scaleGridLineWidth : 1,
            barShowStroke : true,
            barStrokeWidth : 1,
            barValueSpacing : 5,
            barDatasetSpacing : 1,
            responsive:true
        };

        /**
         * Data for Bar chart
         */
        var barData = {
            labels: ["January", "February", "March", "April", "May", "June", "July"],
            /*labels:[<g:each in="${contact}">"${it?.name}", </g:each>],*/
            datasets: [
                {
                    label: "My First dataset",
                    fillColor: "rgba(220,220,220,0.5)",
                    strokeColor: "rgba(220,220,220,0.8)",
                    highlightFill: "rgba(220,220,220,0.75)",
                    highlightStroke: "rgba(220,220,220,1)",
                    data: [22, 33, 44, 55, 44, 33, 22]
                   /* data:[<g:each in="${contact}">${it?.opportunity},</g:each>]*/
                },
                {
                    label: "My Second dataset",
                    fillColor: "rgba(98,203,49,0.5)",
                    strokeColor: "rgba(98,203,49,0.8)",
                    highlightFill: "rgba(98,203,49,0.75)",
                    highlightStroke: "rgba(98,203,49,1)",
                    data: [55, 44, 33, 22, 33, 44, 55]
                    /*data:[<g:each in="${contact}">${it?.opportunity},</g:each>]*/
                }
            ]
        };

        var ctx = document.getElementById("barOptions").getContext("2d");
        var myNewChart = new Chart(ctx).Bar(barData, barOptions);

        /**
         * Options for Bar chart
         */
        var singleBarOptions = {
            scaleBeginAtZero : true,
            scaleShowGridLines : true,
            scaleGridLineColor : "rgba(0,0,0,.05)",
            scaleGridLineWidth : 1,
            barShowStroke : true,
            barStrokeWidth : 1,
            barValueSpacing : 5,
            barDatasetSpacing : 1,
            responsive:true
        };

        /**
         * Data for Bar chart
         */
        var singleBarData = {

            labels: ["January", "February", "March", "April", "May", "June", "July"],
            /*labels:[<g:each in="${contact}">"${it?.name}", </g:each>],*/
            datasets: [
                {
                    label: "My Second dataset",
                    fillColor: "rgba(98,203,49,0.5)",
                    strokeColor: "rgba(98,203,49,0.8)",
                    highlightFill: "rgba(98,203,49,0.75)",
                    highlightStroke: "rgba(98,203,49,1)",
                    data: [10, 20, 30, 40, 30, 20, 10]
                    /*data:[<g:each in="${contact}">"${it?.opportunity}", </g:each>],*/
                }
            ]
        };

        var ctx = document.getElementById("singleBarOptions").getContext("2d");
        var myNewChart = new Chart(ctx).Bar(singleBarData, singleBarOptions);

        var polarData = [
            <g:each in="${contact}">
            {
                value: ${it?.opportunity},
                color:"#62cb31",
                highlight: "#57b32c",
                label: "${it?.name}"
            },

            </g:each>
        ]
        /*var polarData = [

            {
                value: 50,
                color:"#62cb31",
                highlight: "#57b32c",
                label: "张彦超"
            },
            {
                value: 100,
                color: "#80dd55",
                highlight: "#57b32c",
                label: "袁超"
            },
            {
                value: 150,
                color: "#a3e186",
                highlight: "#57b32c",
                label: "王超"
            }
        ];*/

        var polarOptions = {
            scaleShowLabelBackdrop: true,
            scaleBackdropColor: "rgba(255,255,255,0.75)",
            scaleBeginAtZero: true,
            scaleBackdropPaddingY: 1,
            scaleBackdropPaddingX: 1,
            scaleShowLine: true,
            segmentShowStroke: true,
            segmentStrokeColor: "#fff",
            segmentStrokeWidth: 2,
            animationSteps: 100,
            animationEasing: "easeOutBounce",
            animateRotate: true,
            animateScale: false,
            responsive: true,

        };

        var ctx = document.getElementById("polarOptions").getContext("2d");
        var myNewChart = new Chart(ctx).PolarArea(polarData, polarOptions);

        var color = 112233
        var doughnutData = [
            <g:each in="${contact}" >
            {
                value: ${it?.opportunity},
                color:"#"+color,
                highlight: "#57b32c",
                label: "${it?.name}"
            },
            </g:each>
            /*{
                value: 60,
                color:"#62cb31",
                highlight: "#57b32c",
                label: "App"
            },
            {
                value: 60,
                color: "#91dc6e",
                highlight: "#57b32c",
                label: "Software"
            },
            {
                value: 60,
                color: "#90ff34",
                highlight: "#66ff00",
                label: "Laptop"
            }*/
        ];

        var doughnutOptions = {
            segmentShowStroke: true,
            segmentStrokeColor: "#fff",
            segmentStrokeWidth: 2,
            percentageInnerCutout: 45, // This is 0 for Pie charts
            animationSteps: 100,
            animationEasing: "easeOutBounce",
            animateRotate: true,
            animateScale: false,
            responsive: true,
        };


        var ctx = document.getElementById("doughnutChart").getContext("2d");
        var myNewChart = new Chart(ctx).Doughnut(doughnutData, doughnutOptions);

        var radarData = {
            /*labels: ["Eating", "Drinking", "Sleeping", "Designing", "Coding", "Cycling", "Running"],*/
            labels:[<g:each in="${contact}">"${it?.name}", </g:each>],
            datasets: [
                {
                    label: "My First dataset",
                    fillColor: "rgba(98,203,49,0.2)",
                    strokeColor: "rgba(98,203,49,1)",
                    pointColor: "rgba(98,203,49,1)",
                    pointStrokeColor: "#fff",
                    pointHighlightFill: "#fff",
                    pointHighlightStroke: "#62cb31",
                    /*data: [65, 90, 66, 45, 70, 55, 40]*/
                    data:[<g:each in="${contact}">"${it?.opportunity}", </g:each>],
                },
                {
                    label: "My Second dataset",
                    fillColor: "rgba(98,203,49,0.4)",
                    strokeColor: "rgba(98,203,49,1)",
                    pointColor: "rgba(98,203,49,1)",
                    pointStrokeColor: "#fff",
                    pointHighlightFill: "#fff",
                    pointHighlightStroke: "#62cb31",
                    data: [90, 90, 40, 19, 63, 80, 87]
                }
            ]
        };

        var radarOptions = {
            scaleShowLine : true,
            angleShowLineOut : true,
            scaleShowLabels : false,
            scaleBeginAtZero : true,
            angleLineColor : "rgba(0,0,0,.1)",
            angleLineWidth : 1,
            pointLabelFontFamily : "'Arial'",
            pointLabelFontStyle : "normal",
            pointLabelFontSize : 10,
            pointLabelFontColor : "#666",
            pointDot : true,
            pointDotRadius : 2,
            pointDotStrokeWidth : 1,
            pointHitDetectionRadius : 20,
            datasetStroke : true,
            datasetStrokeWidth : 1,
            datasetFill : true,
        };

        var ctx = document.getElementById("radarChart").getContext("2d");
        var myNewChart = new Chart(ctx).Radar(radarData, radarOptions);

        /*打印部分一*/
        $("#sidebar").click(function(){
            $("#wrapper").printArea();
        });

        /*打印部分二*/
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
        }
    });

</script>

</body>
</html>