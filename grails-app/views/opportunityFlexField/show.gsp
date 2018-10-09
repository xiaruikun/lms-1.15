<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunityFlexField.label', default: 'OpportunityFlexField')}"/>
    <title>弹性域模块：${this.opportunityFlexField?.name}</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">弹性域</li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                查看弹性域信息
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hyellow">
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_COO">
                        <g:link class="btn btn-info btn-xs" action="edit"
                                resource="${this.opportunityFlexField}"><i class="fa fa-edit"></i>编辑</g:link>
                    </sec:ifNotGranted>
                </div>
                弹性域信息
            </div>

            <div class="panel-body">
                <h4>
                    订单编号：${this.opportunityFlexField?.category?.opportunity?.serialNumber}
                </h4>
            </div>

            <div class="panel-footer contact-footer">
                <div class="row">
                    <div class="col-md-2 border-right">
                        <div class="contact-stat">
                            <span>名称</span>
                            <strong>${this.opportunityFlexField?.name}</strong>
                        </div>
                    </div>

                    <div class="col-md-2 border-right">
                        <div class="contact-stat">
                            <span>描述</span>
                            <strong>${this.opportunityFlexField?.description}</strong>
                        </div>
                    </div>

                    <div class="col-md-2 border-right">
                        <div class="contact-stat">
                            <span>数据类型</span>
                            <strong>${this.opportunityFlexField?.dataType}</strong>
                        </div>
                    </div>

                    <div class="col-md-2 border-right">
                        <div class="contact-stat">
                            <span>默认值</span>
                            <strong>${this.opportunityFlexField?.defaultValue}</strong>
                        </div>
                    </div>

                    <div class="col-md-2 border-right">
                        <div class="contact-stat">
                            <span>约束</span>
                            <strong>${this.opportunityFlexField?.valueConstraints}</strong>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="contact-stat">
                            <span>值</span>
                            <strong>${this.opportunityFlexField?.value}</strong>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_COO">
                        <g:link class="btn btn-info btn-xs" controller="OpportunityFlexFieldValue" action="create"
                                params="[field: this.opportunityFlexField?.id]"><i class="fa fa-plus"></i>新增</g:link>
                    </sec:ifNotGranted>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                选项
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="value"
                                              title="${message(code: 'OpportunityFlexFieldValue.value.label', default: '值')}"></g:sortableColumn>
                            <g:sortableColumn property="displayOrder"
                                              title="${message(code: 'OpportunityFlexFieldValue.displayOrder.label', default: '展示次序')}"></g:sortableColumn>

                            <sec:ifNotGranted roles="ROLE_COO">
                                <g:sortableColumn property="option" title="${message(code: '操作')}" width="10%"
                                                  class="text-center"></g:sortableColumn>
                            </sec:ifNotGranted>

                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunityFlexField?.values}">
                            <tr>
                                <td>${it?.value}</td>
                                <td>
                                    ${it?.displayOrder}
                                </td>
                                <sec:ifNotGranted roles="ROLE_COO">
                                    <td width="10%" class="text-center">
                                        <g:form resource="${it}" method="DELETE">
                                            <button class="deleteBtn btn btn-danger btn-xs" type="button"><i
                                                    class="fa fa-trash-o"></i> 删除</button>
                                        </g:form>
                                    </td>
                                </sec:ifNotGranted>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
