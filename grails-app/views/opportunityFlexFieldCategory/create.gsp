<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'opportunityFlexFieldCategory.label', default: 'OpportunityFlexFieldCategory')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
      <div class="small-header">
          <div class="hpanel">
              <div class="panel-body">
                  <div id="hbreadcrumb" class="pull-right">
                      <ol class="hbreadcrumb breadcrumb">
                          <li>中佳信LMS</li>
                          <li>
                          </li>
                          <li class="active">
                              <span>新增弹性域模块</span>
                          </li>
                      </ol>
                  </div>
                  <h2 class="font-light m-b-xs">
                      新增弹性域模块
                  </h2>
              </div>
          </div>
      </div>
      <div class="content animate-panel">
          <div class="row">
              <div class="hpanel hblue">
                  <div class="panel-heading">
                      新增工弹性域模块
                  </div>
                  <div class="panel-body">
                      <g:form action="save" class="form-horizontal">
                      <g:if test="${flash.message}">
                      <div class="message" role="status">${flash.message}</div>
                      </g:if>
                      <g:hasErrors bean="${this.opportunityFlexFieldCategory}">
                      <ul class="errors" role="alert">
                          <g:eachError bean="${this.opportunityFlexFieldCategory}" var="error">
                          <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                          </g:eachError>
                      </ul>
                      </g:hasErrors>
                      <div class="form-group">
                          <label class="col-md-3 control-label">模块</label>
                          <div class="col-md-3">
                              <g:textField  class="hidden" name="opportunity.id" id="opportunity" required value="${this.opportunityFlexFieldCategory?.opportunity?.id}" />
                              <g:select class="form-control" name="flexFieldCategory.id" required id="flexFieldCategory" value="${this.opportunityFlexFieldCategory?.flexFieldCategory}" from="${com.next.FlexFieldCategory.list()}" optionKey="id" optionValue="name" ></g:select>
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
