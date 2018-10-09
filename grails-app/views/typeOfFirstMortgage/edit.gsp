<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'typeOfFirstMortgage.label', default: 'TypeOfFirstMortgage')}" />
    <title>一抵类型：${typeOfFirstMortgage.name}</title>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="typeOfFirstMortgage" action="index">一抵类型管理</g:link>
                        </li>
                        <li class="active">
                            <span>${this.typeOfFirstMortgage?.name}</span>
                        </li>
                    </ol>
                </div>

                <h2 class="font-light m-b-xs">
                    一抵类型: ${this.typeOfFirstMortgage?.name}
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    一抵类型信息编辑
                </div>
                <div class="panel-body">
                    <g:form resource="${this.typeOfFirstMortgage}" method="PUT" class="form-horizontal">
                        <g:if test="${flash.message}">
                            <div class="message" role="status">${flash.message}</div>
                        </g:if>
                        <g:hasErrors bean="${this.typeOfFirstMortgage}">
                            <ul class="errors" role="alert">
                                <g:eachError bean="${this.typeOfFirstMortgage}" var="error">
                                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                        <g:message error="${error}" />
                                    </li>
                                </g:eachError>
                            </ul>
                        </g:hasErrors>
                        <div class="form-group">
                            <label class="col-md-3 control-label">一抵类型名称</label>
                            <div class="col-md-3">
                                <g:textField name="name" value="${this.typeOfFirstMortgage?.name}" class="form-control" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-md-3 col-md-offset-3">
                                <g:submitButton class="btn btn-info" name="update" value="保存" />
                            </div>
                        </div>
                    </g:form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
