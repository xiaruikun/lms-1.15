<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>贷款人</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>贷款人管理</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                贷款人管理
            </h2>
            <small><span class="glyphicon glyphicon-user" aria-hidden="true"></span> 所有贷款人</small>

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
            <g:form method="POST" action="search" class="searchForm">
                <input type="hidden" id="type" name="type" value="Client">
                <div class="hpanel hblue">
                    <div class="panel-heading">
                        <div class="panel-tools">
                            <button class="btn btn-primary btn-xs" type="submit"><i class="fa fa-search"></i>查询</button>
                            <button class="btn btn-warning2 btn-xs" type="reset" id="resetBtn"><i
                                    class="fa fa-times"></i>重置</button>
                        </div>
                        查询
                    </div>
                    <div class="panel-body">
                        <div class="col-sm-3">
                            <input type="text" class="form-control" placeholder="借款人姓名" id="fullName" name="fullName"
                                   value="${contact?.fullName}">
                        </div>

                       %{-- <div class="col-sm-3">
                            <input type="text" class="form-control" placeholder="借款人手机" id="cellphone" name="cellphone"
                                   value="${contact?.cellphone}">
                        </div>
--}%
                        <div class="col-sm-3">
                            <input type="text" class="form-control" placeholder="所在城市" id="city" name="city"
                                   value="${contact?.city?.name}">
                        </div>

                       %{-- <div class="col-sm-3">
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
                      <sec:ifAnyGranted roles="ROLE_ADMINISTRATOR"><g:link action="create" params="[type: 'Client']" class="btn btn-info btn-xs">新建</g:link></sec:ifAnyGranted>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </div>
                    全部贷款人
                </div>

                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <g:sortableColumn property="fullName"
                                                  title="${message(code: 'opportunity.fullName.label', default: '借款人姓名')}"/>
                               %{-- <g:sortableColumn property="cellphone"
                                                  title="${message(code: 'opportunity.cellphone.label', default: '借款人手机')}"/>--}%
                                <g:sortableColumn property="cellphone"
                                                  title="${message(code: 'opportunity.city.label', default: '所在城市')}"/>
                                %{--<g:sortableColumn property="idNumber"
                                                  title="${message(code: 'opportunity.idNumber.label', default: '身份证')}"/>--}%
                                <g:sortableColumn property="fullName" title="申请次数"/>
                                <g:sortableColumn property="status" title="成功借款次数"/>
                                <g:sortableColumn property="createdDate"
                                                  title="${message(code: 'opportunity.createdDate.label', default: '最近申请时间')}"/>
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${contactList}">
                                <tr>
                                    <td><g:link action="show" id="${it.id}" class="firstTd">${it.fullName}</g:link></td>
                                    %{--<td class="cellphoneFormat">${it.cellphone}</td>--}%
                                    <td>${it.city?.name}</td>
                                    %{--<td>${it.idNumber}</td>--}%
                                    <td>${com.next.Opportunity.countByIdNumber(it.idNumber)}</td>
                                    <td>${com.next.Opportunity.countByLenderAndStatus(it, "Completed")}</td>
                                    <td><g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it.createdDate}"/></td>
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
