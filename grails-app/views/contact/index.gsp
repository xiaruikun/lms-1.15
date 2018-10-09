<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'contact.label', default: 'Contact')}"/>
    <title>经纪人</title>
</head>

<body class="fixed-navbar fixed-sidebar">

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>经纪人管理</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                经纪人管理
            </h2>
            <small><span class="glyphicon glyphicon-user" aria-hidden="true"></span> 所有经纪人</small>
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
            <g:form method="POST" action="search">
                <input type="hidden" id="type" name="type" value="Agent">

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
                            <input type="text" class="form-control" placeholder="经纪人姓名" id="fullName" name="fullName"
                                   value="${contact?.fullName}">
                        </div>

                       %{-- <div class="col-sm-3">
                            <input type="text" class="form-control" placeholder="经纪人手机" id="cellphone" name="cellphone"
                                   value="${contact?.cellphone}">
                        </div>--}%

                        <div class="col-sm-3">
                            <input type="text" class="form-control" placeholder="所在城市" id="city" name="city"
                                   value="${contact?.city?.name}">
                        </div>

                        %{--<div class="col-sm-3">
                            <input type="text" class="form-control" placeholder="身份证" id="idNumber" name="idNumber"
                                   value="${contact?.idNumber}">
                        </div>--}%
                    </div>
                </div>
            </g:form>
        </div>

        <div class="row">
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <div class="hpanel hgreen">
                <div class="panel-heading">
                    <div class="panel-tools">
                      <sec:ifAnyGranted roles="ROLE_ADMINISTRATOR"><g:link action="create" params="[type: 'Agent']" class="btn btn-info btn-xs">新建</g:link></sec:ifAnyGranted>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </div>
                    全部经纪人
                </div>

                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <g:sortableColumn property="fullName"
                                                  title="${message(code: 'contact.fullName.label', default: '经纪人姓名')}"/>
                               %{-- <g:sortableColumn property="cellphone"
                                                  title="${message(code: 'contact.cellphone.label', default: '经纪人手机')}"/>--}%
                                <g:sortableColumn property="user.fullName"
                                                  title="${message(code: 'user.contact.fullName.label', default: '支持经理姓名')}"/>
                               %{-- <g:sortableColumn property="user.cellphone"
                                                  title="${message(code: 'user.contact.cellphone.label', default: '支持经理手机')}"/>--}%
                                <g:sortableColumn property="city"
                                                  title="${message(code: 'contact.city.label', default: '所在城市')}"/>
                               %{-- <g:sortableColumn property="idNumber"
                                                  title="${message(code: 'contact.idNumber.label', default: '身份证')}"/>--}%
                                <g:sortableColumn property="bankName"
                                                  title="${message(code: 'contact.bankName.label', default: '银行卡支行信息')}"/>
                                <g:sortableColumn property="bankAccount"
                                                  title="${message(code: 'contact.bankAccount.label', default: '银行卡号')}"/>
                                %{--<g:sortableColumn property="createdDate" title="${message(code: 'contact.createdDate.label', default: '累计返点')}" />--}%
                                <g:sortableColumn property="createdDate"
                                                  title="${message(code: 'contact.createdDate.label', default: '注册时间')}"/>
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${contactList}">
                                <tr>
                                    <td><g:link action="show" id="${it?.id}"
                                                class="firstTd">${it?.fullName}</g:link></td>
                                    %{--<td>${it?.cellphone}</td>--}%
                                    <td>${it?.user?.fullName}</td>
                                    %{--<td>${it?.user?.cellphone}</td>--}%
                                    <td>${it?.city?.name}</td>
                                    %{--<td>${it?.idNumber}</td>--}%
                                    <td>${it?.bankName}</td>
                                    <td>${it?.bankAccount}</td>
                                    <td><g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it?.createdDate}"/></td>
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="panel-footer">
                    <div class="pagination">
                        <g:paginate total="${contactCount ?: 0}" params="${params}"/>
                    </div>
                </div>
            </div>
        </div>
</div>
<g:javascript>
    $(function(){
        $("#resetBtn").click(function () {
            $("#fullName").val("");
            $("#cellphone").val("");
            $("#city").val("");
            $("#idNumber").val("");
        })
    });

</g:javascript>
</body>
</html>
