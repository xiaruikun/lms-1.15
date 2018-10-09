<!DOCTYPE html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'contact.label', default: 'Opportunity')}"/>
    <title>新增经纪人</title>
</head>

<body class="fixed-navbar fixed-sidebar">

<div class="small-header">

    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>权限管理</li>
                    <li class="active">
                        <span>新增</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">用户:${com.next.User.findById(params['id'])?.fullName}</h2>
        </div>
    </div>

    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hgreen">
                <div class="panel-heading">
                    新增
                </div>

                <div class="panel-body">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.contact}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.contact}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>



                    <g:form action="save" class="form-horizontal">

                        <div class="form-group">
                            <label class="col-md-3 control-label">姓名</label>

                            <div class="col-md-3">
                                %{--<g:textField type="text" name="fullName" id="fullName"/>--}%
                                <input class="form-control" name="fullName"
                                       type="text" step="any" value="${this.contact?.fullName}">
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>

                        <div class="form-group">
                            <label class="col-md-3 control-label">银行卡号</label>

                            <div class="col-md-3">
                                %{--<g:textField type="text" name="bankAccount" id="bankAccount"/>--}%
                                <input class="form-control" name="bankAccount"
                                       type="text" step="any" value="${this.contact?.bankAccount}">
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>

                        <div class="form-group">
                            <label class="col-md-3 control-label">手机</label>

                            <div class="col-md-3">
                                %{--<g:textField type="text" name="cellphone" id="cellphone" MaxLength="11"/>--}%
                                <input class="form-control" name="cellphone"
                                       type="text" step="any" MaxLength="11" value="${this.contact?.cellphone}">
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>

                        <div class="form-group">
                            <label class="col-md-3 control-label">所属公司</label>

                            <div class="col-md-3">
                                <g:select class="form-control m-b" name="account" id="account"
                                          optionValue="name" optionKey="id"
                                          from="${com.next.Account.list()}"
                                          value="${this.contact?.account?.id}"/>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>

                        <div class="form-group">
                            <label class="col-md-3 control-label">type</label>

                            <div class="col-md-3">
                                <g:select class="form-control m-b" name="type"
                                from="${["Agent", "Client"]}"
                                value="${this.contact?.type}"/>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>

                        <div class="form-group">
                            <label class="col-md-3 control-label">所属城市</label>

                            <div class="col-md-3">
                                <g:select class="form-control m-b" name="city" id="city"
                                          optionValue="name" optionKey="id"
                                          from="${com.next.City.list()}"
                                          value="${this.contact?.city?.id}"/>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>

                        <div class="form-group">
                            <label class="col-md-3 control-label">身份证号</label>

                            <div class="col-md-3">

                                %{--<g:textField type="text" name="idNumber" id="idNumber"/>--}%
                                <input class="form-control" name="idNumber"
                                       type="text" step="any" value="${this.contact?.idNumber}">

                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>

                        <div class="form-group">
                            <label class="col-md-3 control-label">银行卡支行信息</label>

                            <div class="col-md-3">
                                %{--<g:textField type="text" name="bankName" id="bankName"/>--}%
                                <input class="form-control" name="bankName"
                                       type="text" step="any" value="${this.contact?.bankName}">

                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>

                        <div class="form-group">
                            <div class="col-md-3 col-lg-offset-3">
                                <g:submitButton name="create" class="btn btn-info" value="保存"/>
                            </div>
                        </div>
                    </g:form>
                </div>
            </div>
        </div>

    </div>

</div>
</body>
</html>
