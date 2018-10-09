<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunityContact.label', default: 'OpportunityContact')}"/>
    <title>新增联系人信息</title>
</head>

<body>
<div class="small-header transition animated fadeIn">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li><g:link controller="opportunity" action="show"
                                id="${this.opportunityContact?.opportunity?.id}">订单详情</g:link></li>
                    <li class="active">新增联系人</li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                新增联系人信息
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hyellow">
            <div class="panel-heading">
                <div class="panel-tools">
                </div>
                新增联系人信息
            </div>

            <div class="panel-body">
                <g:if test="${flash.message}">
                    <div class="message alert alert-info" role="status">${flash.message}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                                aria-hidden="true">×</span></button>
                    </div>
                </g:if>
                <g:hasErrors bean="${this.opportunityContact}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${this.opportunityContact}" var="error">
                            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                    error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </g:hasErrors>
                <g:form action="save" class="form-horizontal">
                    <div class="form-group">
                        <label class="col-md-2 control-label">订单编号</label>

                        <div class="col-md-3">
                            <g:textField class="hide" name="opportunity.id" id="opportunity" readOnly="true"
                                         value="${this.opportunityContact?.opportunity?.id}"></g:textField>
                            <g:textField class="form-control" name="serialNumber" readOnly="true"
                                         value="${this.opportunityContact?.opportunity?.serialNumber}"></g:textField>
                        </div>
                        <label class="col-md-2 control-label">联系人类型</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="type.id" required="required" id="type"
                                      value="${this.opportunityContact?.type?.id}"
                                      from="${com.next.OpportunityContactType.list()}" optionKey="id"
                                      optionValue="name"></g:select>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">联系人姓名</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" name="contact.fullName"
                                         value="${this.opportunityContact?.contact?.fullName}"></g:textField>
                        </div>
                        <label class="col-md-2 control-label">联系人手机号</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" name="contact.cellphone"
                                         value="${this.opportunityContact?.contact?.cellphone}"></g:textField>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">联系人身份证号</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" name="contact.idNumber"
                                         value="${this.opportunityContact?.contact?.idNumber}"></g:textField>
                        </div>
                        <label class="col-md-2 control-label">联系人婚姻状况</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="contact.maritalStatus"
                                      value="${this.opportunityContact?.contact?.maritalStatus}"
                                      from="${["未婚", "已婚", "再婚", "离异", "丧偶"]}"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                      <label class="col-md-2 control-label">关联联系人</label>

                      <div class="col-md-3">
                          <span class="cont">
                              <g:select class="form-control" name="connectedContact"
                                        value="${this.opportunityContact?.connectedContact?.id}"
                                        noSelection="${['null': '无']}"
                                        from="${this.connectedContactList}" optionKey="id"
                                        optionValue="fullName"></g:select>

                          </span>
                      </div>

                                <label class="col-md-2 control-label">关联关系</label>

                                <div class="col-md-3">
                                    <span class="cont">
                                        <g:select class="form-control" name="connectedType"
                                                  value="${this.opportunityContact?.connectedType?.id}"
                                                  noSelection="${['null': '无']}"
                                                  from="${com.next.OpportunityContactType.list()}" optionKey="id"
                                                  optionValue="name"></g:select>

                                    </span>
                                </div>
                            </div>

                    <div class="hr-line-dashed"></div>

                    %{--<div class="form-group">
                        <label class="col-md-2 control-label">行业</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="contact.industry.id" id="contact.industry"
                                      value="${this.opportunityContact?.contact?.industry?.id}" optionKey="id"
                                      optionValue="name"
                                      from="${com.next.Industry.list()}"/>
                        </div>
                        <label class="col-md-2 control-label">公司</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" type="text"
                                         name="contact.company"
                                         value="${this.opportunityContact?.contact?.company}"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>--}%

                    <div class="form-group">
                        %{--<label class="col-md-2 control-label">工商&机构代码</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" type="text"
                                         name="contact.companyCode"
                                         value="${this.opportunityContact?.contact?.companyCode}"/>
                        </div>--}%
                        <label class="col-md-2 control-label">职业</label>

                        <div class="col-md-3">

                            <select name="contact.profession.id" id="contact.profession" value="${this.opportunityContact?.contact?.profession?.id}"
                                    class="form-control">
                                <g:each in="${com.next.ContactProfession.list()}">
                                    <g:if test="${it?.name == '其他职业'}">
                                        <option value="${it?.id}" selected="selected">${it?.name}</option>
                                    </g:if>
                                    <g:else>
                                        <option value="${it?.id}">${it?.name}</option>
                                    </g:else>
                                </g:each>

                            </select>

                        %{--    <g:select class="form-control" name="contact.profession.id" id="contact.profession"
                                      value="${this.opportunityContact?.contact?.profession?.id}" optionKey="id"
                                      optionValue="name"
                                      from="${com.next.ContactProfession.list()}"/>--}%
                        </div>
                        <label class="col-md-2 control-label">国籍</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="contact.country.id" id="contact.country"
                                      value="${this.opportunityContact?.contact?.country?.id}" optionKey="id"
                                      optionValue="name"
                                      from="${com.next.Country.list()}"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">身份证件类型</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="contact.identityType.id" id="contact.identityType"
                                      value="${this.opportunityContact?.contact?.identityType?.id}" optionKey="id"
                                      optionValue="name"
                                      from="${com.next.ContactIdentityType.list()}"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-lg-offset-5">
                            <button class="btn btn-info">保存</button>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>

</body>
</html>
