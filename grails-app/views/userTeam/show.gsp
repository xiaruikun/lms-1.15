<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'userTeam.label', default: 'UserTeam')}"/>
    <title>人员管理详情</title>

</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="userTeam" action="index">人员管理</g:link>
                    </li>
                    <li class="active">
                        <span>详情</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                人员管理
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                人员详情
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" action="edit"
                            resource="${this.userTeam}"><i class="fa fa-edit"></i>编辑</g:link>
                </div>
            </div>

            <div class="panel-body">
                <g:form class="form-horizontal">
                    <g:if test="${flash.message}">
                        <div class="message alert alert-info" role="status">${flash.message}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                                    aria-hidden="true">×</span></button>
                        </div>
                    </g:if>
                    <g:hasErrors bean="${this.userTeam}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.userTeam}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <div class="form-group">
                        <label class="col-md-3 control-label">员工姓名：</label>

                        <div class="col-md-3">
                            <span class="cont">${this.userTeam?.member?.fullName}</span>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">所属部门：</label>

                        <div class="col-md-3">
                            <span class="cont">${this.userTeam?.member?.department?.name}</span>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">岗位：</label>

                        <div class="col-md-3">
                            <span class="cont">${this.userTeam?.member?.position?.name}</span>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">员工手机：</label>

                        <div class="col-md-3">
                            <span class="cont cellphoneFormat">${this.userTeam?.member?.cellphone}</span>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">所在城市：</label>

                        <div class="col-md-3">
                            <span class="cont">${this.userTeam?.member?.city?.name}</span>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">用户名：</label>

                        <div class="col-md-3">
                           <span class="cont">${this.userTeam?.member?.username}</span>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">所属公司：</label>

                        <div class="col-md-3">
                            <span class="cont">${this.userTeam?.member?.account?.name}</span>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">外部唯一ID：</label>

                        <div class="col-md-3">
                            <span class="cont">${this.userTeam?.member?.externalId}</span>
                        </div>
                    </div>

                </g:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
