<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>外访报告</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body clearfix">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb" style="background-color: transparent;margin-top: 0;">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="opportunity" action="show" id="${this?.opportunity?.id}">订单管理</g:link>
                    </li>
                    <li class="active">
                        <span>信息查看</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light pull-left">
                订单号: ${this.opportunity?.serialNumber}
            </h2>

            <div class="text-muted font-bold m-b-xs ol-md-6">
                <g:if test="${this.opportunity?.protectionEndTime > new Date()}">
                    <div class="col-md-1"><span
                            class="fa fa-chain"></span>${this.opportunity?.protectionEndTime - this.opportunity?.protectionStartTime}天
                    </div>
                </g:if>

                <div class="col-md-2"><strong>${this.opportunity?.stage?.name}</strong></div>
                <g:if test="${this.opportunity?.status == 'Failed'}"><span
                        class="label label-danger">订单结果：失败</span></g:if>
                <g:elseif test="${this.opportunity?.status == 'Completed'}"><span
                        class="label label-success">订单结果：成功</span></g:elseif>
                <g:else><span class="label label-info">订单结果：进行中</span></g:else>
            </div>


        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <g:if test="${flash.message}">
            <div class="message alert alert-info" role="status">${flash.message}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
            </div>
        </g:if>
        <g:hasErrors bean="${this.opportunityTeam}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.opportunityTeam}" var="error">
                    <li>
                        <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>
                        <g:message error="${error}"/>
                    </li>
                </g:eachError>
            </ul>
        </g:hasErrors>
    </div>

    <div class="row">
        <g:each in="${this.opportunityFlexFieldCategorys}">
            <g:if test="${it?.flexFieldCategory?.name == '抵押物评估值'}">
                <div class="hpanel hyellow">

                    <div class="panel-heading hbuilt">
                        <div class="panel-tools">
                            <g:link class="btn btn-info btn-xs" controller="opportunityFlexField" id="${it?.id}"
                                    params="[outReport: '外访报告']"
                                    action="batchEdit"><i class="fa fa-edit"></i>编辑</g:link>

                            <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                        </div>
                        ${it?.flexFieldCategory?.name}
                    </div>

                    <div class="panel-body form-horizontal no-padding">
                        <div class="table-responsive">
                            <g:each in="${it?.fields}">
                                <div style="display: inline-table; margin-bottom: 5px;">
                                    <table class="table table-striped table-bordered table-hover">
                                        <tr>
                                            <th style="background: #eee"><g:link controller="opportunityFlexField" action="show" id="${it?.id}"
                                                        class="firstTd">${it?.name}</g:link></th>
                                        </tr>
                                        <tr>
                                            <td>${it?.value}</td>
                                        </tr>
                                    </table>
                                </div>
                            </g:each>
                        </div>
                    </div>
                </div>
            </g:if>
        </g:each>
    </div>

    <div class="row">
        <g:each in="${this.opportunityFlexFieldCategorys}">
            <g:if test="${it?.flexFieldCategory?.name == '抵押物其他情况'}">
                <div class="hpanel hyellow">

                    <div class="panel-heading hbuilt">
                        <div class="panel-tools">
                            <g:link class="btn btn-info btn-xs" controller="opportunityFlexField" id="${it?.id}"
                                    params="[outReport: '外访报告']"
                                    action="batchEdit"><i class="fa fa-edit"></i>编辑</g:link>

                            <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                        </div>
                        ${it?.flexFieldCategory?.name}
                    </div>

                    <div class="panel-body form-horizontal no-padding">
                        <div class="table-responsive">
                            <g:each in="${it?.fields}">
                                <div style="display: inline-table; margin-bottom: 5px;">
                                    <table class="table table-striped table-bordered table-hover">
                                        <tr>
                                            <th style="background: #eee"><g:link controller="opportunityFlexField" action="show" id="${it?.id}"
                                                        class="firstTd">${it?.name}</g:link></th>
                                        </tr>
                                        <tr>
                                            <td>${it?.value}</td>
                                        </tr>
                                    </table>
                                </div>
                            </g:each>
                        </div>
                    </div>
                </div>
            </g:if>
        </g:each>
    </div>

    <div class="row">
        <g:each in="${this.opportunityFlexFieldCategorys}">
            <g:if test="${it?.flexFieldCategory?.name == '外访预警'}">
                <div class="hpanel hyellow collapsed">
                    <div class="panel-heading hbuilt">
                        <div class="panel-tools">
                            <g:link class="btn btn-info btn-xs" controller="opportunityFlexField" id="${it?.id}"
                                    params="[outReport: '外访报告']"
                                    action="batchEdit"><i class="fa fa-edit"></i>编辑</g:link>
                            <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                        </div>
                        ${it?.flexFieldCategory?.name}
                    </div>

                    <div class="panel-body no-padding">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover">
                                <g:each in="${it?.fields}">
                                    <tr>
                                        <td>
                                            <g:link controller="opportunityFlexField" action="show" id="${it?.id}"
                                                    class="firstTd">${it?.name}</g:link>
                                        </td>
                                        <td>${it?.value}</td>
                                    </tr>
                                </g:each>
                            </table>
                        </div>
                    </div>
                </div>
            </g:if>
        </g:each>
    </div>
</div>
</body>

</html>
