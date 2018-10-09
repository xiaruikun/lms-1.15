<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'externalDataMapping.label', default: 'ExternalDataMapping')}"/>
    <title>新增外部数据映射</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="externalDataMapping" action="index">外部数据映射列表</g:link>
                    </li>
                    <li class="active">
                        <span>新增外部数据映射</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                新增外部数据映射
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                新增外部数据映射
            </div>

            <div class="panel-body">
                <g:form action="save" class="form-horizontal">
                    <g:if test="${flash.message}">
                        <div class="message alert alert-info" role="status">${flash.message}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                                    aria-hidden="true">×</span></button>
                        </div>
                    </g:if>
                    <g:hasErrors bean="${this.externalDataMapping}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.externalDataMapping}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <div class="form-group">
                        <label class="col-md-3 control-label">外部数据映射名称</label>

                        <div class="col-md-3">
                            <g:textField name="systemName" value="${this.externalDataMapping?.systemName}" required=""
                                         maxlength="128" class="form-control"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">外部数据映射名称</label>

                        <div class="col-md-3">
                            <g:textField name="categoryName" value="${this.externalDataMapping?.categoryName}"
                                         required=""
                                         maxlength="32" class="form-control"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">value1</label>

                        <div class="col-md-3">
                            <g:textField name="value1" value="${this.externalDataMapping?.value1}" required=""
                                         maxlength="32"
                                         class="form-control"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">value2</label>

                        <div class="col-md-3">
                            <g:textField name="value2" value="${this.externalDataMapping?.value2}" required=""
                                         maxlength="128" class="form-control"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
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

