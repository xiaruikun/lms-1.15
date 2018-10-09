<!DOCTYPE html>

<html lang="en">
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'contact.label', default: 'contact')}"/>
    <title>经纪人编辑</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link class="list" controller="contact"
                                action="indexByAgent">返回列表</g:link>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                经纪人管理-信息编辑
            </h2>

        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">

        <div class="hpanel hgreen">
            <div class="panel-heading">经纪人信息管理--${this.contact.fullName}</div>

            <div class="panel">
                <div class="panel-body">
                    <g:if test="${flash.message}">
                        <div class="message alert alert-info" role="status">111${flash.message}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                                    aria-hidden="true">×</span></button>
                        </div>
                    </g:if>
                    <g:form resource="${this.contact}" method="PUT" class="form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 control-label">经纪人姓名</label>

                            <div class="col-md-3">
                                <g:textField class="form-control" type="text"
                                             name="fullName" id="fullName"
                                             value="${this.contact.fullName}"/>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>

                        <div class="form-group">
                            <label class="col-md-3 control-label">经纪人手机</label>

                            <div class="col-md-3">
                                <input type="hidden"  name="cellphone" value="${this.contact.cellphone}">
                                <span class="cont cellphoneFormat">${this.contact.cellphone}</span>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>

                        <div class="form-group">
                            <label class="col-md-3 control-label">支持经理邀请码</label>

                            <div class="col-md-3">
                                <g:textField class="form-control" type="text"
                                             name="userCode" id="userCode"
                                             value="${this.contact?.userCode}"/>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>

                        <div class="form-group">
                            <label class="col-md-3 control-label">所在城市</label>

                            <div class="col-md-3">
                                <g:select class="form-control" name="city.id" id="city"
                                          value="${this.contact?.city?.id}"
                                          from="${com.next.City.list()}" optionKey="id"
                                          optionValue="name"></g:select>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>

                        <div class="form-group">
                            <label class="col-md-3 control-label">身份证号</label>

                            <div class="col-md-3">

                                <g:textField class="form-control" type="text"
                                             name="idNumber" id="idNumber"
                                             value="${this.contact.idNumber}"/>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>

                        <div class="form-group">
                            <label class="col-md-3 control-label">银行卡支行信息</label>

                            <div class="col-md-3">
                                <g:textField class="form-control" type="text"
                                             name="bankName" id="bankName"
                                             value="${this.contact.bankName}"/>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>

                        <div class="form-group">
                            <label class="col-md-3 control-label">银行卡号</label>

                            <div class="col-md-3">
                                <g:textField class="form-control" type="text"
                                             name="bankAccount" id="bankAccount"
                                             value="${this.contact.bankAccount}"/>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>

                        <div class="form-group">
                            <div class="col-md-3 col-md-offset-3">
                                <g:submitButton id="submitBtn" class="btn btn-info" name="update" value="保存"/>
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



