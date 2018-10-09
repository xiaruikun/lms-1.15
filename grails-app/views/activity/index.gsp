<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'activity.label', default: 'Activity')}"/>
    <title>活动管理</title>
    <asset:stylesheet src="homer/vendor/fullcalendar/fullcalendar.min.css"/>
    <style>
    .fc-day-grid-event > .fc-content {
        white-space: pre-wrap;
        word-wrap: break-word;
    }

    .fc-event-container {
        padding: 1px 0 !important;
    }

    .fc-event-container a {
        padding: 1px 0 !important;
    }

    .fc-basic-view tbody .fc-row {

    }

    .time-list {
        padding: 6px 20px;
    }

    .time-list p {
        margin: 5px 0;
    }
    .fc-event-container a{
       border-color: transparent;
    }
    </style>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li><g:link controller="activity" action="index">活动管理</g:link></li>
                    <li class="active">
                        <span>活动信息查看</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                活动管理
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row" style="padding-top: 10px;">
        <div class="col-md-9">
            <div class="hpanel horange">
                <div class="panel-heading">
                    <div class="panel-tools">
                      <sec:ifNotGranted roles="ROLE_COO">
                        <g:link class="btn btn-info btn-xs" controller="activity" action="create"><i
                                class="fa fa-plus"></i>添加活动</g:link>
                              </sec:ifNotGranted>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </div>
                    日历
                </div>

                <div class="panel-body">
                    <div id="calendar"></div>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="hpanel ">
                <div class="panel-heading ">
                    <div class="panel-tools">
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </div>
                    活动列表<code>未完成的</code>
                </div>

                <div class="panel-body ">
                    <div class="v-timeline vertical-container animate-panel" data-child="vertical-timeline-block"
                         data-delay="1" style="margin:5px 0">
                        <g:each in="${activityList}">
                            <div class="vertical-timeline-block" style="margin:5px 0">
                                <div class="vertical-timeline-icon navy-bg">
                                    <i class="fa fa-calendar text-primary"></i>
                                </div>

                                <div class="vertical-timeline-content">
                                    <div class="time-list">
                                        <p>类型：${it?.type?.name}</p>

                                        <p>子类型：${it?.subtype?.name}</p>

                                        <p>创建者：<code>${it?.user?.fullName}</code></p>

                                        <p>执行者：${it?.assignedTo?.fullName}</p>
                                    </div>
                                </div>
                            </div>
                        </g:each>

                    </div>
                </div>

            </div>
        </div>

    </div>
</div>
<asset:javascript src="homer/vendor/moment/min/moment.min.js"/>
<asset:javascript src="homer/vendor/fullcalendar/fullcalendar.min.js"/>
<asset:javascript src="homer/vendor/fullcalendar/zh-cn.js"/>

<script>
    $(function () {
        $('#calendar').fullCalendar({
            eventLimit: true,
            monthNames: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            editable: false,
            events: [
                <g:each in="${activityList}">
                {
                    title: '${it?.user?.fullName}-${it?.assignedTo?.fullName}-${it?.subtype?.name}-${it?.contact?.fullName}',
                    start: '<g:formatDate  date="${it?.startTime}" format="yyyy-MM-dd HH:mm:ss"></g:formatDate>',
                    end: '<g:formatDate date="${it?.endTime}" format="yyyy-MM-dd HH:mm:ss"></g:formatDate>',
                    backgroundColor: getColor(),
                    url:"/activity/show/" + ${it?.id},
                },
                </g:each>
            ],


        });


    });
    function getColor() {
        var colorArray = ['5c7b98', '#3498db','#f0ad4e','#e78170','#4CAF50'];
        var n = Math.floor(Math.random() * colorArray.length + 1) - 1;
        return colorArray[n];
    };

</script>
</body>
</html>
