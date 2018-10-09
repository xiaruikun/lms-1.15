<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'address.label', default: 'Address')}" />
    <title>新增地址</title>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="address" action="index">单位地址</g:link>
                        </li>
                        <li class="active">
                            <span>新增地址</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    地址-新增
                </h2>

            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    新增地址
                </div>
                <div class="panel-body">
                    <g:form action="save" class="form-horizontal">
                        <g:if test="${flash.message}">
                            <div class="message" role="status">${flash.message}</div>
                        </g:if>
                        <g:hasErrors bean="${this.address}">
                            <ul class="errors" role="alert" style="right: 4%">
                                <g:eachError bean="${this.address}" var="error">
                                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                        <g:message error="${error}" />
                                    </li>
                                </g:eachError>
                            </ul>
                        </g:hasErrors>
                        <div class="form-group">
                            <label class="col-md-2 control-label">单位名称:</label>
                            <div class="col-md-3">
                                <g:textField class="form-control" name="name" value="${this.address?.name}"></g:textField>
                            </div>
                            <label class="col-md-1 control-label">地址</label>
                            <div class="col-md-3">
                                <g:textField class="form-control" name="address" value="${this.address?.address}"></g:textField>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">火车站:</label>
                            <div class="col-md-3">
                                <g:textField class="form-control" name="trainStations" value="${this.address?.trainStations}"></g:textField>
                            </div>
                            <label class="col-md-1 control-label">公交站</label>
                            <div class="col-md-3">
                                <g:textField class="form-control" name="busStations" value="${this.address?.busStations}"></g:textField>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">热线电话:</label>
                            <div class="col-md-3">
                                <g:textField class="form-control" name="tellphone" value="${this.address?.tellphone}"></g:textField>
                            </div>
                            <label class="col-md-1 control-label">工作时间</label>
                            <div class="col-md-3">
                                <g:textField class="form-control" name="openingHours" value="${this.address?.openingHours}"></g:textField>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">单位类型:</label>
                            <div class="col-md-3">
                                <g:select class="form-control" name="type.id" required id="type" value="${this.address?.type?.id}" from="${com.next.AddressType.list()}" optionKey="id" optionValue="name" noSelection="${['null':'请选择']}"></g:select>
                                <!-- <g:textField class="form-control" name="type" value="${this.address?.type?.name}"></g:textField> -->
                            </div>
                            <label class="col-md-1 control-label">所属城区</label>
                            <div class="col-md-3">
                                <g:if test="${this.address?.district != null && this.address?.district != ''}">
                                    <g:textField name="district.id" required="required" id="district" value="${this.address?.district?.id}" class="hide" />
                                    <g:textField name="districtName" value="${this.address?.district?.name}" readonly="readonly" class="form-control" />
                                </g:if>
                                <g:else>
                                    <g:select class="form-control" name="district.id" required id="district" value="${this.address?.district}" from="${com.next.District.list()}" optionKey="id" optionValue="name" noSelection="${['null':'请选择']}"></g:select>
                                </g:else>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-md-3 col-md-offset-4">
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
