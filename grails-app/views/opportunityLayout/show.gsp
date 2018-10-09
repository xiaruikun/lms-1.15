<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'opportunityLayout.label', default: 'OpportunityLayout')}" />
        <title>布局详情</title>
    </head>
    <body>
      <div class="small-header">
          <div class="hpanel">
              <div class="panel-body">
                  <div id="hbreadcrumb" class="pull-right">
                      <ol class="hbreadcrumb breadcrumb">
                          <li>中佳信LMS</li>
                          <li>
                              <g:link controller="OpportunityLayout" action="index">布局</g:link>
                          </li>
                          <li class="active">
                              <span>${this.opportunityLayout?.name}</span>
                          </li>
                      </ol>
                  </div>
                  <h2 class="font-light m-b-xs">
                      布局: ${this.OpportunityLayout?.id}
                  </h2>
              </div>
          </div>
      </div>
      <div class="content animate-panel">
          <div class="row">
              <div class="hpanel hblue">
                  <div class="panel-heading">
                      <div class="panel-tools">
                          <g:link class="btn btn-info btn-xs" action="edit" resource="${this.opportunityLayout}"><i class="fa fa-edit"></i>编辑</g:link>
                          <g:form resource="${this.opportunityLayout}" method="DELETE" style="display: inline-block">
                              <button class="deleteBtn btn btn-danger btn-xs" type="button">
                                  <i class="fa fa-trash-o"></i>删除
                              </button>
                          </g:form>
                      </div>
                      布局详情
                  </div>
                  <div class="panel-body form-horizontal">
                      <div class="form-group" >
                          <label class="col-md-4 control-label">名称：</label>
                          <div class="col-md-4">
                              <g:textField class="form-control" disabled="" name="name" value="${this.opportunityLayout?.name}"></g:textField>
                          </div>
                      </div>
                      <div class="hr-line-dashed"></div>
                      <div class="form-group" >
                          <label class="col-md-4 control-label">描述：</label>
                          <div class="col-md-4">
                              <g:textField class="form-control" disabled="" name="description" value="${this.opportunityLayout?.description}"></g:textField>
                          </div>
                      </div>
                      <div class="hr-line-dashed"></div>
                      <div class="form-group" >
                          <label class="col-md-4 control-label">启用：</label>
                          <div class="col-md-4">
                              <g:textField class="form-control" disabled="" name="active" value="${this.opportunityLayout?.active}"></g:textField>
                          </div>
                      </div>
                      <div class="hr-line-dashed"></div>

                  </div>
              </div>
          </div>
      </div>
    </body>
</html>
