<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'messageTemplate.label', default: 'MessageTemplate')}"/>
    <title>消息模板详情</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="messageTemplate" action="index">推送模板</g:link>
                    </li>
                    <li class="active">
                        <span>消息推送模板</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                消息推送模板
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link action="edit" class="btn btn-info btn-xs" resource="${this.messageTemplate}"><i
                            class="fa fa-edit"></i>编辑</g:link>
                   %{-- <g:form resource="${this.messageTemplate}" method="DELETE" style="display: inline-block">
                        <button class="deleteBtn btn btn-danger btn-xs" type="button">
                            <i class="fa fa-trash-o"></i>删除
                        </button>
                    </g:form>--}%
                </div>
                消息模板
            </div>

            <div class="panel-body form-horizontal">
                <div class="form-group">
                    <label class="col-md-2 control-label">名称：</label>

                    <div class="col-md-8">
                        <span class="cont">${this.messageTemplate?.name}</span>
                    </div>
                </div>
                <div class="hr-line-dashed"></div>
                <div class="form-group">
                    <label class="col-md-2 control-label">内容：</label>

                    <div class="col-md-8">
                        <span class="cont">${this.messageTemplate?.text}</span>
                    </div>
                </div>
                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-2 control-label">模板：</label>

                    <div class="col-md-8">
                        <g:if test="${this.messageTemplate?.template}">
                            <pre>
                                <code>${this.messageTemplate?.template}</code>
                            </pre>
                        </g:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
