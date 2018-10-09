<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'contactJudgementRecord.label', default: 'ContactJudgementRecord')}" />
        <title>人法网被执记录</title>
    </head>
    <body>
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li class="active">
                            <span>contactJudgementRecord列表</span>
                        </li>
                    </ol>
                </div>

                <h2 class="font-light m-b-xs">
                    人法网被执记录
                </h2>
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

        </div>

        <div class="row">
            <div class="hpanel hgreen">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <g:link class="btn btn-info btn-xs" controller="contactJudgementRecord" action="create"><i
                                class="fa fa-plus"></i>新增</g:link>
                    </div>
                    contactJudgementRecord
                </div>

                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>

                                <g:sortableColumn property="executionObject"
                                                  title="执行标的"/>
                                <g:sortableColumn property="executionStatus"
                                                  title="执行状态"/>
                                <g:sortableColumn property="filingTime"
                                                  title="立案时间"/>
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${contactJudgementRecordList}">
                                <tr>
                                    <td><g:link controller="component" action="show"
                                                id="${it?.id}">${it?.executionObject}</g:link></td>
                                    <td>${it?.executionStatus}</td>
                                    <td><g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it?.filingTime}"/></td>
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="panel-footer">
                    <div class="pagination">
                        <g:paginate total="${contactJudgementRecordCount ?: 0}" params="${params}"/>
                    </div>
                </div>
            </div>
        </div>
    </div>

    </body>
</html>