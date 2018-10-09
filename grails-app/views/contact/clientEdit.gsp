<!DOCTYPE html>

<html lang="en">
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'contact.label', default: 'contact')}"/>
    <title>贷款人编辑</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<g:if test="${flash.message}">
    <div class="row">
        <div class="container">
            <div class="alert alert-info" role="alert">${flash.message}</div>
        </div>
    </div>
</g:if>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li><g:link controller="contact" action="indexByClient">贷款人管理</g:link></li>
                    <li class="active">
                        <span>信息编辑</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                贷款人: ${this.contact?.fullName}
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel horange">
            <div class="panel-heading">贷款人信息管理</div>

            <div class="panel-body">
                <g:form resource="${this.contact}" method="PUT" class="form-horizontal">
                    <div class="form-group">
                        <label class="col-md-3 control-label">借款人姓名</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" name="fullName"
                                         value="${this.contact?.fullName}"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">借款人身份证</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" name="idNumber"
                                         value="${this.contact?.idNumber}"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">借款人手机号</label>

                        <div class="col-md-3">
                            <input type="hidden" name="cellphone" class="hiddenCellphone" value="${this.contact?.cellphone}">
                            <g:textField class="form-control cellphoneFormat2" name="cellphone2"
                                         value="${this.contact?.cellphone}"/>
                        </div>
                    </div>

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
<script>
    $(".cellphoneFormat2").focus(function(){
        $(this).val("");
        $(".hiddenCellphone").val("")
    })
    $(".cellphoneFormat2").blur(function(){
        $(".hiddenCellphone").val($(".cellphoneFormat2").val());
    });

</script>
</body>
</html>



