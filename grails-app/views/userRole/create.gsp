<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'userRole.label', default: 'UserRole')}"/>
    <title>新增权限</title>
</head>

<body class="fixed-navbar fixed-sidebar">

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right m-t-lg">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>权限管理</li>
                    <li class="active">
                        <span>用户权限</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                用户:${com.next.User.findById(params['id'])?.fullName}
            </h2>
        </div>
    </div>

</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                新增权限
            </div>

            <div class="panel-body">
                <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                </g:if>
                <g:hasErrors bean="${this.userRole}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${this.userRole}" var="error">
                            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                    error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </g:hasErrors>
                <g:form action="save" class="form-horizontal">
                    <div class="form-group">
                        <label class="col-md-3 control-label">角色</label>

                        <div class="col-md-3">
                            <g:select class="form-control m-b" name="role.id" required="" id="role"
                                      from="${com.next.Role.list()}" optionKey="id"
                                      optionValue="description"></g:select>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">用户</label>

                        <div class="col-md-3">
                            <g:textField name="user.id" required="" id="user" value="${params['id']}" class="hide"/>
                            <g:textField class="form-control" name="user.fullName" disabled=""
                                         value="${com.next.User.findById(params['id'])}" readonly="readonly"/>
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

</body>
</html>
