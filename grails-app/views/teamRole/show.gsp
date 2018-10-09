<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'teamRole.label', default: 'TeamRole')}"/>
    <title>权限</title>
</head>

<body class="fixed-navbar fixed-sidebar">

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right m-t-lg">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>查看权限</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                查看权限
            </h2>
        </div>
    </div>
</div>

<g:if test="${flash.message}">
    <div class="row">
        <div class="hpanel">
            <div class="panel-body">
                <div class="alert alert-info" role="alert">${flash.message}</div>
            </div>
        </div>
    </div>
    </div>
</g:if>

<div class="row">
    <div class="container">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                查看权限
            </div>

            <div class="panel-body">
                <g:form resource="${this.teamRole}" method="DELETE">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">权限</label>

                        <div class="col-sm-2">${this.teamRole?.name}</div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-2 col-lg-offset-2">
                            <g:link class="edit" action="edit" resource="${this.teamRole}"><g:message
                                    code="default.button.edit.label" default="Edit"/></g:link>
                            <input class="delete" type="submit"
                                   value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                   onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
