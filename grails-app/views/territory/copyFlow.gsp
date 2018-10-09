<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'territory.label', default: 'Territory')}"/>
    <title>复制工作流</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>复制工作流</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                复制工作流
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                复制工作流
            </div>

            <div class="panel-body">
                <g:form action="copyFlow" class="form-horizontal">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <div class="form-group">
                      <g:if test="${this.territory}">
                        <label class="col-md-3 control-label">区域名称:</label>

                          <div class="col-md-3">
                            <g:textField name="territory" required="required" id="territory" value="${this.territory?.id}" class="hide" />
                            <g:textField name="territoryName" value="${this.territory?.name}" readonly="readonly" class="form-control" />
                          </div>
                        </g:if>
                          <g:else>
                          <label class="col-md-3 control-label">复制源:</label>

                            <div class="col-md-3">
                              <g:textField name="preOpportunityWorkflow" required="required" id="preOpportunityWorkflow" value="${this.preOpportunityWorkflow?.id}" class="hide" />
                              <g:textField name="preOpportunityWorkflowName" value="${this.preOpportunityWorkflow?.name}" readonly="readonly" class="form-control" />
                            </div>
                          </g:else>

                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">新工作流：</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="opportunityWorkflow" required="required" id="opportunityWorkflow"
                                      value="${this.opportunityWorkflow?.id}" from="${this.opportunityWorkFlows}"
                                      optionKey="id" optionValue="name" noSelection="${['null': '请选择']}"></g:select>
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
