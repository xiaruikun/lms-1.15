<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName"
           value="${message(code: 'opportunityStageCategory.label', default: 'OpportunityStageCategory')}"/>
    <title>订单阶段分类详情</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="OpportunityStageCategory" action="index">订单阶段</g:link>
                    </li>
                    <li class="active">
                        <span>订单阶段分类详情</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                订单阶段
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" action="edit" resource="${this.opportunityStageCategory}"><i class="fa fa-edit"></i>编辑</g:link>
                    <g:form resource="${this.opportunityStageCategory}" method="DELETE" style="display: inline-block">
                        <button class="deleteBtn btn btn-danger btn-xs" type="button">
                            <i class="fa fa-trash-o"></i>删除
                        </button>
                    </g:form>
                </div>
                阶段阶段分类详情
            </div>

            <div class="panel-body form-horizontal">
                <g:if test="${flash.message}">
                    <div class="message alert alert-info" role="status">${flash.message}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                                aria-hidden="true">×</span></button>
                    </div>
                </g:if>
                <div class="form-group">
                    <label class="col-md-3 control-label">阶段分类名称：</label>

                    <div class="col-md-3">
                        <g:textField name="name" value="${this.opportunityStageCategory?.name}" disabled=""
                                     class="form-control"/>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

</body>

%{--<body>
<a href="#show-opportunityStageCategory" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                               default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="show-opportunityStageCategory" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <f:display bean="opportunityStageCategory"/>
    <g:form resource="${this.opportunityStageCategory}" method="DELETE">
        <fieldset class="buttons">
            <g:link class="edit" action="edit" resource="${this.opportunityStageCategory}"><g:message
                    code="default.button.edit.label" default="Edit"/></g:link>
            <input class="delete" type="submit"
                   value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                   onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>--}%
</html>
