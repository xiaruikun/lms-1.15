<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main"/>
        <g:set var="entityName" value="${message(code: 'territoryFlow.label', default: 'TerritoryFlow')}"/>
        <title><g:message code="default.edit.label" args="[entityName]"/></title>
    </head>
    <body>
        <div class="small-header">
            <div class="hpanel">
                <div class="panel-body">
                    <div id="hbreadcrumb" class="pull-right">
                        <ol class="hbreadcrumb breadcrumb">
                            <li>中佳信LMS</li>
                            <li>
                                工作流
                            </li>
                            <li class="active">
                                <span>信息编辑</span>
                            </li>
                        </ol>
                    </div>

                    <h2 class="font-light m-b-xs">
                        工作流
                    </h2>
                </div>
            </div>
        </div>
        <div class="content animate-panel">
            <div class="row">
                <div class="hpanel hblue">
                    <div class="panel-heading">
                        工作流信息编辑
                    </div>
                    <div class="panel-body">
                        <g:form resource="${this.territoryFlow}" method="PUT" class="form-horizontal">
                            <g:if test="${flash.message}">
                                <div class="message" role="status">${flash.message}</div>
                            </g:if>
                            <g:hasErrors bean="${this.territoryFlow}">
                                <ul class="errors" role="alert">
                                    <g:eachError bean="${this.territoryFlow}" var="error"><li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <div class="form-group">
                        <label class="col-md-3 control-label">区域</label>
                        <div class="col-md-3">
                            <g:textField name="territory.id" value="${this.territoryFlow?.territory?.id}" id="territory" class="hide"/>
                            <g:textField name="territoryName" value="${this.territoryFlow?.territory?.name}" class="form-control" readonly="readonly" />
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">阶段</label>
                        <div class="col-md-3">
                            <g:textField name="stage.id" value="${this.territoryFlow?.stage?.id}" id="stage" class="hide"/>
                            <g:textField name="stageName" value="${this.territoryFlow?.stage?.name}"  readonly="readonly" class="form-control" />
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">执行次序</label>
                        <div class="col-md-3 checkbox-inline">
                            <g:textField name="executionSequence" value="${this.territoryFlow?.executionSequence}" class="form-control" readonly="readonly" />

                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group ">
                        <label class="col-md-3 control-label">是否回退</label>
                        <div class="col-md-3 checkbox-inline">
                            <g:radioGroup class="checkbox-inline" name="canReject" value="${this.territoryFlow?.canReject}" labels="['true','false']" values="[true,false]">
                                ${it.radio} ${it.label}
                            </g:radioGroup>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">布局</label>
                        <div class="col-md-3 checkbox-inline">
                            <g:select class="form-control" name="opportunityLayout.id" value="${this.territoryFlow?.opportunityLayout?.id}" required="required" id="opportunityLayout" from="${com.next.OpportunityLayout.findAllByActive(true)}" optionKey="id" optionValue="description" noSelection="${['null':'请选择']}"></g:select>
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
<!-- <a href="#edit-territoryFlow" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="edit-territoryFlow" class="content scaffold-edit" role="main">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.territoryFlow}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.territoryFlow}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form resource="${this.territoryFlow}" method="PUT">
                <g:hiddenField name="version" value="${this.territoryFlow?.version}" />
                <fieldset class="form">
                    <f:all bean="territoryFlow"/>
                </fieldset>
                <fieldset class="buttons">
                    <input class="save" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                </fieldset>
            </g:form>
        </div> -->
</body>
</html>
