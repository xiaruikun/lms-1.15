<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'bank.label', default: 'Bank')}"/>
    <title>银行</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">

            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>银行</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                银行
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link action="create" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>新建</g:link>
                </div>
                银行
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="code" title="银行代码"/>
                            <g:sortableColumn property="name" title="银行名称"/>
                            <g:sortableColumn property="description" title="描述"/>
                            <g:sortableColumn property="type" title="支付类型"/>
                            <g:sortableColumn property="active" title="有效性"/>
                            <g:sortableColumn property="singleLimit" title="单笔限额"/>
                            <g:sortableColumn property="dailyLimit" title="日累计限额"/>
                            <g:sortableColumn property="monthlyLimit" title="月累计限额"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${bankList}">
                            <tr>
                                <td><g:link action="show" id="${it?.id}" class="firstTd">${it?.code}</g:link></td>
                                <td>${it?.name}</td>
                                <td>${it?.description}</td>
                                <td>${it?.type}</td>
                                <td>${it?.active}</td>
                                <td>${it?.singleLimit}</td>
                                <td>${it?.dailyLimit}</td>
                                <td>${it?.monthlyLimit}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${bankCount ?: 0}" params="${params}"/>
                </div>
            </div>
        </div>
    </div>
</div>
%{-- 

        <a href="#list-bank" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-bank" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:table collection="${bankList}" />

            <div class="pagination">
                <g:paginate total="${bankCount ?: 0}" />
            </div>
        </div> --}%
</body>
</html>