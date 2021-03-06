<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'activity.label', default: 'Activity')}"/>
    <title>新增活动</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="activity" action="index">活动</g:link>
                    </li>
                    <li class="active">
                        <span>新增活动</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                活动
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                新增活动
            </div>

            <div class="panel-body">
                <g:form action="save" class="form-horizontal">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.activity}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.activity}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                    <g:message error="${error}"/>
                                </li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <div class="form-group">
                       <!--  <label class="col-md-3 control-label">活动名称</label>

                        <div class="col-md-3">
                                <g:textField name="name" value="${this.activity?.opportunity?.serialNumber}" class="form-control" readOnly="readOnly"/>

                        </div> -->
                    </div>

                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">订单编号</label>

                        <div class="col-md-3">
                            <g:select name='opportunity.id' value="${this.activity?.opportunity?.id}" id="opportunityId"
                                      from='${this.opportunityList}' optionKey="id"
                                      optionValue="serialNumber" class="form-control"></g:select>
                            %{--<g:if test="${this.activity?.opportunity}">
                                <g:textField name="opportunity.id" id="opportunity"
                                             value="${this.activity?.opportunity?.id}" class="hide"/>
                                <g:textField name="opportunityName" value="${this.activity?.opportunity?.serialNumber}"
                                             class="form-control opportunityName"/>
                            </g:if>
                            <g:else>
                                <g:select name='opportunity.id' value="${this.activity?.opportunity?.id}"
                                          from='${com.next.Opportunity.list()}' optionKey="id"
                                          optionValue="serialNumber" class="form-control"></g:select>
                            </g:else>--}%
                        </div>
                    </div>
                    <g:if test="${this.activity?.opportunity?.contacts?.size() >0}">
                    <div class="hr-line-dashed"></div>


                    <div class="form-group">
                        <label class="col-md-3 control-label">客户</label>

                        <div class="col-md-3">
                            <select id="contact" name="contact.id" value=""
                                     class="form-control contactName">

                            </select>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                </g:if>

                    <div class="form-group">
                        <label class="col-md-3 control-label">所有者</label>

                        <div class="col-md-3">
                            %{--<p class="userInfo hide"><sec:loggedInUserInfo field="username"/></p>--}%
                            <g:textField name="user.id" id="user" value="${this.user?.id}" class="hide"/>
                            <g:textField name="userName" value="${this.user}" class="form-control" disabled="disabled"/>

                            %{--<g:if test="${this.activity?.user}">
                                <g:textField name="user.id" id="user" value="${this.activity?.user?.id}" class="hide"/>
                                <g:textField name="userName" value="${this.activity?.user?.fullName}"
                                             class="form-control"/>
                            </g:if>
                            <g:else>
                                <g:select id="user" name='user.id' value="${this.activity?.user?.id}"
                                          from='${com.next.User.list()}' optionKey="id"
                                          class="form-control"></g:select>
                            </g:else>--}%

                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">类型</label>

                        <div class="col-md-3">
                            <g:select id="type" name='type.id' value="${this.activity?.type?.id}"
                                      from='${com.next.ActivityType.list()}' optionKey="id" optionValue="name"
                                      class="form-control"></g:select>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">子类型</label>

                        <div class="col-md-3">
                            <g:select id="subtype" name='subtype.id' value="${this.activity?.subtype?.id}"
                                      from='${com.next.ActivitySubtype.list()}' optionKey="id" optionValue="name"
                                      class="form-control"></g:select>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">执行人</label>

                        <div class="col-md-3 datePicker">
                            <g:select id="assignedTo" name='assignedTo.id' value="${this.activity?.assignedTo?.id}"
                                      from='${com.next.User.list()}' optionKey="id"
                                      class="form-control"></g:select>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">开始时间</label>

                        <div class="col-md-6 datePicker">
                            <g:datePicker name="startTime"  value="${new Date()}" precision="minute"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">结束时间</label>

                        <div class="col-md-6 datePicker">
                            <g:datePicker name="endTime" value="${new Date()}" precision="minute"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
                            <g:submitButton class="btn btn-info" name="update" value="保存"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
<g:javascript>
    $(function () {
        //$("#userName").val($(".userInfo").text());
         function getContacts() {
            $.ajax({
                type: "POST",
                url: "/activity/getContacts",
                data: {
                    opportunityId: $("#opportunityId").val(),
                },

                success: function (data) {
                    var contactList = data.contactList;
                    var option;
                    if (data.status == "success") {
                        $('.contactName').html("");
                        for (var i = 0; i < contactList.length; i++) {
                            option = '<option value="' + contactList[i].id + '">' + contactList[i].name + '</option>';
                            $(".contactName").append(option)
                        }
                        $("select").select2();
                    }
                },
                error: function () {
                    swal("获取失败，请稍后重试", "", "error");
                }
            });
        }

        getContacts();
        // 动态客户
        $("#opportunityId").change(function () {
            getContacts();
        });

    })
</g:javascript>
</body>

</html>
