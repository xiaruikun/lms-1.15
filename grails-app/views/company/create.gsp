<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'company.label', default: 'Company')}" />
        <title>新增公司信息</title>
    </head>
    <body class="fixed-navbar fixed-sidebar">
<div class="small-header">

    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right m-t-lg">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li><g:link controller="contact" action="show" id="${this.company?.contact?.id}"> 贷款人管理</g:link></li>
                    <li class="active">
                        <span>新增公司信息</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">新增公司信息</h2>
        </div>
    </div>
</div>
<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                新增公司信息
            </div>

            <div class="panel-body">
                <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                </g:if>
                <g:hasErrors bean="${this.company}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${this.company}" var="error">
                            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </g:hasErrors>

                <g:form action="save" class="form-horizontal">
                    <g:textField name="contact.id" id="contact" value="${this.company.contact.id}" class="hide"/>
                    <div class="form-group">
                        <label class="col-md-3 control-label">公司</label>
                        <div class="col-md-3 input-group">
                            <g:textField class="form-control" name="company"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">行业</label>
                        <div class="col-md-3 input-group">
                            <g:select  class="form-control" name="industry"
                                      id="industry" optionValue="name" optionKey="id" from="${com.next.Industry.list()}" noSelection="${['null':'请选择']}"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">工商&机构代码</label>
                        <div class="col-md-3 input-group">
                            <g:textField class="form-control" name="companyCode"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
                            <g:submitButton class="btn btn-info" name="update" value="保存"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>

    </body>
</html>
