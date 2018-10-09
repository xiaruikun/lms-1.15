<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'workflowUser.label', default: 'workflowUser')}"/>
    <title>权限</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        区域权限
                    </li>
                    <li class="active">
                        <span>新增区域权限</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                区域权限-新增区域权限
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                新增区域权限
            </div>

            <div class="panel-body">
                <g:form action="save" class="form-horizontal">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.workflowUser}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.workflowUser}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <div class="form-group">
                        <label class="col-md-4 control-label">岗位</label>

                        <div class="col-md-4">
                          <input type="hidden" name="stage.id" value="${this.workflowUser?.stage?.id}">
                          <g:select class="form-control" name="position.id" required="required" id="position"
                                    value="${this.workflowUser?.position?.id}"
                                    from="${com.next.Position.list()}" optionKey="id" optionValue="name"
                                    noSelection="['': '--请选择--']"></g:select>

                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-4 control-label">权限</label>

                        <div class="col-md-4">
                          <g:select class="form-control" name="authority.id" required="required" id="authority"
                                    value="${this.workflowUser?.authority?.id}"
                                    from="${com.next.WorkflowAuthority.list()}" optionKey="id" optionValue="name"
                                    noSelection="['': '--请选择--']"></g:select>

                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-4 col-md-offset-5">
                            <button class="btn btn-info" type="submit">保存</button>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
