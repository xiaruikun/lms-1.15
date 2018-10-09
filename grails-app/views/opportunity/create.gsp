<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>新建订单</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">

            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li><g:link class="list" style="display:inline;float:right;" action="index"
                                controller="opportunityTeam">返回列表</g:link></li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                订单管理-新增订单
            </h2>
        </div>
    </div>
</div>


<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                新增订单
            </div>

            <div class="panel-body">
                <g:form action="save" class="form-horizontal">

                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.opportunity}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.opportunity}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>

                    <!-- <div class="form-group">
                        <label class="col-md-3 control-label">贷款金额</label>

                        <div class="col-md-3 input-group">
                            <g:textField class="form-control" name="requestedAmount"/>
                            <span class="input-group-addon">万元</span>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">贷款类型</label>

                        <div class="col-md-3 input-group">
                            <g:select name="type" value="${this.opportunity?.type?.id}"
                                      from="${com.next.OpportunityType.list()}" optionKey="id" class="form-control"
                                      noSelection="${['null': '请选择']}"></g:select>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div> -->

                    <div class="form-group">
                        <label class="col-md-3 control-label">经纪人</label>
                        <g:textField name="user.id" id="user" value="${this.user?.id}" class="hide"/>
                        <g:textField name="account.id" id="account" value="${this.account?.id}" class="hide"/>

                        <div class="col-md-3">
                            <div class="row">
                                <g:select from="${this.contacts}" class="form-control" required="required" name="contact.id"
                                          id="contact" optionKey="id" optionValue="fullName" noSelection="['':'请选择']"/>
                            </div>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
                            <g:submitButton class="btn btn-info" name="create" value="保存"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
<g:javascript>
    $(function () {
         function getUserAndAccount() {
            $.ajax({
                type: "POST",
                url: "/opportunity/getUserAndAccount",
                data: {
                    contact: $("#contact").val(),
                },

                success: function (data) {
                    if (data.status == "success") {
                        $('#user').val(data.user);
                        $('#account').val(data.account);
                    }
                },
                error: function () {
                    swal("获取失败，请稍后重试", "", "error");
                }
            });
        }

        $("#contact").change(function () {
            getUserAndAccount();
        });

    })
</g:javascript>
</body>
</html>
