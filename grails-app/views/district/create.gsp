<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'district.label', default: 'District')}" />
    <title>新增城区</title>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="district" action="index">城区管理</g:link>
                        </li>
                        <li class="active">
                            <span>新增城区</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    城区管理-新增城区
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    新增城区
                </div>
                <div class="panel-body form-horizontal">
                    <g:form action="save" class="form-horizontal">
                        <g:if test="${flash.message}">
                            <div class="message" role="status">${flash.message}</div>
                        </g:if>
                        <g:hasErrors bean="${this.district}">
                            <ul class="errors" role="alert">
                                <g:eachError bean="${this.district}" var="error">
                                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                        <g:message error="${error}" />
                                    </li>
                                </g:eachError>
                            </ul>
                        </g:hasErrors>

                        <div class="form-group">
                            <label class="col-md-3 control-label">城区名称</label>
                            <div class="col-md-3">
                                <g:textField name="name" value="${this.district?.name}" class="form-control" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">所属城市</label>
                            <div class="col-md-3">
                                <g:if test="${this.district?.city != null && this.district?.city != ''}">
                                    <g:textField name="city.id" required="required" id="city" value="${this.district?.city?.id}" class="hide" />
                                    <g:textField name="cityName" value="${this.district?.city?.name}" readonly="readonly" class="form-control" />
                                </g:if>
                                <g:else>
                                    <g:select class="form-control" name="city.id" required="required" id="city" value="${this.district?.city}" from="${com.next.City.list()}" optionKey="id" optionValue="name" noSelection="${['null':'请选择']}"></g:select>
                                </g:else>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">城区编码</label>
                            <div class="col-md-3">
                                <g:textField name="code" value="${this.district?.code}" class="form-control" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">出它项时间</label>
                            <div class="col-md-3">
                                <g:textField name="daysOfOtherRights" value="${this.district?.daysOfOtherRights}" class="form-control" />
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-md-3 col-md-offset-3">
                                <g:submitButton class="btn btn-info" name="create" value="保存" />
                            </div>
                        </div>
                    </g:form>
                </div>
            </div>
        </div>
    </div>

</body>

</html>
