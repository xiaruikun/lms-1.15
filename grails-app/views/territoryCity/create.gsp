<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'territoryCity.label', default: 'TerritoryCity')}" />
    <title>新增区域-城市</title>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="territoryCity" action="index">区域-城市</g:link>
                        </li>
                        <li class="active">
                            <span>新增区域-城市</span>
                        </li>
                    </ol>
                </div>

                <h2 class="font-light m-b-xs">
                    新增区域-城市
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    新增区域-城市
                </div>
                <div class="panel-body">
                    <g:form action="save" class="form-horizontal">
                        <g:if test="${flash.message}">
                            <div class="message" role="status">${flash.message}</div>
                        </g:if>
                        <g:hasErrors bean="${this.territoryCity}">
                            <ul class="errors" role="alert">
                                <g:eachError bean="${this.territoryCity}" var="error">
                                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                        <g:message error="${error}" />
                                    </li>
                                </g:eachError>
                            </ul>
                        </g:hasErrors>
                        <div class="form-group">
                            <label class="col-md-3 control-label">区域名称:</label>
                            <div class="col-md-3">
                                <g:if test="${this.territoryCity?.territory != null && this.territoryCity?.territory != ''}">
                                    <g:textField name="territory.id" required="required" id="territory" value="${this.territoryCity?.territory?.id}" class="hide" />
                                    <g:textField name="territoryName" value="${this.territoryCity?.territory?.name}" readonly="readonly" class="form-control" />
                                </g:if>
                                <g:else>
                                    <g:select class="form-control" name="territory.id" required="required" id="territory" value="${this.territoryCity?.territory}" from="${com.next.Territory.list()}" optionKey="id" optionValue="name" noSelection="${['null':'请选择']}"></g:select>
                                </g:else>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">城市名称</label>
                            <div class="col-md-3">
                                <g:if test="${this.territoryCity?.city != null && this.territoryCity?.city != ''}">
                                    <g:textField name="city.id" required="required" id="city" value="${this.territoryCity?.city?.id}" class="hide" />
                                    <g:textField name="cityName" value="${this.territoryCity?.city?.name}" readonly="readonly" class="form-control" />
                                </g:if>
                                <g:else>
                                    <g:select class="form-control" name="city.id" required="required" id="city" value="${this.territoryCity?.city}" from="${com.next.City.list()}" optionKey="id" optionValue="name" noSelection="${['null':'请选择']}"></g:select>
                                </g:else>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">开始时间</label>
                            <div class="col-md-3 datePicker">
                                <g:datePicker name="startTime" value="${new Date()}" precision="day" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">结束时间</label>
                            <div class="col-md-3 datePicker">
                                <g:datePicker name="endTime" value="${new Date()}" precision="day" />
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
