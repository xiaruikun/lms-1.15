<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'account.label', default: 'Account')}"/>
    <title>机构：${account.name}</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header transition animated fadeIn">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li><g:link controller="account" action="index">机构</g:link></li>
                    <li class="active">
                        <span>${this.account.name}</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                机构: ${this.account.name}
            </h2>
            <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>${this.account.name} 的资料</small>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <g:if test="${flash.message}">
            <div class="message alert alert-info" role="status">${flash.message}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
            </div>
        </g:if>
        <div class="hpanel hred contact-panel">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" action="edit"
                            resource="${this.account}"><i class="fa fa-edit"></i>编辑</g:link>
                </div>
                机构基本信息
            </div>

            <div class="panel-body">
                <div class="text-muted font-bold m-b-xs ol-md-6">
                    <div class="col-md-12"><h4><a href=>${this.account.name}</a></h4></div>
                </div>
            </div>

            <div class="panel-footer contact-footer">
                <div class="row">
                    <div class="col-md-4 border-right" style="">
                        <div class="contact-stat"><span>区域数量:</span> <strong>${this.territoryAccounts?.size()}</strong>
                        </div>
                    </div>

                    <div class="col-md-4 border-right" style="">
                        <div class="contact-stat"><span>城市数量:</span> <strong>${this.accountCities?.size()}</strong>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hyellow">
            <div class="panel-heading">
                <div class="panel-tools">
                    <!-- <g:link class="btn btn-info btn-xs" controller="territoryAccount" action="create"
                                 params="[account: this.account?.id]"><i class="fa fa-plus"></i>添加</g:link> -->
                </div>
                所属区域
            </div>

            <div class="panel-body no-padding">
                <ul class="list-group">
                    <g:each in="${this.territoryAccounts}">
                        <li class="list-group-item">
                            <div class="pull-right">

                                <!-- <g:form controller="territoryAccount" action="delete" id="${it.id}"
                                             method="DELETE">
                                    <input class="btn btn-danger btn-xs btn-block" type="submit" value="删除" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                                </g:form> -->
                            </div>
                            <g:link controller="territory" action="show"
                                    id="${it.territory?.id}">${it.territory?.name}</g:link>
                        </li>
                    </g:each>
                </ul>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" controller="accountCity" action="create"
                            params="[account: this.account.id]"><i class="fa fa-plus"></i>添加</g:link>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                开放城市
            </div>

            <div class="panel-body no-padding">
                <ul class="list-group">
                    <g:each in="${this.accountCities}">
                        <li class="list-group-item">
                            <div class="pull-right">
                                <g:form controller="accountCity" action="delete" id="${it.id}" method="DELETE">
                                    <button class="deleteBtn btn btn-danger btn-outline btn-xs" type="button"><i class="fa fa-trash-o"></i> 删除</button>
                                </g:form>
                            </div>
                            <g:link controller="city" action="show" id="${it.city?.id}">${it.city?.name}</g:link>
                        </li>
                    </g:each>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>
