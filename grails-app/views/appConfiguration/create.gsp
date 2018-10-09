<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'appConfiguration.label', default: 'AppConfiguration')}" />
        <title>新增邀请码配置</title>
    </head>
    <body>
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li class="active">
                            新增邀请码配置
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs" style="text-transform:none;">
                    配置:${com.next.AppVersion.findById(params['id'])?.appName} ${com.next.AppVersion.findById(params['id'])?.appVersion}
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hgreen">
                <div class="panel-heading">
                    新增邀请码配置
                </div>
                <div class="panel-body form-horizontal">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.appConfiguration}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.appConfiguration}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <g:form action="save" class="form-horizontal">
                        <input type="hidden" name="appVersion" value="${params['id']}">
                        <div class="form-group">
                            <label class="col-md-3 control-label">key邀请码名称</label>

                            <div class="col-md-3">
                                <g:select class="form-control m-b" name="key" id="key" required="required" from="${com.next.AppConfigurationKey.list()}" noSelection="['':'-请选择-']"
                                          optionKey="id" optionValue="name"></g:select>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">邀请码内容:</label>
                            <div class="col-md-3">
                                <g:textField type="text" id="value" name="value" required="required" class="form-control" maxlength="256" />
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
