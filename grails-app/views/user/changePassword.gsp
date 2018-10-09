<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
    <title>用户：${user?.fullName}</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>口令修改</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                用戶: ${this.user?.fullName}
            </h2>
        </div>
    </div>
</div>
<div class="content animate-panel">
<div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                口令修改
            </div>
            <div class="panel-body">
                <form class="form-horizontal updateForm">
                 <div class="alert alert-danger hide" id="helpMsg" role="status" style="margin-bottom:20px">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                    <g:if test="${flash.message}">
                                <div class="message alert alert-info" role="status" style="margin-bottom:20px">${flash.message}
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                                            aria-hidden="true">×</span></button>
                                </div>
                            </g:if>

                    <g:hasErrors bean="${this.user}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.user}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                    <g:message error="${error}" />
                                </li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <input class="form-control" type="hidden" name="user.id" id="user" value="${this.user.id}"/>
                    <div class="form-group">
                        <label class="col-md-3 control-label">原始口令</label>

                        <div class="col-md-3">
                            <input type="password" class="form-control" name="oldPassword"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">新口令</label>

                        <div class="col-md-3">
                            <input type="password" class="form-control" name="newPassword" id="newPassword"/>
                        </div>
                    </div>
                     <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">确认新口令</label>

                        <div class="col-md-3">
                            <input type="password" class="form-control" name="newPassword2"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
                            <g:submitButton class="btn btn-info submitBtn" name="update" value="保存" type="button"/>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        $(".submitBtn").click(function(){
            $(".updateForm").submit();
        });
         $(".updateForm").validate({
            rules:{
                newPassword:{
                    required:true,
                },
                newPassword2:{
                    required:true,
                    equalTo:"#newPassword"
                }
            },
            messages:{
                newPassword:{
                    required:"请输入新口令",
                },
                newPassword2:{
                    required:"请输入新口令",
                    equalTo:"两次口令不一致"
                }
            },
            submitHandler:function(){
                $.ajax({
                    url:"/user/updatePassword",
                    type:"post",
                    data:$(".updateForm").serialize(),
                    success:function(data){
                        if(data.status == "success") {

                            swal("口令重置成功，3秒钟之后调整到登录页面");
                            setTimeout(function(){
                                window.location.href = "/login/authenticate";
                            },3000);
                        } else {
                            swal(data.errorMessage, "", "error");
                        }
                    },
                    error:function(){
                        swal("服务器忙，口令重置失败，请稍后重试", "", "error");
                    },
                });
            }
        });
   });
</script>
</body>
</html>
