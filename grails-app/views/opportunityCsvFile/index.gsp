<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'opportunityCsvFile.label', default: 'OpportunityCsvFile')}" />
    <title>存量订单导入</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>存量订单导入</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                存量订单导入
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" controller="OpportunityCsvFile" action="create"><i
                            class="fa fa-plus"></i>新增</g:link>
                </div>
                导入文件列表
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="filetype"
                                              title="${message(code: 'opportunityCsvFile.filename.label', default: '文件名称')}"/>
                            <g:sortableColumn property="filename"
                                              title="${message(code: 'opportunityCsvFile.filetype.label', default: '文件类型')}"/>
                            <g:sortableColumn property="filepath"
                                              title="${message(code: 'opportunityCsvFile.filepath.label', default: '文件路径')}"/>

                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${opportunityCsvFileList}">
                            <tr>
                                <td><g:link controller="opportunityCsvFile" action="show"
                                            id="${it?.id}">${it?.filename}</g:link></td>
                                <td>${it?.filetype}</td>
                                <td>${it?.filepath}</td>

                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${opportunityCsvFileCount ?: 0}" params="${params}"/>
                </div>
            </div>
        </div>
    </div>
</div>
        <!--<a href="#list-opportunityCsvFile" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-opportunityCsvFile" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:table collection="${opportunityCsvFileList}" />

            <div class="pagination">
                <g:paginate total="${opportunityCsvFileCount ?: 0}" />
            </div>
        </div> -->
</body>
</html>