<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'user.label', default: 'user')}"/>
    <title>汇报关系</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>汇报关系</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                用户:${com.next.User.findById(params['id'])}
            </h2>
        </div>
    </div>

</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                汇报关系
            </div>

            <div class="panel-body">
                <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                </g:if>
                <g:hasErrors bean="${this.reporting}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${this.reporting}" var="error">
                            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                    error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </g:hasErrors>
                <g:form action="save" class="form-horizontal">
                    <input type="hidden" name="manager.id" value="${params['id']}">
                    <div class="form-group">
                        <label class="col-md-3 control-label">下属员工</label>

                        <div class="col-md-3">
                            <g:select class="form-control m-b" name="user.id" id="teamRole" from="${userList}"
                                      optionKey="id"></g:select>
                        </div>
                    </div>

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
